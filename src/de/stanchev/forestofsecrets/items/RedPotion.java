package de.stanchev.forestofsecrets.items;

import board.BoardUI;
import board.UnknownElementException;
import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Roter Heiltrank.
 * Wird sichtbar auf dem Spielfeld gezeichnet und dient als Energiequelle.
 * Die Logik (Energie auf 1500 setzen & entfernen) passiert in _010_0_FoS.
 */
public class RedPotion extends Treasure {

    public RedPotion(Position ort) {
        super(ort, "RedPotion");
    }

    @Override
    public void draw(BoardUI board) throws UnknownElementException {
        board.draw("RedPotion", ort.getX(), ort.getY());
    }
}
