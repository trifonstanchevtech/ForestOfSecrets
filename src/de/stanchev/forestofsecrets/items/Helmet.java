package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Helmet (Ritterhelm).
 *
 * Symbolisches Ausrüstungsstück ohne spielmechanische Wirkung.
 */
public class Helmet extends Treasure {
    public Helmet(Position ort) {
        super(ort, "Helmet");
    }
}
