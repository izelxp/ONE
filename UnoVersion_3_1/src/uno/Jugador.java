package uno;

import java.util.Scanner;

public abstract class Jugador {
    protected String nombre;
    protected Mano mano;
    protected boolean dijoUno;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new Mano();
        this.dijoUno = false;
    }

    public Carta tomarCarta(Mazo mazo) {
        Carta carta = mazo.tomarCarta();
        if (carta != null) mano.agregarCarta(carta);
        return carta;
    }

    public Carta jugarCarta(int indice) {
        Carta carta = mano.quitarCarta(indice);
        this.dijoUno = false;
        return carta;
    }

    public String getNombre() { return nombre; }
    public Mano getMano() { return mano; }
    public boolean isDijoUno() { return dijoUno; }
    public void setDijoUno(boolean dijoUno) { this.dijoUno = dijoUno; }

    public abstract Carta ejecutarTurno(Carta cartaMesa, Mazo mazoPrincipal, Scanner scanner);
    public abstract String elegirColor(Scanner scanner);

    @Override
    public String toString() { return nombre + " (" + mano.size() + " cartas)"; }
}