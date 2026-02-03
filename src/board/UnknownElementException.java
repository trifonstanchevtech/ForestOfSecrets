package board;

/**
 * Title:
 * Description:	Exception to handle unknown elements on the board.
 * 
 * Copyright:	Copyright (c) 2025
 * Company:		Medert-IT
 * Author:		Christine Medert
 * Version:		2.0
 */

@SuppressWarnings("serial")
public class UnknownElementException extends Exception{

    // Default-Konstruktor
    public UnknownElementException() {
        super();
    }

    // Konstruktor mit Fehlertext
    public UnknownElementException(String text) {
        super(text != null ? text : "");
    }
}
