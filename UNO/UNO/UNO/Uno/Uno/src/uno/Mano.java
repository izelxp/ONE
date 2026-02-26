/*
 * Clase que representa la mano de cartas de un jugador
 * 
 * Funcionalidad:
 * - Almacena la lista de cartas del jugador
 * - Agrega y quita cartas de la mano
 * - Busca cartas válidas para jugar
 * - Muestra las cartas en consola
 */
package uno;

import java.util.ArrayList;
import java.util.List;

public class Mano {
    private List<Carta> cartas;
    
    public Mano() {
        cartas = new ArrayList<>();
    }
    
    /**
     * Agrega una carta a la mano
     * @param carta Carta a agregar
     * @throws IllegalArgumentException si la carta es nula
     */
    public void agregarCarta(Carta carta) {
        if (carta == null) {
            throw new IllegalArgumentException("Error: No se puede agregar una carta nula");
        }
        cartas.add(carta);
    }
    
    /**
     * Quita y devuelve una carta de la mano
     * @param indice Índice de la carta a quitar
     * @return La carta quitada o null si el índice es inválido
     */
    public Carta quitarCarta(int indice) {
        if (indice >= 0 && indice < cartas.size()) {
            return cartas.remove(indice);
        }
        return null;
    }
    
    /**
     * Obtiene los índices de las cartas válidas para jugar
     * @param colorActual Color de la carta en mesa
     * @param numeroActual Número de la carta en mesa
     * @return Lista de índices de cartas válidas
     */
    public List<Integer> obtenerCartasValidas(String colorActual, int numeroActual) {
        List<Integer> validas = new ArrayList<>();
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i).esValida(colorActual, numeroActual)) {
                validas.add(i);
            }
        }
        return validas;
    }
    
    /**
     * Muestra las cartas de la mano en consola
     * @param nombreJugador Nombre del jugador dueño de la mano
     */
    public void mostrar(String nombreJugador) {
        System.out.println("\nMano de " + nombreJugador + ":");
        if (cartas.isEmpty()) {
            System.out.println("  (vacía)");
        } else {
            for (int i = 0; i < cartas.size(); i++) {
                System.out.println("  [" + i + "] " + cartas.get(i));
            }
        }
    }
    
    /**
     * Verifica si la mano está vacía
     * @return true si no hay cartas, false si hay
     */
    public boolean estaVacia() {
        return cartas.isEmpty();
    }
    
    /**
     * Obtiene la cantidad de cartas en la mano
     * @return Número de cartas
     */
    public int size() {
        return cartas.size();
    }
}