package edu.kit.informatik.manager.exceptions;

/**
 * Represents an exception that is thrown when trying to create a game that already exists.
 *
 * @author utobm
 * @version 1.0
 */

public class GameAlreadyExistsException extends Exception {
    /**
     * Default error message format for when a game already exists.
     */
    private static final String GAME_ALREADY_EXISTS_ERROR = "A game with the name %s already exists.";

    /**
     * Constructs a new GameAlreadyExistsException with a detailed message indicating the name of the game that already exists.
     *
     * @param name The name of the game that caused the exception.
     */

    public GameAlreadyExistsException(String name) {
        super(GAME_ALREADY_EXISTS_ERROR.formatted(name));
    }
}
