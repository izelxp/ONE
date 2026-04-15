package uno;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class JugadorComputadora extends Jugador {

    public JugadorComputadora(String nombre) {
        super(nombre);
    }

    @Override
    public Carta ejecutarTurno(Carta cartaMesa, Mazo mazoPrincipal, Scanner scanner) {
        List<Integer> validas = getMano().obtenerCartasValidas(cartaMesa);
        if (validas.isEmpty()) return null; 
        
        Carta jugada = jugarCarta(validas.get(0));
        System.out.println(getNombre() + " juega: " + jugada);
        return jugada;
    }

    @Override
    public String elegirColor(Scanner scanner) {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        String colorElegido = colores[new Random().nextInt(colores.length)];
        System.out.println(getNombre() + " ha elegido el color: " + colorElegido);
        return colorElegido;
    }
}