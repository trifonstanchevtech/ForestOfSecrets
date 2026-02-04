package de.stanchev.forestofsecrets.items;

import board.BoardUI;
import board.UnknownElementException;
import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert einen magischen Lebensstein (LifeStone).
 *
 * Der LifeStone ist eine sichtbare Energiequelle auf dem Spielfeld.
 * Wird er vom Ritter gefunden, stellt er dessen Energie vollständig wieder her.
 *
 * Die eigentliche Spiel- und Interaktionslogik
 * (Energie auffüllen und Objekt entfernen)
 * wird zentral in der Klasse {@link de.stanchev.forestofsecrets.core.Game} verarbeitet.
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
