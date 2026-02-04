package board;

/**
 * Exception, die ausgelöst wird, wenn ein unbekanntes
 * grafisches Element auf dem Board gezeichnet werden soll.
 *
 * Wird geworfen, wenn z. B. kein passender Drawable-Key
 * im Board registriert ist.
 */
public class UnknownElementException extends Exception {

    public UnknownElementException() {
        super();
    }

    public UnknownElementException(String message) {
        super(message);
    }
}
