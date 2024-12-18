package edu.kit.informatik.manager.exceptions;

/**
 * Represents an exception that is thrown when the requested number of recent moves exceeds the available move history.
 *
 * @author utobm
 * @version 1.0
 */
public class HistoryExceededException extends Exception {
    /**
     * Default error message for when the requested move history exceeds the available history.
     */
    private static final String HISTORY_EXCEEDED_ERROR = "The requested number of recent moves exceeds the available history.";

    /**
     * Constructs a new HistoryExceededException with a predefined detailed message indicating that
     * the requested number of recent moves exceeds the available history.
     */

    public HistoryExceededException() {
        super(HISTORY_EXCEEDED_ERROR);
    }
}
