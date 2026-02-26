/*
 * Clase principal que controla la lógica del juego UNO
 * 
 * Funcionalidad:
 * - Gestiona el flujo del juego (turnos, rondas)
 * - Controla las reglas de juego (cartas válidas, robar cartas)
 * - Implementa la regla del UNO (decir UNO cuando queda 1 carta)
 * - Maneja la interacción entre jugador humano y computadora
 */
package uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuegoUno {
    private Jugador jugador;
    private Jugador maquina;
    private Mazo mazoPrincipal;
    private List<Carta> mazoDescarte;
    private boolean turnoHumano;
    private String colorActual;
    private int numeroActual;
    private Scanner scanner;

    /**
     * Constructor del juego
     * @param nombreJugador Nombre del jugador humano
     * @param scanner Scanner compartido para entrada de datos
     */
    public JuegoUno(String nombreJugador, Scanner scanner) {
        this.scanner = scanner;
        jugador = new Jugador(nombreJugador, true);
        maquina = new Jugador("Computadora", false);
        mazoPrincipal = new Mazo();
        mazoDescarte = new ArrayList<>();
        turnoHumano = true;

        System.out.println("\n" + mazoPrincipal);
        repartirCartas();
        iniciarJuego();
        jugar();
    }

    /**
     * Reparte 7 cartas iniciales a cada jugador
     */
    private void repartirCartas() {
        System.out.println("\nRepartiendo cartas...");
        for (int i = 0; i < 7; i++) {
            jugador.tomarCarta(mazoPrincipal);
            maquina.tomarCarta(mazoPrincipal);
        }
    }

    /**
     * Coloca la primera carta en el mazo de descarte
     * y establece el color y número iniciales
     */
    private void iniciarJuego() {
        Carta primeraCarta = mazoPrincipal.tomarCarta();
        if (primeraCarta != null) {
            mazoDescarte.add(primeraCarta);
            colorActual = primeraCarta.getColor();
            numeroActual = primeraCarta.getNumero();
            System.out.println("\nPRIMERA CARTA EN MESA:");
            System.out.println("   " + primeraCarta);
            System.out.println("-".repeat(50));
        }
    }

    /**
     * Toma una carta del mazo principal para el jugador indicado
     * Si el mazo está vacío, lo reconstruye desde el descarte
     * @param jugador Jugador que tomará la carta
     * @return true si pudo tomar carta, false si no
     */
    private boolean tomarDelMazo(Jugador jugador) {
        if (!mazoPrincipal.hayCartas()) {
            if (mazoPrincipal.reconstruir(mazoDescarte)) {
                Carta ultimaCarta = mazoDescarte.get(mazoDescarte.size() - 1);
                mazoDescarte.clear();
                mazoDescarte.add(ultimaCarta);
            }
        }
        return jugador.tomarCarta(mazoPrincipal) != null;
    }

    /**
     * Toma una carta del mazo y la devuelve (para evaluar si es válida)
     * @param jugador Jugador que tomará la carta
     * @return La carta tomada o null si no hay
     */
    private Carta tomarDelMazoConRetorno(Jugador jugador) {
        if (!mazoPrincipal.hayCartas()) {
            if (mazoPrincipal.reconstruir(mazoDescarte)) {
                Carta ultimaCarta = mazoDescarte.get(mazoDescarte.size() - 1);
                mazoDescarte.clear();
                mazoDescarte.add(ultimaCarta);
            }
        }
        return jugador.tomarCarta(mazoPrincipal);
    }
    
    /**
     * Aplica la penalización por no decir UNO
     * El jugador penalizado roba 2 cartas
     * @param jugadorPenalizado Jugador que no dijo UNO
     * @param jugadorQueSorprende Jugador que sorprende
     */
    private void aplicarPenalizacionUno(Jugador jugadorPenalizado, Jugador jugadorQueSorprende) {
        System.out.println("\n¡¡¡ PENALIZACIÓN !!!");
        System.out.println(jugadorPenalizado.getNombre() + " no dijo UNO y fue sorprendido por " + jugadorQueSorprende.getNombre());
        System.out.println("Debe robar 2 cartas como castigo.");
        
        for (int i = 0; i < 2; i++) {
            tomarDelMazo(jugadorPenalizado);
        }
    }
    
    /**
     * Verifica y aplica la regla del UNO
     * - Detecta si un jugador tiene 1 carta
     * - Pregunta si quiere decir UNO (si es humano)
     * - Permite al otro jugador sorprender si no dijo UNO
     * @param jugadorActual Jugador que acaba de terminar su turno
     * @param otroJugador El otro jugador (quien puede sorprender)
     */
    private void verificarReglaUno(Jugador jugadorActual, Jugador otroJugador) {
        // Si el jugador actual tiene 1 carta después de su turno
        if (jugadorActual.getMano().size() == 1) {
            System.out.println("\n¡¡¡ ATENCIÓN !!!");
            System.out.println(jugadorActual.getNombre() + " tiene UNA CARTA.");
            
            if (jugadorActual.isEsHumano()) {
                // Preguntar al jugador humano si quiere decir UNO
                System.out.print("¿Quieres decir UNO? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                
                if (respuesta.equals("s")) {
                    jugadorActual.setDijoUno(true);
                    System.out.println("¡Dijiste UNO! Estás a salvo.");
                } else {
                    jugadorActual.setDijoUno(false);
                    System.out.println("No dijiste UNO. La computadora puede sorprenderte.");
                }
            } else {
                // La máquina siempre dice UNO (juego limpio)
                jugadorActual.setDijoUno(true);
                System.out.println("La computadora dice UNO.");
            }
        }
        
        // Verificar si el otro jugador puede sorprender por no decir UNO
        if (jugadorActual.getMano().size() == 1 && !jugadorActual.isDijoUno()) {
            if (otroJugador.isEsHumano()) {
                // El jugador humano puede sorprender a la máquina
                System.out.print("\n¿Quieres sorprender a la computadora por no decir UNO? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                
                if (respuesta.equals("s")) {
                    aplicarPenalizacionUno(jugadorActual, otroJugador);
                }
            } else {
                // La máquina siempre sorprende al jugador si no dijo UNO
                System.out.println("\n¡La computadora te ha sorprendido por no decir UNO!");
                aplicarPenalizacionUno(jugadorActual, maquina);
            }
        }
    }

    /**
     * Gestiona el turno del jugador humano
     * @return true si el jugador ganó al quedar sin cartas, false si no
     */
    private boolean turnoJugador() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TURNO DEL JUGADOR");
        System.out.println("Carta en mesa: " + mazoDescarte.get(mazoDescarte.size() - 1));

        jugador.getMano().mostrar(jugador.getNombre());
        List<Integer> cartasValidas = jugador.getMano()
                .obtenerCartasValidas(colorActual, numeroActual);

        if (!cartasValidas.isEmpty()) {
            System.out.println("\nCartas válidas: " + cartasValidas);

            while (true) {
                try {
                    System.out.print("\nElige una carta (número) o 't' para tomar del mazo: ");
                    String opcion = scanner.nextLine();

                    if (opcion.equalsIgnoreCase("t")) {
                        System.out.println("  Tomaste una carta del mazo (pierdes el turno)");
                        tomarDelMazo(jugador);
                        
                        // Verificar regla UNO después de tomar carta
                        verificarReglaUno(jugador, maquina);
                        return false;
                    }

                    int indice = Integer.parseInt(opcion);
                    if (cartasValidas.contains(indice)) {
                        Carta cartaJugada = jugador.jugarCarta(indice);
                        mazoDescarte.add(cartaJugada);
                        colorActual = cartaJugada.getColor();
                        numeroActual = cartaJugada.getNumero();
                        System.out.println("  Jugaste: " + cartaJugada + "!");
                        
                        // Verificar si ganó
                        boolean gano = jugador.getMano().estaVacia();
                        
                        // Verificar regla UNO si no ganó
                        if (!gano) {
                            verificarReglaUno(jugador, maquina);
                        }
                        
                        return gano;
                    } else {
                        System.out.println("  Error: Esa carta no es válida. Elige una carta válida o 't'");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  Error: Opción inválida. Intenta de nuevo");
                }
            }
        } else {
            System.out.println("\nNo tienes cartas válidas. Debes tomar UNA carta.");
            System.out.println("Presiona ENTER para tomar una carta...");
            scanner.nextLine();

            Carta cartaRobada = tomarDelMazoConRetorno(jugador);

            if (cartaRobada != null) {
                System.out.println("  Tomaste: " + cartaRobada);

                if (cartaRobada.getColor().equals(colorActual) ||
                        cartaRobada.getNumero() == numeroActual) {

                    System.out.println("  ¡La carta robada ES VÁLIDA! Se juega automáticamente.");
                    int ultimoIndice = jugador.getMano().size() - 1;
                    Carta cartaJugada = jugador.jugarCarta(ultimoIndice);
                    mazoDescarte.add(cartaJugada);
                    colorActual = cartaJugada.getColor();
                    numeroActual = cartaJugada.getNumero();
                    System.out.println("  Jugaste la carta robada: " + cartaJugada + "!");
                    
                    // Verificar si ganó
                    boolean gano = jugador.getMano().estaVacia();
                    
                    // Verificar regla UNO si no ganó
                    if (!gano) {
                        verificarReglaUno(jugador, maquina);
                    }
                    
                    return gano;
                } else {
                    System.out.println("  La carta robada NO es válida. Pasas el turno.");
                    
                    // Verificar regla UNO después de tomar carta
                    verificarReglaUno(jugador, maquina);
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * Gestiona el turno de la computadora
     * @return true si la computadora ganó al quedar sin cartas, false si no
     */
    private boolean turnoMaquina() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TURNO DE LA COMPUTADORA");
        System.out.println("Carta en mesa: " + mazoDescarte.get(mazoDescarte.size() - 1));

        List<Integer> cartasValidas = maquina.getMano()
                .obtenerCartasValidas(colorActual, numeroActual);

        if (!cartasValidas.isEmpty()) {
            int indice = cartasValidas.get(0);
            Carta cartaJugada = maquina.jugarCarta(indice);
            mazoDescarte.add(cartaJugada);
            colorActual = cartaJugada.getColor();
            numeroActual = cartaJugada.getNumero();
            System.out.println("  La computadora juega: " + cartaJugada + "!");
            
            // Verificar si ganó
            boolean gano = maquina.getMano().estaVacia();
            
            // Verificar regla UNO si no ganó
            if (!gano) {
                verificarReglaUno(maquina, jugador);
            }
            
            return gano;
        } else {
            System.out.println("  La computadora no tiene cartas válidas. Toma UNA carta.");

            Carta cartaRobada = tomarDelMazoConRetorno(maquina);

            if (cartaRobada != null) {
                System.out.println("  La computadora tomó: " + cartaRobada);

                if (cartaRobada.getColor().equals(colorActual) ||
                        cartaRobada.getNumero() == numeroActual) {

                    System.out.println("  ¡La computadora juega la carta robada!");
                    int ultimoIndice = maquina.getMano().size() - 1;
                    Carta cartaJugada = maquina.jugarCarta(ultimoIndice);
                    mazoDescarte.add(cartaJugada);
                    colorActual = cartaJugada.getColor();
                    numeroActual = cartaJugada.getNumero();
                    
                    // Verificar si ganó
                    boolean gano = maquina.getMano().estaVacia();
                    
                    // Verificar regla UNO si no ganó
                    if (!gano) {
                        verificarReglaUno(maquina, jugador);
                    }
                    
                    return gano;
                } else {
                    System.out.println("  La carta robada no es válida. Pierde turno.");
                    
                    // Verificar regla UNO después de tomar carta
                    verificarReglaUno(maquina, jugador);
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * Muestra el estado actual del juego (cartas en mazos y jugadores)
     */
    private void mostrarEstado() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("Cartas en mazo principal: " + mazoPrincipal.size());
        System.out.println("Cartas en mazo descarte: " + mazoDescarte.size());
        System.out.println(jugador);
        System.out.println(maquina);
        System.out.println("-".repeat(50));
    }

    /**
     * Bucle principal del juego
     * Controla las rondas, turnos y determina el ganador
     */
    public void jugar() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("UNO - VERSIÓN NÚMEROS (0-9)");
        System.out.println("=".repeat(60));
        System.out.println("\nREGLAS:");
        System.out.println("• Se juega con cartas numéricas del 0 al 9");
        System.out.println("• Puedes jugar cartas del mismo color o mismo número");
        System.out.println("• El primero en quedarse sin cartas gana");
        System.out.println("• Si no tienes carta, tomas UNA del mazo");
        System.out.println("• Si la carta robada es válida, SE JUEGA AUTOMÁTICAMENTE");
        System.out.println("• NUEVA REGLA: Si tienes UNA carta y NO dices UNO, te pueden sorprender");
        System.out.println("  y robarás 2 cartas como penalización.");

        boolean juegoTerminado = false;
        int ronda = 1;

        while (!juegoTerminado) {
            System.out.println("\n" + "-".repeat(10) + " RONDA " + ronda + " " + "-".repeat(10));
            mostrarEstado();

            if (turnoHumano) {
                juegoTerminado = turnoJugador();
                if (!juegoTerminado) {
                    turnoHumano = false;
                }
            } else {
                juegoTerminado = turnoMaquina();
                if (!juegoTerminado) {
                    turnoHumano = true;
                }
            }

            if (juegoTerminado) {
                String ganador;
                if (!turnoHumano) {
                    ganador = "LA COMPUTADORA";
                } else {
                    ganador = "TÚ";
                }

                System.out.println("\n" + "=".repeat(15));
                System.out.println("¡¡¡ " + ganador + " HAS GANADO !!!");
                System.out.println("=".repeat(15));

                System.out.println("\nMANOS FINALES:");
                jugador.getMano().mostrar(jugador.getNombre());
                maquina.getMano().mostrar(maquina.getNombre());

                System.out.print("\n¿Quieres jugar otra vez? (s/n): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    mazoPrincipal = new Mazo();
                    mazoDescarte.clear();
                    jugador = new Jugador(jugador.getNombre(), true);
                    maquina = new Jugador("Computadora", false);
                    turnoHumano = true;
                    System.out.println("\n" + mazoPrincipal);
                    repartirCartas();
                    iniciarJuego();
                    juegoTerminado = false;
                    ronda = 0;
                } else {
                    System.out.println("\n¡Gracias por jugar!");
                }
            }

            ronda++;
        }
    }
}