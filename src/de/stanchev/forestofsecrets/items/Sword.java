package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert das Schwert im Spiel.
 *
 * Das Schwert ist ein Schlüsselobjekt:
 * Hat der Ritter das Schwert gefunden, kann er später
 * den Drachen besiegen und das Spiel gewinnen.
 *
 * Ohne Schwert führt eine Begegnung mit dem Drachen
 * zum sofortigen Spielverlust.
 */
public class Sword extends Treasure {
    public Sword(Position ort) {
        super(ort, "Sword");
    }
}
