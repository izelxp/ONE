package uno;

import java.util.ArrayList;
import java.util.List;

public class Mano {
    private List<Carta> cartas;
    
    public Mano() { cartas = new ArrayList<>(); }
    
    public void agregarCarta(Carta carta) {
        if (carta == null) throw new IllegalArgumentException("Error: No se puede agregar carta nula");
        cartas.add(carta);
    }
    
    public Carta quitarCarta(int indice) {
        if (indice >= 0 && indice < cartas.size()) return cartas.remove(indice);
        return null;
    }
    
    public List<Integer> obtenerCartasValidas(Carta cartaMesa) {
        List<Integer> validas = new ArrayList<>();
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i).esValida(cartaMesa)) validas.add(i);
        }
        return validas;
    }
    
    public void mostrar(String nombreJugador) {
        System.out.println("\nMano de " + nombreJugador + ":");
        if (cartas.isEmpty()) System.out.println("  (vacía)");
        else {
            for (int i = 0; i < cartas.size(); i++) {
                System.out.println("  [" + i + "] " + cartas.get(i));
            }
        }
    }
    
    public boolean estaVacia() { return cartas.isEmpty(); }
    public int size() { return cartas.size(); }
}