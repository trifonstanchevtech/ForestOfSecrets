package de.stanchev.forestofsecrets.items;

import board.BoardUI;
import board.UnknownElementException;
import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Magischer Runentrank (lila mit brauner Rune).
 * Funktioniert wie ein Heiltrank:
 * - Setzt die Energie des Ritters auf 1500
 * - Verschwindet nach dem Einsammeln
 *
 * Die eigentliche Spiellogik liegt in _010_0_FoS.
 */
public class RunePotion extends Treasure {

    public RunePotion(Position ort) {
        super(ort, "RunePotion");
    }

    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("RunePotion", ort.getX(), ort.getY());
    }
}
