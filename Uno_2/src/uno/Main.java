/*
fra * Clase principal que inicia el juego UNO
 * 
 * Funcionalidad:
 * - Muestra el título y las reglas del juego
 * - Solicita el nombre del jugador
 * - VALIDA que el nombre no sea "Computadora" (reservado para la máquina)
 * - Crea una instancia del juego con el scanner compartido
 * 
 * @author franc
 */
package uno;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("============================================================");
        System.out.println("       UNO - VERSION NUMEROS (0-9) & CARTAS ESPECIALES");
        System.out.println("============================================================");
        System.out.println("\nREGLAS:");
        System.out.println("• Se juega con cartas numéricas del 0 al 9");
        System.out.println("• Puedes jugar cartas del mismo color o mismo número");
        System.out.println("• El primero en quedarse sin cartas gana");
        System.out.println("• Si no tienes carta, tomas UNA del mazo");
        System.out.println("• Si la carta robada es válida, SE JUEGA AUTOMÁTICAMENTE");
        System.out.println("• NUEVA REGLA: Si tienes UNA carta y NO dices UNO, te pueden sorprender");
        System.out.println("  y robarás 2 cartas como penalización.");
        System.out.println("-".repeat(60));
        
        String nombre = pedirNombreValido(scanner);

        System.out.println("\n¡Hola " + nombre + "! Preparando el juego...");
        
        JuegoUno juego = new JuegoUno(nombre, scanner);
        
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
