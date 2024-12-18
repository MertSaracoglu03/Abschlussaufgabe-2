
package edu.kit.informatik.manager.exceptions;

/**
 * Represents an exception thrown when a given location lies outside the boundaries.
 * This exception is typically used in scenarios where an operation or query involves
 * coordinates or indices that need to fall within certain limits, and the provided
 * values exceed those limits.
 *
 * <p>The default error message for this exception is:
 * "The given location lies outside the boundaries".
 *
 * @author utobm
 * @version 1.0
 */
public class OutOfBoundsException extends Exception {
    private static final String OUT_OF_BOUNDS_ERROR = "The given location lies outside of the boundaries";

    /**
     * Constructs a new OutOfBoundsException with a default error message.
     */
    public OutOfBoundsException() {
        super(OUT_OF_BOUNDS_ERROR);
    }
}
