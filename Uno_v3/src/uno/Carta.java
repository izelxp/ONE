package uno;



public class Carta {


	public enum Tipo{
		NUMERO,
		REVERSA,
		SALTO,
		ROBA2,
		ROBA4,
		COMODIN
	}
	
    private String color;
    private Tipo tipo;
    private int numero;
    private boolean visible;
    
    /**
     * Constructor de la carta tipo NUMERO
     * @param color Color de la carta (Rojo, Azul, Verde, Amarillo)
     * @param numero Número de la carta (0-9)
     */
    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
        this.tipo = Tipo.NUMERO;
        this.visible = true;
    }
    
    //Constructor para Carta especial
    public Carta(String color, Tipo tipo) {
    	this.color = color;
    	this.tipo = tipo;
    	this.numero = -1;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
    	this.color = color;
    }
    
    public Tipo getTipo(){
    	return tipo;
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
    public boolean esValida(Carta otra) {
    	// si la carta es un comodin o un roba4 es valida 
    	if (this.tipo == Tipo.COMODIN || this.tipo == Tipo.ROBA4)  
    		return true;
    	// si el color es igual a la otra carta es valida
    	if (this.color.equals(otra.color))
    		return true;
    	// si el numero de carta es igual al numero de la otra carta es valida
    	if (this.tipo == Tipo.NUMERO && otra.tipo == Tipo.NUMERO)
    		return this.numero == otra.numero;
        
    	return this.tipo == otra.tipo;
    }
    
    @Override
    public String toString() {
    	if (tipo == Tipo.NUMERO)
    		return color + " " + numero;
    	return color + " " + tipo;        
    }
	
}
