package _010_;

import board.BoardUI;
import board.UnknownElementException;

/**
 * LifeStone (grüner Lebensstein).
 * Dient als besondere Energiequelle:
 * - Setzt die Energie des Ritters auf 1500
 * - Verschwindet nach dem Einsammeln
 *
 * Die Logik wird zentral in _010_0_FoS verarbeitet.
 */
public class LifeStone extends Treasure {

    public LifeStone(Position ort) {
        super(ort, "LifeStone");
    }

    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("LifeStone", ort.getX(), ort.getY());
    }
}

