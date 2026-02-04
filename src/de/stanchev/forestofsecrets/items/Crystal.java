package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Crystal (blauer Kristall).
 *
 * Normales Fundobjekt im Spiel:
 * - hat keine Auswirkungen auf Energie oder Spielstatus
 * - dient ausschließlich der Atmosphäre und Exploration
 */
public class Crystal extends Treasure {

    public Crystal(Position ort) {
        super(ort, "Crystal");
    }
}

