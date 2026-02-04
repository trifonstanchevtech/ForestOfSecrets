package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert einen Kristall (Crystal).
 *
 * Der Kristall ist ein normales Fundobjekt im Spiel.
 * Er hat keine direkte Auswirkung auf Energie oder Spielstatus,
 * wird aber beim Entdecken gemeldet und anschließend vom Spielfeld entfernt.
 *
 * Solche Objekte dienen der Exploration und erweitern die Spielwelt
 * um zusätzliche Sammel- und Entdeckungsmomente.
 */
public class Crystal extends Treasure {

    public Crystal(Position ort) {
        super(ort, "Crystal");
    }
}
