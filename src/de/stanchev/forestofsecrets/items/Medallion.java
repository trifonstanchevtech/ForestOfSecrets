package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert ein Medaillon (Medallion).
 *
 * Das Medaillon ist ein dekoratives Fundobjekt ohne Einfluss
 * auf Spielmechanik oder Spielfortschritt.
 * Beim Betreten des Feldes wird der Fund gemeldet
 * und der Gegenstand anschließend vom Spielfeld entfernt.
 *
 * Es dient der Atmosphäre und dem Erkundungsaspekt des Spiels.
 */
public class Medallion extends Treasure {

    public Medallion(Position ort) {
        super(ort, "Medallion");
    }
}
