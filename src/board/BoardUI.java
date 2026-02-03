package board;

import javax.swing.*;
import java.awt.*;

/**
 * Title:
 * Description:	The Board class is used to display and manage a graphical game board.
 * 				Use 'setVisible(true)' to display the board.
 * 				Use 'setVisible(false)' to hide the board.
 * 				Use 'dispose()' to release resources allocated by the windowing system.
 * 				Use 'draw' to draw figures on the board.
 * 				Use 'wipe' to clear the board.
 * 
 * Copyright:	Copyright (c) 2026
 * Author:		Trifon Stanchev
 * Version:		1.0
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
	 * The 'wipe' method clears all previously drawn figures from the board.
	 * Use this method to reset the board to a blank state.
	 */
	public void wipe() {
		getMyBoard().wipe();
	}

	/**
	 * The 'draw' method displays a graphical element on the board.
	 * Supported figure types: 'Apple', 'Bow', 'Castle', 'Chain', 'Coin', 
	 * 'Crown', 'Diamond', 'Heart', 'Ring', 'Sword', 'Car', 'GasStation', 
	 * 'Dog', 'Bone', 'Tree', 'Square' (non-filled red square), and 'Grid' 
	 * (draws a 15x15 cell grid).
	 *
	 * @param figure Type of figure to draw
	 * @param xpos   Horizontal position (0–420)
	 * @param ypos   Vertical position (0–420)
	 * @throws UnknownElementException if the figure type is invalid
	 */
	public void draw(String figure, int xpos, int ypos) throws UnknownElementException {
		//        if (xpos < 0 || xpos > 460 || ypos < 0 || ypos > 460) {
		//            throw new IllegalArgumentException("Position (xpos, ypos) must be in the range 0 to 420.");
		//        }

		getMyBoard().draw(figure, xpos, ypos);
		getMyBoard().repaint();
	}
}
