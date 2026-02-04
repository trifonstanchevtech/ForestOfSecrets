package de.stanchev.forestofsecrets.app;

import de.stanchev.forestofsecrets.entities.Knight;
import de.stanchev.forestofsecrets.core.Game;
import de.stanchev.forestofsecrets.model.Position;

/**
 * Use-Case-Controller für das Spiel.
 * Verbindet UI (ConsoleUI) mit der Spiellogik (FoS).
 */
public class GameController {
    // Gekapselte Attribute
    private GameWindow frame;
    private Game myGame;

    // Programmstart (main-Methode)
    public static void main(String[] args) {
        new GameController();
    }

    // Default-Konstruktor
    public GameController() {
        frame = new GameWindow(this); // Weitergabe der eigenen Instanz
        initFirstFrame();
    }

    // Initialisierung des ersten Frames
    private void initFirstFrame() {
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Initialisiert das Spiel basierend auf den Eingaben der GUI.
     * Wird über den „Anwenden“-Button ausgelöst.
     */
    public void startGame() {
        if (myGame != null) return;   // nur einmal initialisieren
        initFoS();                   // initFoS entscheidet, ob gestartet wird
    }

    // Initialisierung von FoS mit GUI-Werten
    private void initFoS() {
        try {
            // Default-Werte setzen, falls Felder leer sind
            if (frame.getTxtTitel().getText().trim().isEmpty())
                frame.getTxtTitel().setText("JA VA 16");

            if (frame.getTxtName().getText().trim().isEmpty())
                frame.getTxtName().setText("Arthur");

            if (frame.getTxtAlter().getText().trim().isEmpty())
                frame.getTxtAlter().setText("30");

            if (frame.getTxtX().getText().trim().isEmpty())
                frame.getTxtX().setText("210");

            if (frame.getTxtY().getText().trim().isEmpty())
                frame.getTxtY().setText("180");

            // Werte aus GUI-Feldern holen und umwandeln
            String titel = frame.getTxtTitel().getText();
            String name = frame.getTxtName().getText();
            int alter = Integer.parseInt(frame.getTxtAlter().getText());
            int x = Integer.parseInt(frame.getTxtX().getText());
            int y = Integer.parseInt(frame.getTxtY().getText());

            String vollerName;

            if (titel.isEmpty()) {
                vollerName = name;
            } else {
                vollerName = titel + " " + name;
            }

            // Raster-Prüfung
            if (x % 30 != 0 || y % 30 != 0) {
                frame.setNachricht("X und Y müssen Vielfache von 30 sein!");
                return;
            }

            // Board-Fenster erstellen
            myGame = new Game();

            // Position und Auto erstellen
            Position startPosition = new Position(x, y);
            Knight knight = new Knight(vollerName, alter, startPosition);
            myGame.setMyKnight(knight); // Setter statt direkter Zugriff

            // GUI aktualisieren
            updatePropFrame();
            updateUI();
            myGame.refreshBoardUI(); // NEU: Board sofort zeichnen

            frame.setNachricht("Der Ritter ist bereit für das Abenteuer!");

        } catch (NumberFormatException e) {
            frame.setNachricht("Fehler: Ungültige Zahlenwerte!");
        }
    }

    // UI-Zustand:
    // Sperrt Eingabefelder, aktiviert Bewegungs-Buttons
    private void updatePropFrame() {
        frame.getTxtX().setEditable(false);
        frame.getTxtY().setEditable(false);
        frame.getBtnAnwenden().setEnabled(false);

        frame.getBtnMoveUp().setEnabled(true);
        frame.getBtnMoveDown().setEnabled(true);
        frame.getBtnMoveLeft().setEnabled(true);
        frame.getBtnMoveRight().setEnabled(true);
    }

    // Aktualisiert die GUI mit aktuellen Werten
    private void updateUI() {
        Knight knight = myGame.getMyKnight();
        frame.getTxtX().setText(String.valueOf(knight.getOrt().getX()));
        frame.getTxtY().setText(String.valueOf(knight.getOrt().getY()));
        frame.getTxtEnergie().setText(String.valueOf(knight.getEnergie()));
    }

    // Bewegungsmethoden
    public void moveDown() {
        moveKnight(new Position(0, 30));
    }

    public void moveLeft() {
        moveKnight(new Position(-30, 0));
    }

    public void moveRight() {
        moveKnight(new Position(30, 0));
    }

    public void moveUp() {
        moveKnight(new Position(0, -30));
    }

    /**
     * Bewegt den Ritter um die angegebene Verschiebung,
     * aktualisiert Spiellogik und Benutzeroberfläche.
     *
     * @param verschiebung Bewegungsrichtung in Raster-Schritten
     */
    private void moveKnight(Position verschiebung) {
        String nachricht = myGame.play(verschiebung);
        myGame.refreshBoardUI();
        updateUI();
        frame.setNachricht(nachricht);
    }

// Für Testzwecke (Debug):
// private void moveKnight(_010_0_Position verschiebung) {
//
//     _010_0_Knight knight = myFoS.getMyKnight();
//
//     System.out.println("DEBUG: Vor play()");
//     System.out.println("  Position: " + knight.getOrt());
//     System.out.println("  Energie : " + knight.getEnergie());
//
//     String nachricht = myFoS.play(verschiebung);
//
//     System.out.println("DEBUG: Nach play()");
//     System.out.println("  Position : " + knight.getOrt());
//     System.out.println("  Nachricht: \"" + nachricht + "\"");
//     System.out.println("  Verschiebung: " +
//             verschiebung.getX() + ", " + verschiebung.getY());
//
//     updateUI();
//     frame.setNachricht(nachricht);
// }

  // Methode zum Beenden des Programms
    public void exit(int status) {
        System.exit(status);
    }
}
