package de.stanchev.forestofsecrets.entities;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert den Drachen im Spiel.
 * Der Drache ist auf dem Spielfeld als Baum getarnt.
 * Trifft der Ritter ohne Schwert auf den Drachen, verliert er.
 * Hat der Ritter bereits das Schwert, kann er den Drachen besiegen und gewinnt.
 */
public class Dragon extends Treasure {

    public Dragon(Position ort) {
        super(ort, "Dragon");
    }
}
