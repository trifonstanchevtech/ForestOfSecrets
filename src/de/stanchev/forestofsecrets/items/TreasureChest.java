package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert die Schatztruhe.
 *
 * Wird die Schatztruhe vom Ritter gefunden,
 * gewinnt der Spieler das Spiel.
 */
public class TreasureChest extends Treasure {

    public TreasureChest(Position ort) {
        super(ort, "Chest");
    }

    /**
     * Kennzeichnet dieses Objekt als Gewinnobjekt.
     */
    @Override
    public boolean isWinningTreasure() {
        return true;
    }
}
