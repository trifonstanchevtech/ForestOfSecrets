package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Relic (mystisches Artefakt).
 *
 * Dient nur der Spielatmosphäre und dem Sammelaspekt.
 */
public class Relic extends Treasure {
    public Relic(Position ort) {
        super(ort, "Relic");
    }
}
