package edu.kit.informatik.entity.ai;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.session.Board;
import edu.kit.informatik.manager.session.Entry;
import edu.kit.informatik.manager.session.HexGame;
import edu.kit.informatik.util.vector.Vector2D;

import static edu.kit.informatik.entity.ai.AIStrategyHelper.*;

/**
 * Represents an artificial intelligence player named 'BogoAI'.
 * The AI determines its next move based on a set of strategies, such as
 * Finding a winning move.
 * Preventing the opponent from winning.
 * Opting for a swap move.
 * Making a symmetric move in response to the opponent's last move.
 * If none of the above apply, make a random move.
 *
 * @author utobm
 * @version 1.0
 */


public class BogoAI extends Player {

    /**
     * Static name for the BogoAI player.
     */
    private static final String NAME = "BogoAI";
    /**
     * Reference to the game manager.
     */
    private final GameManager manager;


    /**
     * Initializes a new BogoAI player.
     *
     * @param manager The game manager.
     */

    public BogoAI(GameManager manager) {
        super(NAME, Entry.O);
        this.manager = manager;

    }

    /**
     * Determines the next move for BogoAI.
     *
     * @return A string command representing the next move.
     */
    public String nextMove() {
        HexGame currentGame = manager.getCurrentGame();
        Board board;
        board = currentGame.getBoard().copy();

        String winningMove = determineWinningMove(board, this);
        if (winningMove != null) {
            return winningMove;
        }

        String preventativeMove = determineBlockingMove(board, manager);
        if (preventativeMove != null) {
            return preventativeMove;
        }

        String swapMove = findSwapMove(currentGame);
        if (swapMove != null) {
            return swapMove;
        }
        String symmetricMove = getSymmetricMove(currentGame);
        if (symmetricMove != null) {
            return symmetricMove;
        }

        return findRandomMove(board);
    }
    /**
     * Determines if a swap move is possible based on the current game state.
     * In this specific Hex game implementation, after the first move, the opposing player
     * can decide to "swap" positions if certain conditions are met.
     * This method checks
     * whether the swap condition is satisfied and if so, returns the string "swap".
     *
     * @param currentGame The current state of the Hex game.
     * @return "swap" if a swap is possible based on the game's current state, otherwise returns null.
     */

    private String findSwapMove(HexGame currentGame) {
        if (currentGame.getMoveHistory().size() == 1) {
            Vector2D vector = currentGame.getMoveHistory().get(0).vector();
            if ((vector.x() + vector.y()) % 2 == 0) {
                return "swap";
            }
        }
        return null;
    }
    /**
     * Calculates the symmetric move for a given game state.
     * The symmetric move is the one that mirrors the last move about the center of the board.
     * If the symmetric move is valid (i.e., the position on the board is empty), then it
     * returns the symmetric move as a command.
     * Otherwise, it returns null.
     *
     * @param currentGame The current state of the Hex game.
     * @return A command representing the symmetric move if it's valid, otherwise returns null.
     */
    private String getSymmetricMove(HexGame currentGame) {
        int boardSize = currentGame.getBoard().getSize();
        Vector2D lastMove = currentGame.getMoveHistory().get(currentGame.getMoveHistory().size() - 1).vector();
        int symmetricY = boardSize - 1 - lastMove.x();
        int symmetricX = boardSize - 1 - lastMove.y();
        if (currentGame.getBoard().isEmpty(symmetricX, symmetricY)) {
            return convertMoveToCommand(new Vector2D(symmetricX, symmetricY));
        }
        return null;
    }
    /**
     * Finds a random move on the board that has not been occupied.
     * This method iterates over the entire board, looking for the first empty cell.
     * It returns this cell as a move command.
     * If no such cell is found (i.e., the board is full),
     * it returns null.
     *
     * @param board The current board state.
     * @return A command representing the first found empty cell, or null if the board is full.
     */

    private String findRandomMove(Board board) {
        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isEmpty(i, j)) {
                    return convertMoveToCommand(new Vector2D(i, j));
                }
            }
        }
        return null;
    }

    /**
     * Creates and returns a copy of this BogoAI instance.
     * This method is used to create a new instance of BogoAI with the same properties
     * as the current instance.
     * It ensures that the copied BogoAI player retains its
     * behavior and state.
     *
     * @return A new BogoAI object that is a copy of the current instance.
     */
    public BogoAI copy() {
        return new BogoAI(manager);
    }
}
