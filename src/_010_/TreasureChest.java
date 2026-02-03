package _010_;

/**
 * Repräsentiert die Schatztruhe.
 * Wird die Schatztruhe vom Ritter gefunden, gewinnt der Spieler das Spiel.
 */
public class TreasureChest extends Treasure {

    public TreasureChest(Position ort) {
        super(ort, "Chest");
    }

    /**
     * Die Schatztruhe ist das Gewinnobjekt des Spiels.
     */
    @Override
    public boolean isWinningTreasure() {
        return true;
    }
}
