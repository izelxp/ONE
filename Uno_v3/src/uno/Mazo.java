package uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uno.Carta.Tipo;

public class Mazo {
	
private List<Carta> cartas;
    
    public Mazo() {
        cartas = new ArrayList<>();
        inicializar();
        barajar();
    }
    
    /**
     * Inicializa el mazo con las cartas del UNO (versión números)
     * - 4 colores: Rojo, Azul, Verde, Amarillo
     * - Por color: 1 carta 0 + 2 cartas de cada número 1-9 = 19 cartas
     * - Total: 4 × 19 = 76 cartas
     */
    private void inicializar() {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        
        for (String color : colores) {
            // Un 0 por color
            cartas.add(new Carta(color, 0));
            
            // Dos cartas de cada número del 1 al 9
            //for (int numero = 1; numero <= 9; numero++) {
                //cartas.add(new Carta(color, numero));
                //cartas.add(new Carta(color, numero));
            //}
            //METODO PARA CREAR 2 CARTAS DE DEL 1 AL 9 
            //MAS 2 CARTAS DE TIPO: SALTO, REVERSA Y TOMA 2 
            //ULTIMA MODIFICACIÓN 8-MARZO-26 BY JAV 
            for (int numero = 1; numero <= 9; numero++){
                cartas.add(new Carta(color, numero));
                cartas.add(new Carta(color, numero));

            }

            for (int i = 1; i <= 2; i++) {
                cartas.add(new Carta(color, Tipo.SALTO));
                cartas.add(new Carta(color, Tipo.REVERSA));
                cartas.add(new Carta(color, Tipo.ROBA2));
            }
        }  
        //CREAMOS 4 CARTAS TIPO COMODÍN Y 4 CARTAS TIPO TOMA4
        //ULTIMA MODIFICACIÓN 8-MARZO-26 BY JAV 
        for (int numero = 1; numero <= 4; numero++){
            cartas.add(new Carta("Negro", Tipo.ROBA4));
            cartas.add(new Carta("Negro", Tipo.COMODIN));
        } 
    }
    
    /**
     * Baraja las cartas del mazo aleatoriamente
     */
    public void barajar() {
        Collections.shuffle(cartas);
    }
    
    /**
     * Toma la primera carta del mazo (la elimina)
     * @return La carta tomada o null si el mazo está vacío
     */
    public Carta tomarCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(0);
    }
    
    /**
     * Verifica si quedan cartas en el mazo
     * @return true si hay cartas, false si está vacío
     */
    public boolean hayCartas() {
        return !cartas.isEmpty();
    }
    
    /**
     * Obtiene la cantidad de cartas en el mazo
     * @return Número de cartas
     */
    public int size() {
        return cartas.size();
    }
    
    /**
     * Reconstruye el mazo a partir de las cartas del descarte
     * @param descarte Lista de cartas en el mazo de descarte
     * @return true si se reconstruyó, false si no hay suficientes cartas
     */
    public boolean reconstruir(List<Carta> descarte) {
        if (descarte.size() <= 1) {
            return false;
        }
        
        Carta ultima = descarte.remove(descarte.size() - 1);
        cartas.addAll(descarte);
        descarte.clear();
        descarte.add(ultima);
        barajar();
        
        System.out.println("  Reconstruyendo mazo desde descarte...");
        return true;
    }
    
    @Override
    public String toString() {
        return "Mazo con " + cartas.size() + " cartas";
    }

}
