package de.stanchev.forestofsecrets.model;

/**
 * Repräsentiert eine zweidimensionale Position im Spielfeldraster.
 * Wird für Bewegungen, Kollisionen und Energieverbrauch verwendet.
 *
 * Das Spielfeld basiert auf festen Rastereinheiten (30 Pixel).
 *
 */
public class Position {

    private int x;
    private int y;

    /** Erstellt eine Position bei (0,0). */
    public Position() {
        this(0, 0);
    }

    /**
     * Erstellt eine Position mit festen Koordinaten.
     *
     * @param x X-Koordinate (Vielfaches von 30)
     * @param y Y-Koordinate (Vielfaches von 30)
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter- / Setter-Methoden
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    /** Setzt die X-Koordinate. */
    public void setX(int valueX) {
        this.x = valueX;
    }

    /** Setzt die Y-Koordinate. */
    public void setY(int valueY) {
        this.y = valueY;
    }

    // Positionslogik
    /**
     * Addiert eine Verschiebung zur aktuellen Position
     * und gibt eine neue Position zurück.
     *
     * @param verschiebung Bewegungsrichtung
     * @return neue Position
     */
    public Position add(Position verschiebung) {
        int neuesX = this.x + verschiebung.getX();
        int neuesY = this.y + verschiebung.getY();
        return new Position(neuesX, neuesY);
    }

    /**
     * Berechnet den Energieverbrauch einer Bewegung.
     * Der Verbrauch entspricht der Summe der absoluten
     * Verschiebungen in X- und Y-Richtung.
     *
     * @return Energieverbrauch
     */
    public int calcEnergieReduction() {
        return Math.abs(x) + Math.abs(y);
    }

    // Vergleich und Ausgabe
    /**
     * Zwei Positionen gelten als gleich,
     * wenn ihre X- und Y-Koordinaten identisch sind.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    /**
     * Liefert einen Hash-Wert basierend auf X- und Y-Koordinate.
     * Wird benötigt, damit Positionen korrekt in Collections
     * (z.B. HashSet oder HashMap) verwendet werden können.
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    /**
     * toString()-Methode
     * Liefert eine lesbare Textdarstellung der Position.
     */
    @Override
    public String toString() {
        return String.format("x: %d,\t y: %d", this.x, this.y);
    }
}
