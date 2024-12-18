package edu.kit.informatik.manager.exceptions;

/**
 * Represents an exception that is thrown when an attempt is made to place a tile on an already occupied position.
 *
 * @author utobm
 * @version 1.0
 */
public class NotEmptyException extends Exception {
    /**
     * Default error message indicating that a tile has already been placed in the requested position.
     */
    private static final String NOT_EMPTY_ERROR = "Tile already placed.";

    /**
     * Constructs a new NotEmptyException with a predefined detailed message indicating that
     * a tile has already been placed in the requested position.
     */
    public NotEmptyException() {
        super(NOT_EMPTY_ERROR);
    }
}
