package de.stanchev.forestofsecrets.model;

import board.BoardUI;
import board.UnknownElementException;

/**
 * Abstrakte Basisklasse für alle darstellbaren Elemente im Spiel.
 * Jedes Element besitzt eine Position und kann auf dem Board gezeichnet werden.
 *
 * @author Trifon Stanchev
 * @version 1.0
 */

public abstract class GameObject {
    protected Position ort;   // protected = Vererbungshierarchie

    // Konstruktor-Methoden
    // Default-Konstruktor
    public GameObject() {
        this.ort = new Position(0, 0);
    }

    // Konstruktor, der nur die Position erzeugt
    public GameObject(Position ort) {
        this.ort = ort;
    }

    // protected Getter-/Setter-Methoden (gekapselt nur für Subklassen)
    protected Position getOrt() {
        return ort;
    }
    protected void setOrt(Position ort) {
        this.ort = ort;
    }

    /**
     * Prüft Kollision mit anderem GameObject für Controller-Klassen (Game.core).
     *
     * Löst protected-Zugriffsproblem: public API -> intern protected getOrt().
     *
     * Beispiel:
     * if (myKnight.isAtSameLocation(gameObject)) { ... }
     *
     * @param other das zu prüfende GameObject
     * @return true bei Kollision
     */
    public boolean isAtSameLocation(GameObject other) {
        if (other == null) return false;
        return this.getOrt().equals(other.getOrt());
    }

    // Abstrakte draw-Methode (muss implementiert werden)
    public abstract void draw(BoardUI board) throws UnknownElementException;
}
