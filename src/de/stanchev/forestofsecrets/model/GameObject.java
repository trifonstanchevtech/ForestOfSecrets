package de.stanchev.forestofsecrets.model;

import board.BoardUI;
import board.UnknownElementException;

/**
 * Abstrakte Basisklasse für alle darstellbaren Objekte im Spiel.
 * Jedes GameObject besitzt eine Position im Spielfeld
 * und kann auf dem Board gezeichnet werden.
 *
 * @author Trifon Stanchev
 * @version 1.0
 */
public abstract class GameObject {

    // Position des Objekts im Spielfeld
    protected Position ort;

    // Standardposition (0,0)
    public GameObject() {
        this.ort = new Position(0, 0);
    }

    // Objekt mit Startposition
    public GameObject(Position ort) {
        this.ort = ort;
    }

    // Zugriff auf die Position (nur für Unterklassen)
    protected Position getOrt() {
        return ort;
    }

    protected void setOrt(Position ort) {
        this.ort = ort;
    }

    /**
     * Prüft, ob sich dieses Objekt an derselben Position
     * wie ein anderes GameObject befindet.
     *
     * Wird für Kollisionen im Spiel verwendet.
     *
     * @param other anderes Objekt im Spielfeld
     * @return true, wenn beide Objekte dieselbe Position haben
     */
    public boolean isAtSameLocation(GameObject other) {
        if (other == null) return false;
        return this.getOrt().equals(other.getOrt());
    }

    /**
     * Zeichnet das Objekt auf dem Spielfeld.
     * Muss von jeder Unterklasse implementiert werden.
     */
    public abstract void draw(BoardUI board) throws UnknownElementException;
}
