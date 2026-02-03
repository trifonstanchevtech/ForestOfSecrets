package _010_;

import board.BoardUI;
import board.UnknownElementException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Zentrale Spiellogik für "Forest of Secrets".
 * Steuert Spielfeld, Spielfigur, Objekte, Kollisionen und Spielende.
 */
public class Game {
    private static final int TILE = 30;

    // BoardUI sagt: 0–420 (nicht 450)
    private static final int MIN = 0;
    private static final int MAX = 420;

    // Board zeichnet Trennlinie bei y=455 -> darunter ist "Fundzone"
    private static final int FOUND_Y = 465;

    // unten mittig: x = (450 - 30) / 2 = 210
    private static final int FOUND_X_CENTER = (450 - TILE) / 2; // 210

    private final BoardUI myBoardUI;
    private GameObject[] gameObjects;

    private Knight myKnight;

    // Merkt sich das zuletzt gefundene Element-Objekt (damit es nach wipe wieder angezeigt wird)
    private String lastFoundName = null;

    public Game() {
        myBoardUI = new BoardUI();
        myBoardUI.setVisible(true);

        // 0 = Ritter | 1..3 = normale Schätze | 10 = Schwert | 11 = Drache | 12 = Gewinnschatz
        gameObjects = new GameObject[13];

        myKnight = new Knight("Arthur", 30, new Position(0, 0));

        initializeElements();
    }

    // Initialisierung
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

        // Normale Schätze
        gameObjects[4] = new Crystal(getRandomPosition(random, belegtePositionen));
        gameObjects[5] = new GoldPouch(getRandomPosition(random, belegtePositionen));
        gameObjects[6] = new Medallion(getRandomPosition(random, belegtePositionen));
        gameObjects[7] = new Helmet(getRandomPosition(random, belegtePositionen));
        gameObjects[8] = new Relic(getRandomPosition(random, belegtePositionen));
        gameObjects[9] = new Scroll(getRandomPosition(random, belegtePositionen));

        // Schwert (Status: hasSword = true)
        gameObjects[10] = new Sword(getRandomPosition(random, belegtePositionen));

        // Drache (Win/Lose abhängig vom Schwert)
        gameObjects[11] = new Dragon(getRandomPosition(random, belegtePositionen));

        // Schatztruhe (Gewinn)
        gameObjects[12] = new TreasureChest(getRandomPosition(random, belegtePositionen));
    }

    // Spielzug
    public String play(Position verschiebung) {

        if (!checkMove(verschiebung)) {
            return "Du kannst nicht weiter – der Rand ist erreicht.";
        }

        int verbrauch = verschiebung.calcEnergieReduction();
        if (myKnight.getEnergie() < verbrauch) {
            return "Du bist zu erschöpft, um weiterzugehen.";
        }

        myKnight.move(verschiebung);

        StringBuilder nachricht = new StringBuilder();
        processElements(nachricht);

        return nachricht.length() == 0 ? "Du gehst weiter." : nachricht.toString();
    }

    // Kollisionen
    private void processElements(StringBuilder nachricht) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null && gameObject != myKnight && myKnight.getOrt().equals(gameObject.getOrt())) {
                handleElementInteraction(gameObject, nachricht);
                break;
            }
        }
    }

    private void handleElementInteraction(GameObject gameObject, StringBuilder nachricht) {
        if (!(gameObject instanceof Treasure treasure)) {
            return;
        }

        lastFoundName = treasure.getName();

        // Sword | Schwert
        if (treasure instanceof Sword) {
            myKnight.setHasSword(true);
            nachricht.append("Du hast ein Schwert entdeckt. Es kann dir auf deinem Weg nützlich sein.");
            return;
        }

        // Dragon | Drache
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

        // Energiequellen (RedPotion, RunePotion, LifeStone)
        if (treasure instanceof RedPotion
                || treasure instanceof RunePotion
                || treasure instanceof LifeStone) {

            myKnight.setEnergie(1500);

            nachricht.append("Du hast ")
                    .append(articleDE(treasure.getName()))
                    .append(" ")
                    .append(nameDE(treasure.getName()))
                    .append(" benutzt. Energie wieder voll.");

            removeElement(gameObject); // verschwindet bis Neustart
            return;
        }

        // Schatztruhe
        if (treasure.isWinningTreasure()) {
            refreshBoardUI();
            showWinDialog();
            return;
        }

        // Normaler Schatz
        nachricht.append("Du hast ")
                .append(articleDE(treasure.getName()))
                .append(" ")
                .append(nameDE(treasure.getName()))
                .append(" gefunden.");

        removeElement(gameObject);
    }

    // Dialoge
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

    // Neustart / Board
    private void restartGame() {
        myKnight.setEnergie(1500);
        myKnight.setHasSword(false);
        myKnight.setOrt(new Position(0, 0));
        lastFoundName = null;
        initializeElements();
        refreshBoardUI();
    }

    public void refreshBoardUI() {
        myBoardUI.wipe();
        try {
            myBoardUI.draw("Grid", 0, 0);

            for (GameObject gameObject : gameObjects) {
                if (gameObject != null) {
                    gameObject.draw(myBoardUI);
                }
            }

            if (lastFoundName != null) {
                myBoardUI.draw(lastFoundName, FOUND_X_CENTER, FOUND_Y);
            }
        } catch (UnknownElementException ignored) {}
    }

    // Hilfsmethoden
    private boolean checkMove(Position verschiebung) {
        Position neu = new Position(
                myKnight.getOrt().getX() + verschiebung.getX(),
                myKnight.getOrt().getY() + verschiebung.getY()
        );

        return neu.getX() >= MIN && neu.getX() <= MAX && neu.getY() >= MIN && neu.getY() <= MAX;
    }

    private void removeElement(GameObject gameObject) {
        for (int i = 0; i < gameObjects.length; i++) {
            if (gameObjects[i] == gameObject) {
                gameObjects[i] = null;
                return;
            }
        }
    }

    private Position getRandomPosition(Random random, ArrayList<Position> belegtePositionen) {
        Position pos;
        do {
            int x = random.nextInt((MAX / TILE) + 1) * TILE;
            int y = random.nextInt((MAX / TILE) + 1) * TILE;
            pos = new Position(x, y);
        } while (istBelegt(pos, belegtePositionen));

        belegtePositionen.add(pos);
        return pos;
    }

    private boolean istBelegt(Position position, ArrayList<Position> belegtePositionen) {
        for (Position p : belegtePositionen) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Deutsche Texte für Fundmeldung
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

            // Energieobjekte
            case "RedPotion"  -> "roten Trank";
            case "RunePotion" -> "Runentrank";
            case "LifeStone"  -> "Lebensstein";

            // Normale Fundobjekte
            case "Crystal"    -> "Kristall";
            case "GoldPouch"  -> "Goldbeutel";
            case "Medallion"  -> "Medaillon";
            case "Helmet"     -> "Helm";
            case "Relic"      -> "Relikt";
            case "Scroll"     -> "Schriftrolle";

            default -> name;
        };
    }

    public Knight getMyKnight() {
        return myKnight;
    }

    public void setMyKnight(Knight knight) {
        this.myKnight = knight;
        gameObjects[0] = knight;
    }
}
