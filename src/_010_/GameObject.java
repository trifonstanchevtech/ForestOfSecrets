package _010_;

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

    // Abstrakte draw-Methode (muss implementiert werden)
    public abstract void draw(BoardUI board) throws UnknownElementException;
}
