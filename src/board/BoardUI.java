package board;

import javax.swing.*;
import java.awt.*;

/**
 * Fenster-Komponente für die grafische Darstellung des Spielfelds.
 *
 * <p>{@code BoardUI} kapselt ein {@link Board}-Objekt und stellt ein
 * Swing-Fenster bereit, in dem das Spielfeld angezeigt wird.</p>
 *
 * <h3>Aufgaben</h3>
 * <ul>
 *   <li>Erzeugt und verwaltet das Spielfenster (JFrame)</li>
 *   <li>Delegiert Zeichenbefehle an {@link Board}</li>
 *   <li>Stellt Methoden zum Neuzeichnen ({@link #draw}) und Zurücksetzen ({@link #wipe}) bereit</li>
 * </ul>
 *
 * <p>Die Klasse enthält <strong>keine Spiellogik</strong> und wird ausschließlich
 * von der Spiellogik (z. B. {@code Game}) verwendet.</p>
 *
 * @author Trifon Stanchev
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BoardUI extends JFrame{

	private Board myBoard;

	// Standardkonstruktor
	public BoardUI() {
		super();
		setTitle("Game Board");
		setSize(465, 540);
		setResizable(false);
		setLocationRelativeTo(null);

		myBoard = new Board();
		getContentPane().setBackground(new Color(255, 255, 204));
		add(myBoard);
	}

	// Getter für GUI
	private Board getMyBoard() {
		return this.myBoard;
	}

	// Setter für GUI
	@SuppressWarnings("unused")
	private void setMyBoard(Board board) {
		this.myBoard = board;
	}

	/**
	 * Entfernt alle aktuell dargestellten Elemente vom Spielfeld.
	 * Wird z. B. beim Neustart oder Neuzeichnen verwendet.
	 */
	public void wipe() {
		getMyBoard().wipe();
	}

	/**
	 * Zeichnet ein grafisches Element auf dem Spielfeld.
	 *
	 * <p>Der {@code figure}-Parameter muss einem in {@link Board}
	 * registrierten Zeichen-Key entsprechen.</p>
	 *
	 * @param figure Zeichen-Key (z. B. "Knight", "Dragon", "Sword", "Grid")
	 * @param xpos   X-Position in Pixeln (0–420)
	 * @param ypos   Y-Position in Pixeln (0–420)
	 * @throws UnknownElementException wenn der Zeichen-Key unbekannt ist
	 */
	public void draw(String figure, int xpos, int ypos) throws UnknownElementException {
		getMyBoard().draw(figure, xpos, ypos);
		getMyBoard().repaint();
	}
}
