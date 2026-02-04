package de.stanchev.forestofsecrets.model;

import board.BoardUI;
import board.UnknownElementException;

/**
 * Abstrakte Basisklasse für alle Schatz-Objekte im Spiel.
 * Schätze erscheinen auf dem Spielfeld als Baum
 * und werden bei Fund separat dargestellt
 * (z.B. Schwert, Trank, Schatztruhe).
 *
 * @author Trifon Stanchev
 * @version 1.0
 */
public abstract class Treasure extends GameObject {

    // Name des Schatzes: (z.B. "Sword", "LifeStone", ...)
    private final String name;

    public Treasure(Position ort, String name) {
        super(ort);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Zeichnet den Schatz auf dem Spielfeld.
     * Während des Spiels wird der Schatz als Baum dargestellt.
     */
    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("Tree", ort.getX(), ort.getY());
    }

    /**
     * Zeichnet das echte Schatzsymbol, z.B. in der Fundzone
     * unterhalb des Spielfelds.
     */
    public void show(BoardUI board, Position position) throws UnknownElementException {
        board.draw(name, position.getX(), position.getY());
    }

    /**
     * Gibt an, ob dieses Objekt ein Gewinnobjekt ist.
     * Wird von speziellen Schätzen (z.B. Schatztruhe) überschrieben.
     */
    public boolean isWinningTreasure() {
        return false;
    }
}
