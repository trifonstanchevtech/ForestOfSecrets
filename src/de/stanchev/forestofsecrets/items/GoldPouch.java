package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * GoldPouch (Goldbeutel).
 *
 * Normales Fundobjekt ohne Spezialfunktion.
 * Wird beim Betreten gefunden und angezeigt.
 */
public class GoldPouch extends Treasure {
    public GoldPouch(Position ort) {
        super(ort, "GoldPouch");
    }
}
