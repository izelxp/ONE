package uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JuegoUno {

    private List<Jugador> jugadores;
    private Mazo mazoPrincipal;
    private List<Carta> mazoDescarte;
    private int indiceTurnoActual;
    private int direccion; // 1 horario, -1 anti-horario
    private String colorActual;
    private Scanner scanner;
    private boolean juegoTerminado;

    /**
     * Constructor para 1 Humano vs 3 IAs
     */
    public JuegoUno(String nombreHumano, Scanner scanner) {
        this.scanner = scanner;
        this.jugadores = new ArrayList<>();
        this.mazoDescarte = new ArrayList<>();
        this.mazoPrincipal = new Mazo();
        this.direccion = 1; 
        this.indiceTurnoActual = 0;
        this.juegoTerminado = false;

        // Inicializar jugadores
        jugadores.add(new Jugador(nombreHumano, true));
        
        //Nombres predefinidos para las computadoras
        
        String[] nombresCompu = {"Pepe", "Toña", "Mari"};
        
        for (int i = 0; i<nombresCompu.length; i++) {
            jugadores.add(new Jugador(nombresCompu[i], false));
        }

        repartirCartas();
        iniciarJuego();
        jugar();
    }

    private void repartirCartas() {
        System.out.println("\nRepartiendo 7 cartas a cada jugador...");
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) {
                j.tomarCarta(mazoPrincipal);
            }
        }
    }

    private void iniciarJuego() {
        Carta primera = mazoPrincipal.tomarCarta();
        mazoDescarte.add(primera);
        colorActual = primera.getColor();
        System.out.println("\nPRIMERA CARTA EN MESA: " + primera);
        System.out.println("-".repeat(50));
    }

    /**
     * Bucle principal con lógica de Módulo
     */
    public void jugar() {
        while (!juegoTerminado) {
            Jugador actual = jugadores.get(indiceTurnoActual);
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("TURNO DE: " + actual.getNombre());
            System.out.println("Carta en mesa: " + mazoDescarte.get(mazoDescarte.size() - 1));
            System.out.println("Color actual: " + colorActual);
            System.out.println("=".repeat(40));

            boolean gano;
            if (actual.isEsHumano()) {
                gano = turnoJugador(actual);
            } else {
                gano = turnoMaquina(actual);
            }

            if (gano) {
                System.out.println("\n¡¡¡ " + actual.getNombre() + " HA GANADO EL JUEGO !!!");
                juegoTerminado = true;
                break;
            }

            avanzarTurno();
        }
    }

    private void avanzarTurno() {
        // Fórmula de módulo: (actual + dirección + total) % total
        int total = jugadores.size();
        indiceTurnoActual = (indiceTurnoActual + direccion + total) % total;
    }

    private boolean turnoJugador(Jugador humano) {
        Carta cartaMesa = mazoDescarte.get(mazoDescarte.size() - 1);
        humano.getMano().mostrar(humano.getNombre());
        
        List<Integer> validas = humano.getMano().obtenerCartasValidas(cartaMesa);

        if (validas.isEmpty()) {
            System.out.println("No tienes cartas válidas. Robando...");
            Carta robada = tomarDelMazoConRetorno(humano);
            if (robada != null && robada.esValida(cartaMesa)) {
                System.out.println("¡La robada es válida! Se juega: " + robada);
                return procesarCartaJugada(robada, humano);
            }
            return false;
        }

        while (true) {
            System.out.print("Elige índice o 't' para robar: ");
            String op = scanner.nextLine();
            if (op.equalsIgnoreCase("t")) {
                tomarDelMazoConRetorno(humano);
                return false;
            }
            try {
                int idx = Integer.parseInt(op);
                if (validas.contains(idx)) {
                    Carta jugada = humano.jugarCarta(idx);
                    return procesarCartaJugada(jugada, humano);
                }
            } catch (Exception e) { System.out.println("Entrada inválida."); }
        }
    }

    private boolean turnoMaquina(Jugador ia) {
        Carta cartaMesa = mazoDescarte.get(mazoDescarte.size() - 1);
        List<Integer> validas = ia.getMano().obtenerCartasValidas(cartaMesa);

        if (validas.isEmpty()) {
            Carta robada = tomarDelMazoConRetorno(ia);
            if (robada != null && robada.esValida(cartaMesa)) {
                return procesarCartaJugada(robada, ia);
            }
            return false;
        }

        // IA elige la primera válida
        Carta jugada = ia.jugarCarta(validas.get(0));
        System.out.println(ia.getNombre() + " juega: " + jugada);
        return procesarCartaJugada(jugada, ia);
    }

    private boolean procesarCartaJugada(Carta jugada, Jugador actual) {
        mazoDescarte.add(jugada);
        colorActual = jugada.getColor();
        aplicarEfectosEspeciales(jugada, actual);
        
        if (actual.getMano().size() == 1) {
            verificarReglaUno(actual);
        }
        return actual.getMano().estaVacia();
    }

    private void aplicarEfectosEspeciales(Carta carta, Jugador actual) {
        // Determinar quién es el siguiente (víctima de efectos)
        int siguienteIdx = (indiceTurnoActual + direccion + jugadores.size()) % jugadores.size();
        Jugador victima = jugadores.get(siguienteIdx);

        switch (carta.getTipo()) {
            case ROBA2 -> {
                System.out.println(victima.getNombre() + " recibe 2 cartas y pierde turno.");
                penalizar(victima, 2);
                avanzarTurno(); 
            }
            case ROBA4 -> {
                System.out.println(victima.getNombre() + " recibe 4 cartas y pierde turno.");
                penalizar(victima, 4);
                cambiarColor(actual);
                avanzarTurno();
            }
            case SALTO -> {
                System.out.println("¡Salto! Turno de " + victima.getNombre() + " omitido.");
                avanzarTurno();
            }
            case REVERSA -> {
                direccion *= -1;
                System.out.println("¡Reversa! El sentido del juego ha cambiado.");
            }
            case COMODIN -> cambiarColor(actual);
            default -> {}
        }
    }

    private void penalizar(Jugador j, int cant) {
        for (int i = 0; i < cant; i++) {
            tomarDelMazoConRetorno(j);
        }
    }

    private void cambiarColor(Jugador actual) {
        if (actual.isEsHumano()) {
            colorActual = elegirColorHumano();
        } else {
            colorActual = elegirColorComputadora(actual);
        }
        // Actualizar la carta física en el mazo de descarte para validaciones
        mazoDescarte.get(mazoDescarte.size() - 1).setColor(colorActual);
    }

    private String elegirColorHumano() {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        System.out.println("Elige color: 1.Rojo, 2.Azul, 3.Verde, 4.Amarillo");
        while(true) {
            try {
                int op = Integer.parseInt(scanner.nextLine());
                if(op >= 1 && op <= 4) return colores[op-1];
            } catch(Exception e) {}
            System.out.print("Intenta de nuevo (1-4): ");
        }
    }

    /**
     * La computadora elige un color al azar.
     */
    private String elegirColorComputadora(Jugador computadora) {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        Random random = new Random();
        
        // Elegimos un índice entre 0 y 3
        int indiceAleatorio = random.nextInt(colores.length);
        String colorElegido = colores[indiceAleatorio];
        
        System.out.println(computadora.getNombre() + " ha elegido el color: " + colorElegido);
        return colorElegido;
    }

    private Carta tomarDelMazoConRetorno(Jugador j) {
        if (!mazoPrincipal.hayCartas()) {
            mazoPrincipal.reconstruir(mazoDescarte);
        }
        return j.tomarCarta(mazoPrincipal);
    }

    private void verificarReglaUno(Jugador actual) {
        if (actual.isEsHumano()) {
            System.out.print("¡Te queda una carta! ¿Quieres decir UNO? (s/n): ");
            actual.setDijoUno(scanner.nextLine().equalsIgnoreCase("s"));
        } else {
            actual.setDijoUno(true); // La computadora  siempre dice UNO
        }
    }
}