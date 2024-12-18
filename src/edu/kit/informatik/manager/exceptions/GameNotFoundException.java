package edu.kit.informatik.manager.exceptions;

/**
 * Represents an exception that is thrown when a game session with the specified name is not found.
 *
 * @author utobm
 * @version 1.0
 */
public class GameNotFoundException extends Exception {
    /**
     * Default error message format for when a game session is not found.
     */

    private static final String GAME_NOT_FOUND_ERROR = "No game session found with the name %s.";

    /**
     * Constructs a new GameNotFoundException with a detailed message indicating the name of the game session
     * that could not be found.
     *
     * @param gameName The name of the game session that was not found, causing the exception.
     */
    public GameNotFoundException(String gameName) {
        super(GAME_NOT_FOUND_ERROR.formatted(gameName));
    }
}
