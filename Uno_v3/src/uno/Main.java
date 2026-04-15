package uno;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        
        String nombre = pedirNombreValido(scanner);

        System.out.println("\n¡Hola " + nombre + "! Preparando el juego...");
        
        JuegoUno jugar = new JuegoUno(nombre, scanner);
        
        //scanner.close();
    }
    
    /**
     * Solicita y valida que el nombre del jugador no sea "Computadora"
     * @param scanner Scanner para entrada de datos
     * @return Nombre válido del jugador
     */
    private static String pedirNombreValido(Scanner scanner) {
        String nombre;
        boolean nombreValido = false;
        
        do {
            System.out.print("\n¿Como te llamas? ");
            nombre = scanner.nextLine().trim();
            
            // Validar que no esté vacío
            if (nombre.isEmpty()) {
                System.out.println("  Error: El nombre no puede estar vacío. Intenta de nuevo.");
                continue;
            }
            
            // Validar que no sea "Computadora" (insensible a mayúsculas/minúsculas)
            if (nombre.equalsIgnoreCase("Computadora")) {
                System.out.println("  Error: No puedes llamarte 'Computadora'. Ese nombre está reservado para la máquina.");
                System.out.println("  Por favor, elige otro nombre.");
                continue;
            }
            
            // Si pasa todas las validaciones
            nombreValido = true;
            
        } while (!nombreValido);
        
        return nombre;
    }
	
}
