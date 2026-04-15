package uno;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== BIENVENIDO A UNO V3 ===");
        String nombre = pedirNombreValido(scanner);
        int numBots = pedirCantidadBots(scanner);

        System.out.println("\n¡Hola " + nombre + "! Preparando el juego contra " + numBots + " oponentes...");
        JuegoUno jugar = new JuegoUno(nombre, numBots, scanner);
    }
    
    private static String pedirNombreValido(Scanner scanner) {
        String nombre;
        while (true) {
            System.out.print("\n¿Cómo te llamas? ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("  Error: El nombre no puede estar vacío.");
            } else if (nombre.equalsIgnoreCase("Computadora")) {
                System.out.println("  Error: Nombre reservado para la máquina.");
            } else {
                break;
            }
        }
        return nombre;
    }

    private static int pedirCantidadBots(Scanner scanner) {
        int bots = 0;
        while (true) {
            System.out.print("¿Contra cuántos bots quieres jugar? (1 a 3): ");
            try {
                bots = Integer.parseInt(scanner.nextLine().trim());
                if (bots >= 1 && bots <= 3) {
                    break;
                } else {
                    System.out.println("  Error: Debes elegir entre 1 y 3 bots para cumplir la regla de 2 a 4 jugadores.");
                }
            } catch (NumberFormatException e) { 
                System.out.println("  Excepción capturada: Entrada inválida. Por favor, ingresa un número entero.");
            }
        }
        return bots;
    }
}