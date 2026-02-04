package de.stanchev.forestofsecrets.entities;

import board.BoardUI;
import board.UnknownElementException;
import de.stanchev.forestofsecrets.model.GameObject;
import de.stanchev.forestofsecrets.model.Position;

/**
 * Spielfigur des Spiels (Ritter).
 *
 * Der Ritter bewegt sich auf dem Spielfeldraster, verbraucht dabei Energie
 * und kann verschiedene Gegenstände finden.
 *
 * Findet er das Schwert, ist er in der Lage,
 * den Drachen zu besiegen.
 */
public class Knight extends GameObject {

    private final String name;     // Name des Ritters
    private final int alter;       // Alter des Ritters
    private int energie;           // Aktuelle Energie

    private boolean hasSword;      // Status: besitzt der Ritter ein Schwert?

    public Knight(String name, int alter, Position ort) {
        super(ort);
        this.name = name;
        this.alter = alter;
        this.energie = 1500;
        this.hasSword = false;
    }

    // Getter
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

    // Setter
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
     * Bewegt den Ritter um die angegebene Verschiebung
     * und reduziert dabei die Energie.
     *
     * Die Bewegung findet nur statt,
     * wenn ausreichend Energie vorhanden ist.
     */
    public void move(Position verschiebung) {
        int verbrauch = verschiebung.calcEnergieReduction();
        if (energie < verbrauch) {
            return;
        }

        energie -= verbrauch;
        ort = ort.add(verschiebung);
    }

    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("Knight", ort.getX(), ort.getY());
    }
}
