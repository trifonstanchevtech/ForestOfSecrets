package _010_;

import board.BoardUI;
import board.UnknownElementException;

/**
 * Spielfigur (Ritter).
 * Der Ritter bewegt sich auf dem Raster, verbraucht Energie und kann Gegenstände finden.
 * Wenn er das Schwert findet, kann er später den Drachen besiegen.
 */
public class Knight extends GameObject {
    private final String name;
    private final int alter;
    private int energie;

    private boolean hasSword;

    public Knight(String name, int alter, Position ort) {
        super(ort);
        this.name = name;
        this.alter = alter;
        this.energie = 1500;
        this.hasSword = false;
    }

    // Getter-Methoden
    public String getName() {
        return name;
    }

    public int getAlter() {
        return alter;
    }

    public int getEnergie() {
        return energie;
    }

    public boolean hasSword() {
        return hasSword;
    }

    public Position getOrt() {
        return ort;
    }

    // Setter-Methoden
    public void setEnergie(int energie) {
        this.energie = energie;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
    }

    public void setOrt(Position ort) {
        this.ort = ort;
    }

    /**
     * Bewegt den Ritter um die angegebene Verschiebung und reduziert dabei die Energie.
     * Bewegung findet nur statt, wenn genug Energie vorhanden ist.
     */
    public void move(Position verschiebung) {
        int verbrauch = verschiebung.calcEnergieReduction();
        if (energie < verbrauch) {
            return;  // Keine Bewegung bei unzureichender Energie
        }

        energie -= verbrauch;
        ort = ort.add(verschiebung);
    }

    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("Knight", ort.getX(), ort.getY());
    }
}






























