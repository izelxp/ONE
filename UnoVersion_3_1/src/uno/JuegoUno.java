package uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuegoUno {
    private List<Jugador> jugadores;
    private Mazo mazoPrincipal;
    private List<Carta> mazoDescarte;
    private int indiceTurnoActual;
    private int direccion; 
    private String colorActual;
    private Scanner scanner;
    private boolean juegoTerminado;

    public JuegoUno(String nombreHumano, int numBots, Scanner scanner) {
        this.scanner = scanner;
        this.jugadores = new ArrayList<>();
        this.mazoDescarte = new ArrayList<>();
        this.mazoPrincipal = new Mazo();
        this.direccion = 1; 
        this.indiceTurnoActual = 0;
        this.juegoTerminado = false;

        jugadores.add(new JugadorHumano(nombreHumano));
        String[] nombresCompu = {"Bot Pepe", "Bot Toña", "Bot Mari"};
        for (int i = 0; i < numBots; i++) {
            jugadores.add(new JugadorComputadora(nombresCompu[i]));
        }

        repartirCartas();
        iniciarJuego();
        jugar();
    }

    private void repartirCartas() {
        System.out.println("\nRepartiendo 7 cartas a cada jugador...");
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) j.tomarCarta(mazoPrincipal);
        }
    }

    private void iniciarJuego() {
        Carta primera;
        do {
            primera = mazoPrincipal.tomarCarta();
            mazoDescarte.add(primera);
        } while (primera.getTipo() != Carta.Tipo.NUMERO); 

        colorActual = primera.getColor();
        System.out.println("\nPRIMERA CARTA EN MESA: " + primera);
    }

    public void jugar() {
        while (!juegoTerminado) {
            Jugador actual = jugadores.get(indiceTurnoActual);
            
            System.out.println("\n========================================");
            System.out.println("TURNO DE: " + actual.getNombre());
            System.out.println("Carta en mesa: " + mazoDescarte.get(mazoDescarte.size() - 1));
            System.out.println("Color actual: " + colorActual);
            System.out.println("========================================");
            Carta jugada = actual.ejecutarTurno(mazoDescarte.get(mazoDescarte.size() - 1), mazoPrincipal, scanner);
            
            boolean gano = false;

            if (jugada == null) {
                Carta robada = tomarDelMazoConRetorno(actual);
                if (robada != null && robada.esValida(mazoDescarte.get(mazoDescarte.size() - 1))) {
                    System.out.println(actual.getNombre() + " juega la carta robada: " + robada);
                    gano = procesarCartaJugada(robada, actual);
                } else if (robada != null && actual instanceof JugadorHumano) {
                    System.out.println("Robaste: " + robada + ". No se puede jugar, pasa el turno.");
                }
            } else {
                gano = procesarCartaJugada(jugada, actual);
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
        int total = jugadores.size();
        indiceTurnoActual = (indiceTurnoActual + direccion + total) % total;
    }

    private boolean procesarCartaJugada(Carta jugada, Jugador actual) {
        mazoDescarte.add(jugada);
        colorActual = jugada.getColor();
        aplicarEfectosEspeciales(jugada, actual);
        verificarReglaUno(actual);
        return actual.getMano().estaVacia();
    }

    private void aplicarEfectosEspeciales(Carta carta, Jugador actual) {
        int siguienteIdx = (indiceTurnoActual + direccion + jugadores.size()) % jugadores.size();
        Jugador victima = jugadores.get(siguienteIdx);

        switch (carta.getTipo()) {
            case ROBA2:
                System.out.println(victima.getNombre() + " recibe 2 cartas y pierde turno.");
                penalizar(victima, 2);
                avanzarTurno(); 
                break;
            case ROBA4:
                System.out.println(victima.getNombre() + " recibe 4 cartas y pierde turno.");
                penalizar(victima, 4);
                colorActual = actual.elegirColor(scanner);
                mazoDescarte.get(mazoDescarte.size() - 1).setColor(colorActual);
                avanzarTurno();
                break;
            case SALTO:
                System.out.println("¡Salto! Turno de " + victima.getNombre() + " omitido.");
                avanzarTurno();
                break;
            case REVERSA:
                direccion *= -1;
                System.out.println("¡Reversa! El sentido del juego ha cambiado.");
                if (jugadores.size() == 2) avanzarTurno();
                break;
            case COMODIN:
                colorActual = actual.elegirColor(scanner);
                mazoDescarte.get(mazoDescarte.size() - 1).setColor(colorActual);
                break;
            default:
                break;
        }
    }

    private void penalizar(Jugador j, int cant) {
        for (int i = 0; i < cant; i++) tomarDelMazoConRetorno(j);
    }

    private Carta tomarDelMazoConRetorno(Jugador j) {
        if (!mazoPrincipal.hayCartas()) mazoPrincipal.reconstruir(mazoDescarte);
        return j.tomarCarta(mazoPrincipal);
    }

    private void verificarReglaUno(Jugador actual) {
        if (actual.getMano().size() == 1) {
            if (actual instanceof JugadorHumano) {
                System.out.print("¡Te queda una carta! Escribe 'UNO' para avisar: ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("UNO")) {
                    actual.setDijoUno(true);
                    System.out.println("¡Has dicho UNO!");
                } else {
                    System.out.println("¡Se te olvidó decir UNO! Penalización de 2 cartas.");
                    penalizar(actual, 2);
                }
            } else {
                actual.setDijoUno(true);
                System.out.println(actual.getNombre() + " dice: ¡UNO!");
            }
        }
    }
}