package uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
	
    private List<Carta> cartas;
    
    public Mazo() {
        cartas = new ArrayList<>();
        inicializar();
        barajar();
    }
    
    private void inicializar() {
        String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
        
        for (String color : colores) {
            cartas.add(new Carta(color, 0));
            
            for (int numero = 1; numero <= 9; numero++){
                cartas.add(new Carta(color, numero));
                cartas.add(new Carta(color, numero));
            }

            for (int i = 1; i <= 2; i++) {
                cartas.add(new Carta(color, Carta.Tipo.SALTO));
                cartas.add(new Carta(color, Carta.Tipo.REVERSA));
                cartas.add(new Carta(color, Carta.Tipo.ROBA2));
            }
        }  
        
        for (int numero = 1; numero <= 4; numero++){
            cartas.add(new Carta("Negro", Carta.Tipo.ROBA4));
            cartas.add(new Carta("Negro", Carta.Tipo.COMODIN));
        } 
    }
    
    public void barajar() {
        Collections.shuffle(cartas);
    }
    
    public Carta tomarCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(0);
    }
    
    public boolean hayCartas() {
        return !cartas.isEmpty();
    }
    
    public int size() {
        return cartas.size();
    }
    
    public boolean reconstruir(List<Carta> descarte) {
        if (descarte.size() <= 1) {
            return false;
        }
        
        Carta ultima = descarte.remove(descarte.size() - 1);
        
        // CORRECCIÓN DE BUG: Restaurar explícitamente el color "Negro"
        for (Carta carta : descarte) {
            if (carta.getTipo() == Carta.Tipo.COMODIN || carta.getTipo() == Carta.Tipo.ROBA4) {
                carta.setColor("Negro");
            }
        }
        
        cartas.addAll(descarte);
        descarte.clear();
        descarte.add(ultima);
        barajar();
        
        System.out.println("  Reconstruyendo mazo desde descarte y limpiando colores de comodines...");
        return true;
    }
    
    @Override
    public String toString() {
        return "Mazo con " + cartas.size() + " cartas";
    }
}