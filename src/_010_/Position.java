package _010_;

/**
 * Repräsentiert eine zweidimensionale Position im Spielfeldraster.
 * Wird für Bewegungen, Kollisionen und Energieverbrauch verwendet.
 *
 * Das Spielfeld basiert auf festen Rastereinheiten (30 Pixel).
 *
 * @author Trifon Stanchev
 * @version 1.0
 */

public class Position {

    private int x;
    private int y;

    // Default-Konstruktor
    public Position() {
        this(0, 0);
    }

    // Parametrisiereter Konstruktor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter-Methoden
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // Setter-Methoden
    // valueX/valueY statt x/y sofort um Verwechslung zu vermeiden
    public void setX(int valueX) {
        this.x = valueX;
    }

    public void setY(int valueY) {
        this.y = valueY;
    }

    // Methode zum Addieren von Positionen (erzeugt neues Objekt)
    public Position add(Position verschiebung) {
        int neuesX = this.x + verschiebung.getX();
        int neuesY = this.y + verschiebung.getY();
        return new Position(neuesX, neuesY);
    }

    // Methode zur Berechnung des Energieverbrauchs
    public int calcEnergieReduction() {
        return Math.abs(x) + Math.abs(y);
    }

    // Neue Methode: Kollision
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    // toString-Methode
    @Override
    public String toString() {
        return String.format("x: %d,\t y: %d", this.x, this.y);
    }
}
