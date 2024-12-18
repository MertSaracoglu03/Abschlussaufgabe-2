package edu.kit.informatik.entity.ai;

import edu.kit.informatik.entity.Player;
import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.session.Board;
import edu.kit.informatik.util.vector.Vector2D;

/**
 * Helper class providing AI-related strategy functions.
 * Assists AI players in determining strategic moves such as winning moves or blocking opponent moves.
 *
 * <p>This class should not be instantiated, and all methods are static for utility purposes.</p>
 *
 * @author utobm
 * @version 1.0
 */
public final class AIStrategyHelper {
    private AIStrategyHelper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * Identifies a potential winning move for the provided player on the given board.
     *
     * @param board  the current game board.
     * @param player the player for whom a winning move is sought.
     * @return a command string for the winning move in the format "place y x",
     *        or null if no winning move is found.
     */
    public static String determineWinningMove(Board board, Player player) {
        Vector2D winningPosition = board.getWinningLocation(player);
        if (winningPosition != null) {
            return convertMoveToCommand(winningPosition);
        }
        return null;
    }

    /**
     * Identifies a move that blocks the opponent from potentially winning on the given board.
     *
     * @param board   the current game board.
     * @param manager the game manager to determine the current opponent.
     * @return a command string for the move that prevents the opponent from winning in the format "place y x",
     *        or null if no such preventative move is found.
     */
    public static String determineBlockingMove(Board board, GameManager manager) {
        Vector2D blockPosition = board.getWinningLocation(manager.getCurrentGame().getOpponent());
        if (blockPosition != null) {
            return convertMoveToCommand(blockPosition);
        }
        return null;
    }

    /**
     * Converts a move vector into the appropriate command format.
     *
     * @param movePosition the vector indicating the location of the move.
     * @return a command string in the format "place y x".
     */
    public static String convertMoveToCommand(Vector2D movePosition) {
        return "place " + movePosition.y() + " " + movePosition.x();
    }
}
