package edu.kit.informatik.manager.exceptions;


import edu.kit.informatik.entity.Player;

/**
 * Represents an exception that is thrown when an action is attempted on a game that has already been concluded.
 *
 * @author utobm
 * @version 1.0
 */

public class GameAlreadyWonException extends Exception {
    /**
     * Default error message format for when a game has already been won.
     */
    private static final String GAME_ALREADY_WON_ERROR
            = "The game has already been won by %s. No further moves allowed.";

    /**
     * Constructs a new GameAlreadyWonException with a detailed message indicating the player who won the game.
     *
     * @param winningPlayer The player who won the game, causing the exception.
     */

    public GameAlreadyWonException(Player winningPlayer) {
        super(GAME_ALREADY_WON_ERROR.formatted(winningPlayer.getName()));
    }
}
