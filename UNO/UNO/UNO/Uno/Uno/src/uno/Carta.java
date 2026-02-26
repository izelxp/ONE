/*
 * Clase que representa una carta del juego UNO
 * 
 * Funcionalidad:
 * - Almacena color, número y visibilidad de la carta
 * - Proporciona métodos para obtener sus propiedades
 * - Verifica si la carta es válida para jugar
 */
package uno;

public class Carta {
    private String color;
    private int numero;
    private boolean visible;
    
    /**
     * Constructor de la carta
     * @param color Color de la carta (Rojo, Azul, Verde, Amarillo)
     * @param numero Número de la carta (0-9)
     */
    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
        this.visible = true;
    }
    
    public String getColor() {
        return color;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Verifica si la carta es válida para jugar
     * @param colorMesa Color de la carta en mesa
     * @param numeroMesa Número de la carta en mesa
     * @return true si coincide color o número, false si no
     */
    public boolean esValida(String colorMesa, int numeroMesa) {
        return this.color.equals(colorMesa) || this.numero == numeroMesa;
    }
    
    @Override
    public String toString() {
        return color + " " + numero;
    }
}