package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert ein mystisches Relikt (Relic).
 *
 * Das Relikt ist ein dekoratives Artefakt ohne spielmechanische Wirkung.
 * Es kann vom Ritter gefunden werden und trägt zur Atmosphäre
 * sowie zum Entdeckungs- und Sammelaspekt des Spiels bei.
 *
 * Nach dem Finden wird das Objekt vom Spielfeld entfernt.
 */
public class Relic extends Treasure {

    public Relic(Position ort) {
        super(ort, "Relic");
    }
}
