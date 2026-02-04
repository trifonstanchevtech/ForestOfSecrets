package de.stanchev.forestofsecrets.items;

import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

/**
 * Repräsentiert einen Ritterhelm (Helmet).
 *
 * Der Helm ist ein symbolisches Ausrüstungsobjekt ohne Einfluss
 * auf Energie, Kampf oder Spielverlauf.
 * Beim Finden wird der Gegenstand gemeldet
 * und anschließend vom Spielfeld entfernt.
 *
 * Er dient der Atmosphäre und dem Rollenspiel-Aspekt des Spiels.
 */
public class Helmet extends Treasure {

    public Helmet(Position ort) {
        super(ort, "Helmet");
    }
}
