package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Scroll (Schriftrolle).
 *
 * Normales Fundobjekt, das Wissen oder Geschichte symbolisiert.
 */
public class Scroll extends Treasure {
    public Scroll(Position ort) {
        super(ort, "Scroll");
    }
}
