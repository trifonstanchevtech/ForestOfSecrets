package de.stanchev.forestofsecrets.model;

import board.BoardUI;
import board.UnknownElementException;

/**
 * Abstrakte Basisklasse für alle Schatz-Objekte im Spiel.
 * Schätze erscheinen auf dem Spielfeld als Baum
 * und werden bei Fund separat dargestellt.
 *
 * @author Trifon Stanchev
 * @version 1.0
 */

public abstract class Treasure extends GameObject {

    private final String name; // "Sword", "LifeStone", ...

    public Treasure(Position ort, String name) {
        super(ort);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Auf dem Spielfeld immer als Baum
    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("Tree", ort.getX(), ort.getY());
    }

    // Außerhalb: echtes Symbol (z.B. unter dem Grid)
    public void show(BoardUI board, Position position) throws UnknownElementException {
        board.draw(name, position.getX(), position.getY());
    }

    // Für Schloss-Gewinn
    public boolean isWinningTreasure() {
        return false;
    }
}
