package uno;

import java.util.List;
import java.util.Scanner;

public class JugadorHumano extends Jugador {

    public JugadorHumano(String nombre) {
        super(nombre);
    }

    @Override
    public Carta ejecutarTurno(Carta cartaMesa, Mazo mazoPrincipal, Scanner scanner) {
        getMano().mostrar(getNombre());
        List<Integer> validas = getMano().obtenerCartasValidas(cartaMesa);

        if (validas.isEmpty()) {
            System.out.println("No tienes cartas válidas. Tienes que robar...");
            return null; 
        }

        while (true) {
            System.out.print("Elige índice de carta o 't' para robar: ");
            String op = scanner.nextLine();
            if (op.equalsIgnoreCase("t")) return null;
            
            try {
                int idx = Integer.parseInt(op);
                if (validas.contains(idx)) {
                    return jugarCarta(idx);
                } else {
                    System.out.println("Esa carta no es válida para jugar ahora.");
                }
            } catch (NumberFormatException e) { 
                System.out.println("Entrada inválida. Ingresa un número."); 
            }
        }
    }

    @Override
    public String elegirColor(Scanner scanner) {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        System.out.println("Elige color: 1.Rojo, 2.Azul, 3.Verde, 4.Amarillo");
        while(true) {
            try {
                int op = Integer.parseInt(scanner.nextLine());
                if(op >= 1 && op <= 4) return colores[op-1];
            } catch(NumberFormatException e) {}
            System.out.print("Intenta de nuevo (1-4): ");
        }
    }
}