package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert eine Schriftrolle (Scroll).
 *
 * Die Schriftrolle ist ein normales Fundobjekt ohne spielmechanische Wirkung.
 * Sie symbolisiert Wissen, Geschichte oder alte Magie
 * und dient dem Entdeckungs- und Atmosphäre-Aspekt des Spiels.
 *
 * Nach dem Finden wird das Objekt vom Spielfeld entfernt.
 */
public class Scroll extends Treasure {

    public Scroll(Position ort) {
        super(ort, "Scroll");
    }
}
