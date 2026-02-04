package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Medallion (Amulett / Medaille).
 *
 * Dekoratives Fundobjekt ohne Einfluss auf das Gameplay.
 */
public class Medallion extends Treasure {
    public Medallion(Position ort) {
        super(ort, "Medallion");
    }
}
