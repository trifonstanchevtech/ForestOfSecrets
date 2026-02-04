package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert einen Goldbeutel (GoldPouch).
 *
 * Der Goldbeutel ist ein normales Fundobjekt ohne spielmechanische
 * Sonderfunktion. Beim Betreten des Feldes wird der Fund gemeldet
 * und der Gegenstand anschließend vom Spielfeld entfernt.
 *
 * Solche Objekte tragen zur Atmosphäre und zur Erkundung der Spielwelt bei.
 */
public class GoldPouch extends Treasure {

    public GoldPouch(Position ort) {
        super(ort, "GoldPouch");
    }
}
