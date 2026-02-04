package de.stanchev.forestofsecrets.core;

import board.BoardUI;
import board.UnknownElementException;
import de.stanchev.forestofsecrets.entities.Dragon;
import de.stanchev.forestofsecrets.entities.Knight;
import de.stanchev.forestofsecrets.items.*;
import de.stanchev.forestofsecrets.model.GameObject;
import de.stanchev.forestofsecrets.model.Position;
import de.stanchev.forestofsecrets.model.Treasure;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Zentrale Spiellogik für "Forest of Secrets".
 *
 * Verantwortlichkeiten:
 * - Verwaltet den Spielzustand (Spielfigur, Objekte, Positionen)
 * - Führt Spielzüge aus (Bewegung + Energieverbrauch)
 * - Prüft Kollisionen und löst Interaktionen aus
 * - Zeichnet das Spielfeld über {@link BoardUI} neu
 * - Behandelt Gewinn-/Verlustbedingungen (Dialoge + Neustart)
 *
 * Hinweis:
 * Die Darstellung erfolgt über das {@code board}-Package (BoardUI/Board).
 * Die Spiellogik selbst ist unabhängig von Swing-Buttons; sie wird vom Controller angesteuert.
 */
public class Game {


    /** Rastergröße eines Feldes (Pixel). Alle Objekte bewegen sich in 30er-Schritten. */
    private static final int TILE = 30;

    /** Gültiger Koordinatenbereich laut Board: 0..420. */
    private static final int MIN = 0;
    private static final int MAX = 420;

    /** Y-Position unterhalb des Grids ("Fundzone"),
     * wo das letzte gefundene Objekt angezeigt wird. */
    private static final int FOUND_Y = 465;

    /** X-Position für zentrierte Anzeige eines Objekts in der Fundzone. */
    private static final int FOUND_X_CENTER = (450 - TILE) / 2; // 210

    private final BoardUI myBoardUI;

    /**
     * Enthält alle Objekte, die gezeichnet/verwaltet werden.
     * Index 0 ist die Spielfigur (Knight). Andere Einträge sind Items/Enemy/Goal.
     */
    private GameObject[] gameObjects;

    /** Die Spielfigur (Player). */
    private Knight myKnight;

    /** Merkt sich das zuletzt gefundene Objekt (Name/Key),
     * damit es nach wipe() unten angezeigt wird. */
    private String lastFoundName = null;

    public Game() {
        myBoardUI = new BoardUI();
        myBoardUI.setVisible(true);

        // 0 = Ritter | 1..3 = Energie | 4..9 = Sammelobjekte | 10 = Schwert | 11 = Drache | 12 = Schatztruhe
        gameObjects = new GameObject[13];

        myKnight = new Knight("Arthur", 30, new Position(0, 0));

        initializeElements();
    }

    /**
     * Platziert alle Spielobjekte zufällig auf freie Felder im Grid.
     * Die Startposition (0,0) ist für den Ritter reserviert.
     */
    private void initializeElements() {
        ArrayList<Position> belegtePositionen = new ArrayList<>();
        belegtePositionen.add(new Position(0, 0));

        Random random = new Random();

        // Ritter
        gameObjects[0] = myKnight;

        // Energie-Objekte
        gameObjects[1] = new RedPotion(getRandomPosition(random, belegtePositionen));
        gameObjects[2] = new RunePotion(getRandomPosition(random, belegtePositionen));
        gameObjects[3] = new LifeStone(getRandomPosition(random, belegtePositionen));

        // Sammelobjekte (ohne Spezialfunktion)
        gameObjects[4] = new Crystal(getRandomPosition(random, belegtePositionen));
        gameObjects[5] = new GoldPouch(getRandomPosition(random, belegtePositionen));
        gameObjects[6] = new Medallion(getRandomPosition(random, belegtePositionen));
        gameObjects[7] = new Helmet(getRandomPosition(random, belegtePositionen));
        gameObjects[8] = new Relic(getRandomPosition(random, belegtePositionen));
        gameObjects[9] = new Scroll(getRandomPosition(random, belegtePositionen));

        // Schwert (setzt Status hasSword = true, wenn gefunden)
        gameObjects[10] = new Sword(getRandomPosition(random, belegtePositionen));

        // Drache (Gewinn/Verlust abhängig vom Schwert)
        gameObjects[11] = new Dragon(getRandomPosition(random, belegtePositionen));

        // Schatztruhe (Gewinn)
        gameObjects[12] = new TreasureChest(getRandomPosition(random, belegtePositionen));
    }

