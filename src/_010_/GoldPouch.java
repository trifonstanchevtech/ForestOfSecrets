package _010_;

/**
 * GoldPouch (Goldbeutel).
 *
 * Normales Fundobjekt ohne Spezialfunktion.
 * Wird beim Betreten gefunden und angezeigt.
 */
public class GoldPouch extends Treasure {
    public GoldPouch(Position ort) {
        super(ort, "GoldPouch");
    }
}
