package de.stanchev.forestofsecrets.app;

import de.stanchev.forestofsecrets.core.Game;
import de.stanchev.forestofsecrets.entities.Knight;
import de.stanchev.forestofsecrets.model.Position;

/**
 * Start- und Steuerklasse (Controller) des Spiels.
 * <p>
 * Verantwortlichkeiten:
 * <ul>
 *   <li>Startet die Anwendung (main-Methode)</li>
 *   <li>Initialisiert das Spiel anhand der Eingaben aus der {@link GameWindow}</li>
 *   <li>Leitet Benutzeraktionen (Bewegung, Start, Beenden) an die Spiellogik ({@link Game}) weiter</li>
 *   <li>Aktualisiert die Anzeige in der GUI nach jeder Aktion</li>
 * </ul>
 *
 * Hinweis: Die GUI arbeitet ereignisgesteuert (Buttons/Key-Bindings),
 * der Controller sorgt für die Verbindung zwischen Oberfläche und Spiellogik.
 */
public class GameController {

    /** Referenz auf die grafische Oberfläche. */
    private GameWindow frame;

    /** Zentrale Spiellogik (wird beim Start einmalig initialisiert). */
    private Game myGame;

    /**
     * Program Einstiegspunkt / Programmstart (main-Methode)
     *
     * @param args Kommandozeilenargumente (werden hier nicht verwendet)
     */
    public static void main(String[] args) {
        new GameController();
    }

    /**
     * Erstellt den Controller und startet die GUI (Default-Konstruktor).
     * Das eigentliche Spiel wird erst über den Start-Button initialisiert.
     */
    public GameController() {
        frame = new GameWindow(this); // Weitergabe der eigenen Instanz
        initFirstFrame();
    }

