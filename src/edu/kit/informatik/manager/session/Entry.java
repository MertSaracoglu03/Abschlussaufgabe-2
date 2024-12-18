package edu.kit.informatik.manager.session;

/**
 * Represents an entry within a Tic-Tac-Toe game board.
 * <p>
 * This enum defines the possible values that each cell on the Tic-Tac-Toe board can have: either EMPTY, X, or O.
 * Each value is associated with a character representation for display purposes.
 *
 * @author Programmieren-Team
 * @version 1.0
 */

public enum Entry {
    /**
     * Represents an empty cell on the game board.
     */
    EMPTY('.'),
    /**
     * Represents a cell occupied by the player who plays 'X'.
     */
    X('X'),
    /**
     * Represents a cell occupied by the player who plays 'O'.
     */
    O('O');
    /**
     * The character representation of the entry.
     */

    private final char token;
    /**
     * Constructor to initialize the character representation of the entry.
     *
     * @param token The character representation of the entry.
     */

    Entry(char token) {
        this.token = token;
    }
    /**
     * Returns the character representation of the entry.
     *
     * @return The character representation of the entry.
     */

    public char getToken() {
        return token;
    }
    /**
     * Converts a 2D array of {@link Entry} objects into a 2D array of their respective character representations.
     *
     * @param entries A 2D array of {@link Entry} objects.
     * @return A 2D array containing the character representations of the provided entries.
     */

    public static char[][] getRepr(Entry[][] entries) {
        int length = entries.length;
        char[][] repr = new char[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                repr[i][j] = entries[i][j].token;
            }
        }
        return repr;
    }
}