    /**
     * Führt einen Spielzug aus.
     *
     * Ablauf:
     * 1) Randprüfung (Grid)
     * 2) Energieprüfung
     * 3) Bewegung der Spielfigur
     * 4) Kollisionen/Interaktionen
     *
     * @param verschiebung Bewegung in Raster-Schritten (z.B. (0,30), (30,0), ...)
     * @return Spielnachricht für die GUI
     */
    public String play(Position verschiebung) {

        if (myKnight == null) {
            return "Kein Ritter vorhanden.";
        }

        if (!checkMove(verschiebung)) {
            return "Du kannst nicht weiter – der Rand ist erreicht.";
        }

        int verbrauch = verschiebung.calcEnergieReduction();
        if (myKnight.getEnergie() < verbrauch) {
            return "Du bist zu erschöpft, um weiterzugehen.";
        }

        myKnight.move(verschiebung);

        StringBuilder nachricht = new StringBuilder();
        processCollisions(nachricht);

        return nachricht.length() == 0 ? "Du gehst weiter." : nachricht.toString();
    }

    /**
     * Prüft, ob die Spielfigur auf einem Objekt steht und löst die Interaktion aus.
     */
    private void processCollisions(StringBuilder nachricht) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null && gameObject != myKnight && myKnight.isAtSameLocation(gameObject)) {
                handleInteraction(gameObject, nachricht);
                break;
            }
        }
    }

    /**
     * Verarbeitet die Interaktion zwischen Ritter und einem Objekt.
     * Alle interaktiven Objekte sind vom Typ {@link Treasure}.
     */
    private void handleInteraction(GameObject gameObject, StringBuilder nachricht) {
        if (!(gameObject instanceof Treasure treasure)) {
            return;
        }

        // merken für Anzeige unten im Board
        lastFoundName = treasure.getName();

        // Schwert
        if (treasure instanceof Sword) {
            myKnight.setHasSword(true);
            nachricht.append("Du hast ein Schwert entdeckt. Es kann dir auf deinem Weg nützlich sein.");
            removeElement(gameObject);
            return;
        }

        // Drache
        if (treasure instanceof Dragon) {
            refreshBoardUI();
            if (myKnight.hasSword()) {
                showWinDialogDragon();
            } else {
                myKnight.setEnergie(0);
                showLoseDialog();
            }
            return;
        }

        // Energieobjekte
        if (treasure instanceof RedPotion || treasure instanceof RunePotion || treasure instanceof LifeStone) {
            myKnight.setEnergie(1500);

            nachricht.append("Du hast ")
                    .append(articleDE(treasure.getName()))
                    .append(" ")
                    .append(nameDE(treasure.getName()))
                    .append(" benutzt. Energie wieder voll.");

            removeElement(gameObject); // verschwindet bis Neustart
            return;
        }

        // Schatztruhe (Gewinn)
        if (treasure.isWinningTreasure()) {
            refreshBoardUI();
            showWinDialog();
            return;
        }

        // Sammelobjekte
        nachricht.append("Du hast ")
                .append(articleDE(treasure.getName()))
                .append(" ")
                .append(nameDE(treasure.getName()))
                .append(" gefunden.");

        removeElement(gameObject);
    }

    // Dialoge (Gewinn/Verlust)
    private void showWinDialog() {
        Object[] options = {"Los geht's!", "Beenden"};

        int antwort = JOptionPane.showOptionDialog(
                null,
                "Du hast die Schatztruhe gefunden!\nNeustart?",
                "\uD83C\uDFC6 GEWONNEN! \uD83C\uDFC6",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (antwort == 0) {
            restartGame();
        } else System.exit(0);
    }

    private void showWinDialogDragon() {
        Object[] options = {"Los geht's!", "Beenden"};

        int antwort = JOptionPane.showOptionDialog(
                null,
                "Du hast den Drachen besiegt!\nNeustart?",
                "\uD83D\uDC09\u2694\uFE0F GEWONNEN \u2694\uFE0F\uD83D\uDC09",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (antwort == 0) {
            restartGame();
        } else System.exit(0);
    }

    private void showLoseDialog() {
        Object[] options = {"Los geht's!", "Beenden"};

        int antwort = JOptionPane.showOptionDialog(
                null,
                "Der Drache hat dich besiegt!\nNeustart?",
                "\uD83D\uDC80 VERLOREN \uD83D\uDC80",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (antwort == 0) {
            restartGame();
        } else System.exit(0);
    }

    // Neustart / Board-Rendering
    /** Setzt den Spielzustand zurück und platziert alle Objekte neu. */
    private void restartGame() {
        myKnight.setEnergie(1500);
        myKnight.setHasSword(false);
        myKnight.setOrt(new Position(0, 0));

        lastFoundName = null;

        initializeElements();
        refreshBoardUI();
    }

    /**
     * Zeichnet das komplette Spielfeld neu.
     * Wird nach jeder Aktion (z.B. Bewegung) aufgerufen.
     */
    public void refreshBoardUI() {
        myBoardUI.wipe();
        try {
            myBoardUI.draw("Grid", 0, 0);

            for (GameObject gameObject : gameObjects) {
                if (gameObject != null) {
                    gameObject.draw(myBoardUI);
                }
            }

            // Fundzone: zuletzt gefundenes Objekt anzeigen
            if (lastFoundName != null) {
                myBoardUI.draw(lastFoundName, FOUND_X_CENTER, FOUND_Y);
            }
        } catch (UnknownElementException ignored) {
            // Falls ein Schlüssel nicht im Board registriert ist, wird das Objekt nicht gezeichnet.
        }
    }

    // Hilfsmethoden
    /** Prüft, ob eine Bewegung innerhalb der Spielfeldgrenzen bleibt. */
    private boolean checkMove(Position verschiebung) {
        Position neu = new Position(
                myKnight.getOrt().getX() + verschiebung.getX(),
                myKnight.getOrt().getY() + verschiebung.getY()
        );

        return neu.getX() >= MIN && neu.getX() <= MAX && neu.getY() >= MIN && neu.getY() <= MAX;
    }

    /** Entfernt ein Objekt aus dem Spiel (verschwindet bis Neustart). */
    private void removeElement(GameObject gameObject) {
        for (int i = 0; i < gameObjects.length; i++) {
            if (gameObjects[i] == gameObject) {
                gameObjects[i] = null;
                return;
            }
        }
    }

    /** Liefert eine zufällige freie Position im Raster (0..420 in 30er-Schritten). */
    private Position getRandomPosition(Random random, ArrayList<Position> belegtePositionen) {
        Position pos;
        do {
            int x = random.nextInt((MAX / TILE) + 1) * TILE;    // 0..420
            int y = random.nextInt((MAX / TILE) + 1) * TILE;    // 0..420
            pos = new Position(x, y);
        } while (istBelegt(pos, belegtePositionen));

        belegtePositionen.add(pos);
        return pos;
    }

    /** Prüft, ob eine Position bereits belegt ist. */
    private boolean istBelegt(Position position, ArrayList<Position> belegtePositionen) {
        for (Position p : belegtePositionen) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Deutsche Texte (Artikel + Namen)
    private String articleDE(String name) {
        return switch (name) {
            // Spezialobjekte
            case "Sword"      -> "ein";
            case "Chest"      -> "eine";
            case "Dragon"     -> "einen";

            // Energieobjekte
            case "RedPotion"  -> "einen";
            case "RunePotion" -> "einen";
            case "LifeStone"  -> "einen";

            // Normale Fundobjekte
            case "Crystal"    -> "einen";
            case "GoldPouch"  -> "einen";
            case "Medallion"  -> "ein";
            case "Helmet"     -> "einen";
            case "Relic"      -> "ein";
            case "Scroll"     -> "eine";
            default -> "einen";
        };
    }

    // Deutsche Namen für Fundmeldungen
    private String nameDE(String name) {
        return switch (name) {
            // Spezialobjekte
            case "Sword"      -> "Schwert";
            case "Chest"      -> "Schatztruhe";
            case "Dragon"     -> "Drachen";

            case "RedPotion"  -> "roten Trank";
            case "RunePotion" -> "Runentrank";
            case "LifeStone"  -> "Lebensstein";

            case "Crystal"    -> "Kristall";
            case "GoldPouch"  -> "Goldbeutel";
            case "Medallion"  -> "Medaillon";
            case "Helmet"     -> "Helm";
            case "Relic"      -> "Relikt";
            case "Scroll"     -> "Schriftrolle";

            default -> name;
        };
    }

    // Getter / Setter (Controller)
    public Knight getMyKnight() {
        return myKnight;
    }

    public void setMyKnight(Knight knight) {
        this.myKnight = knight;
        gameObjects[0] = knight;
    }
}
