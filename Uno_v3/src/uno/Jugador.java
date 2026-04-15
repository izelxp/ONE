package uno;

public class Jugador {

	  private String nombre;
	    private Mano mano;
	    private boolean esHumano;
	    private boolean dijoUno;  // Indica si el jugador dijo UNO cuando le queda 1 carta
	    
	    /**
	     * Constructor del jugador
	     * @param nombre Nombre del jugador
	     * @param esHumano true si es humano, false si es computadora
	     */
	    public Jugador(String nombre, boolean esHumano) {
	        this.nombre = nombre;
	        this.mano = new Mano();
	        this.esHumano = esHumano;
	        this.dijoUno = false;
	    }
	    
	    /**
	     * Toma una carta del mazo y la agrega a la mano
	     * @param mazo Mazo de donde tomar la carta
	     * @return La carta tomada o null si no hay
	     */
	    public Carta tomarCarta(Mazo mazo) {
	        Carta carta = mazo.tomarCarta();
	        if (carta != null) {
	            mano.agregarCarta(carta);
	            if (esHumano) {
	                System.out.println("  Tomaste: " + carta);
	            }
	        }
	        return carta;
	    }
	    
	    /**
	     * Juega una carta de la mano (la elimina)
	     * @param indice Índice de la carta en la mano
	     * @return La carta jugada o null si el índice es inválido
	     */
	    public Carta jugarCarta(int indice) {
	        Carta carta = mano.quitarCarta(indice);
	        // Al jugar una carta, restablecemos el estado de dijoUno
	        this.dijoUno = false;
	        return carta;
	    }
	    
	    public String getNombre() {
	        return nombre;
	    }
	    
	    public Mano getMano() {
	        return mano;
	    }
	    
	    public boolean isEsHumano() {
	        return esHumano;
	    }
	    
	    public boolean isDijoUno() {
	        return dijoUno;
	    }
	    
	    public void setDijoUno(boolean dijoUno) {
	        this.dijoUno = dijoUno;
	    }
	    
	    @Override
	    public String toString() {
	        return nombre + " (" + mano.size() + " cartas)";
	    }
	
}