    /** Zeigt das Hauptfenster an und setzt feste Fenster-Eigenschaften. */
    private void initFirstFrame() {
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Startet das Spiel anhand der Eingaben aus der GUI.
     * Wird vom Start-Button ausgelöst.
     * <p>
     * Das Spiel wird nur einmal initialisiert (mehrfaches Klicken wird ignoriert).
     */
    public void startGame() {
        if (myGame != null) return;   // nur einmal initialisieren
        initGameFromUI();
    }

    /**
     * Liest die Eingabefelder aus, prüft die Werte (Raster/ Zahlen),
     * erstellt anschließend die Spiellogik und die Spielfigur.
     */
    private void initGameFromUI() {
        try {
            applyDefaultValuesIfEmpty();

            // Werte aus GUI-Feldern holen und umwandeln
            String titel = frame.getTxtTitel().getText().trim();
            String name = frame.getTxtName().getText().trim();
            int alter = Integer.parseInt(frame.getTxtAlter().getText().trim());
            int x = Integer.parseInt(frame.getTxtX().getText().trim());
            int y = Integer.parseInt(frame.getTxtY().getText().trim());

            // Raster-Prüfung (Spiel bewegt sich in 30er-Schritten)
            if (x % 30 != 0 || y % 30 != 0) {
                frame.setNachricht("X und Y müssen Vielfache von 30 sein!");
                return;
            }

            // Voller Name aus Titel + Name
            String vollerName = titel.isEmpty() ? name : (titel + " " + name);

            // Spiel initialisieren
            myGame = new Game();

            // Spielfigur erstellen und setzen
            Position startPosition = new Position(x, y);
            Knight knight = new Knight(vollerName, alter, startPosition);
            myGame.setMyKnight(knight);

            // GUI aktualisieren und Spiel starten
            lockStartInputsAndEnableMovement();
            updateUI();
            myGame.refreshBoardUI();

            frame.setNachricht("Der Ritter ist bereit für das Abenteuer!");

        } catch (NumberFormatException e) {
            frame.setNachricht("Fehler: Ungültige Zahlenwerte!");
        }
    }

    /** Setzt Standardwerte, falls Eingaben leer sind. */
    private void applyDefaultValuesIfEmpty() {
        if (frame.getTxtTitel().getText().trim().isEmpty())
            frame.getTxtTitel().setText("King");

        if (frame.getTxtName().getText().trim().isEmpty())
            frame.getTxtName().setText("Arthur");

        if (frame.getTxtAlter().getText().trim().isEmpty())
            frame.getTxtAlter().setText("30");

        if (frame.getTxtX().getText().trim().isEmpty())
            frame.getTxtX().setText("210");

        if (frame.getTxtY().getText().trim().isEmpty())
            frame.getTxtY().setText("180");
    }

    /**
     * Sperrt Eingabefelder für Startwerte und aktiviert die Bewegungs-Buttons.
     * Dadurch kann der Spieler nach dem Start nur noch über das Grid laufen.
     */
    private void lockStartInputsAndEnableMovement() {
        frame.getTxtX().setEditable(false);
        frame.getTxtY().setEditable(false);
        frame.getBtnAnwenden().setEnabled(false);

        frame.getBtnMoveUp().setEnabled(true);
        frame.getBtnMoveDown().setEnabled(true);
        frame.getBtnMoveLeft().setEnabled(true);
        frame.getBtnMoveRight().setEnabled(true);
    }

    /** Aktualisiert die GUI mit den aktuellen Werten der Spielfigur. */
    private void updateUI() {
        Knight knight = myGame.getMyKnight();
        frame.getTxtX().setText(String.valueOf(knight.getOrt().getX()));
        frame.getTxtY().setText(String.valueOf(knight.getOrt().getY()));
        frame.getTxtEnergie().setText(String.valueOf(knight.getEnergie()));
    }

    /** Bewegung nach unten (y + 30). */
    public void moveDown() {
        moveKnight(new Position(0, 30));
    }

    /** Bewegung nach links (x - 30). */
    public void moveLeft() {
        moveKnight(new Position(-30, 0));
    }

    /** Bewegung nach rechts (x + 30). */
    public void moveRight() {
        moveKnight(new Position(30, 0));
    }

    /** Bewegung nach oben (y - 30). */
    public void moveUp() {
        moveKnight(new Position(0, -30));
    }

    /**
     * Führt einen Spielzug aus und aktualisiert danach Board und GUI.
     *
     * @param verschiebung Bewegungsrichtung in Raster-Schritten (30er-Koordinaten)
     */
    private void moveKnight(Position verschiebung) {
        if (myGame == null) return; // Sicherheit: Bewegung erst nach Start
        String nachricht = myGame.play(verschiebung);
        myGame.refreshBoardUI();
        updateUI();
        frame.setNachricht(nachricht);
    }

    /**
     * Debug-Methode zur Analyse von Spielzügen.
     * Gibt Position, Energie und Rückgabewerte der Spiellogik
     * zusätzlich in der Konsole aus.
     *
     * @param verschiebung Bewegungsrichtung in Raster-Schritten
     */
    @SuppressWarnings("unused")
    private void debugMoveKnight(Position verschiebung) {
        Knight knight = myGame.getMyKnight();

        System.out.println("DEBUG: Vor play()");
        System.out.println("  Position: " + knight.getOrt());
        System.out.println("  Energie : " + knight.getEnergie());

        String nachricht = myGame.play(verschiebung);

        System.out.println("DEBUG: Nach play()");
        System.out.println("  Position : " + knight.getOrt());
        System.out.println("  Nachricht: \"" + nachricht + "\"");
        System.out.println("  Verschiebung: " +
                verschiebung.getX() + ", " + verschiebung.getY());

        myGame.refreshBoardUI();
        updateUI();
        frame.setNachricht(nachricht);
    }

    /**
     * Beendet die Anwendung.
     *
     * @param status Exit-Code (0 = normal)
     */
    public void exit(int status) {
        System.exit(status);
    }
}
