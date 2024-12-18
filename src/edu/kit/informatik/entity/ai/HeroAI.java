package edu.kit.informatik.entity.ai;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.session.Board;
import edu.kit.informatik.manager.session.Entry;
import edu.kit.informatik.manager.session.HexGame;
import edu.kit.informatik.util.vector.Direction2D;
import edu.kit.informatik.util.vector.Vector2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static edu.kit.informatik.entity.ai.AIStrategyHelper.*;


/**
 * Represents an artificial intelligence player named 'HeroAI'.
 * <p>
 * This AI determines its next move based on a more advanced strategy compared to a basic AI. The strategies include:
 * Finding a winning move if available.
 * Preventing the opponent from making a winning move.
 * Trying to occupy the north most west position on the board.
 * Calculate the shortest path to reach the end and make a move accordingly.
 * If no suitable move is found, recursively call the next move with a different approach.
 * It utilizes Breadth-First Search (BFS) to find the shortest path for its moves.
 *
 * @author utobm
 * @version 1.0
 */

public class HeroAI extends Player {
    /**
     * Static name for the HeroAI player.
     */
    private static final String NAME = "HeroAI";
    /**
     * Reference to the game manager.
     */
    private final GameManager manager;


    /**
     * Initializes a new HeroAI player.
     *
     * @param manager The game manager.
     */

    public HeroAI(GameManager manager) {
        super(NAME, Entry.O);
        this.manager = manager;

    }

    /**
     * Determines the next move for HeroAI based on its advanced strategies.
     * If no valid move is found,
     * the method recursively checks for valid moves by treating past moves of the HeroAI as free.
     * The number of past moves considered is increased with each recursive call, and the recursion terminates
     * when all past moves have been considered.
     *
     * @param pastMoves The number of past moves to consider as free in the current iteration.
     *                  For the initial call, this should typically be set to 0.
     * @return A string command representing the next move.
     *        If no valid move is found after considering all past moves, it might return null or a default value.
     */

    public String nextMove(int pastMoves) {

        HexGame currentGame = manager.getCurrentGame();
        Board board = currentGame.getBoard().copy();

        String winningMove = determineWinningMove(board, this);
        if (winningMove != null) {
            return winningMove;
        }

        String preventativeMove = determineBlockingMove(board, manager);
        if (preventativeMove != null) {
            return preventativeMove;
        }

        if (currentGame.getMoveHistory().size() == 1) {
            return findNorthMostWestMove(board);
        }

        String pathMove = findShortestPathMove(board, pastMoves);
        if (pathMove != null) {
            return pathMove;
        }

        // If no suitable move found, recursively call nextMove() treating tried hexagons as free
        // Note: You need to handle and avoid infinite recursion
        return nextMove(pastMoves + 1);
    }

    /**
     * Finds the northernmost unoccupied position in the westernmost column of the board.
     * This method iterates through the rows of the westernmost column of the board
     * looking for an unoccupied cell.
     * If found, it returns the position as a move command;
     * otherwise, it returns null.
     *
     * @param board The current board state.
     * @return A command representing the northernmost unoccupied cell in the westernmost column,
     *        or null if no such cell is found.
     */

    private String findNorthMostWestMove(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.isEmpty(i, 0)) {  // Check the westernmost column
                return convertMoveToCommand(new Vector2D(i, 0));
            }
        }
        return null;
    }

    /**
     * Finds the next move based on the shortest path strategy.
     * Given the last move and a certain board state, this method attempts to find the
     * next move that would create or continue the shortest path to reach the east side of the board.
     * This method utilizes BFS (Breadth-First Search) to explore potential paths.
     *
     * @param board     The current board state.
     * @param pastMoves The number of past moves made.
     * @return A command representing the next move based on the shortest path strategy,
     *        or null if no suitable move is found.
     */
    private String findShortestPathMove(Board board, int pastMoves) {
        int boardSize = board.getSize();
        boolean[][] visited = new boolean[boardSize][boardSize];
        Queue<Vector2D> queue = new LinkedList<>();
        Map<Vector2D, Vector2D> parentMap = new HashMap<>();

        Vector2D lastMove = getLastMove(pastMoves);
        if (lastMove.y() == boardSize - 1) {
            return null;
        }
        queue.add(lastMove);
        visited[lastMove.x()][lastMove.y()] = true;

        while (!queue.isEmpty()) {
            Vector2D current = queue.poll();

            if (current.y() == boardSize - 1) {
                Vector2D backtrack = backtrack(lastMove, current, parentMap);
                assert backtrack != null;
                if (board.getEntryAt(backtrack).equals(Entry.EMPTY)) {
                    return convertMoveToCommand(backtrack);
                } else {
                    continue;
                }
            }

            for (Direction2D dir : Direction2D.values()) {
                Vector2D neighbor = current.add(dir.getDirection());
                if (board.isPositionValid(neighbor) && !visited[neighbor.x()][neighbor.y()]
                        && (board.isEmpty(neighbor.x(), neighbor.y()) || board.getEntryAt(neighbor).equals(Entry.O))) {
                    queue.add(neighbor);
                    visited[neighbor.x()][neighbor.y()] = true;
                    parentMap.put(neighbor, current);
                }
            }
        }

        return null;
    }

    /**
     * Backtracks from the destination position to find the next move after the last move.
     * Using the parentMap, which maps each position to the position from which it was visited,
     * this method backtracks from the destination to find the next position that needs to be visited.
     *
     * @param lastMove    The last move that was made.
     * @param destination The final destination position.
     * @param parentMap   A map from each position to its parent in the search tree.
     * @return The next move's position after the last move, or null if such a move doesn't exist.
     */
    private Vector2D backtrack(Vector2D lastMove, Vector2D destination, Map<Vector2D, Vector2D> parentMap) {
        Vector2D currentNode = destination;

        while (currentNode != null) {
            if (parentMap.get(currentNode).equals(lastMove)) {
                return currentNode;
            }
            currentNode = parentMap.get(currentNode);
        }
        return null;
    }
    /**
     * Retrieves the last move made by the player based on a given number of past moves.
     * Uses a manager to fetch the last move for the player, taking the number of past moves into account.
     * Note: The returned position has its x and y values swapped.
     *
     * @param pastMoves The number of past moves made.
     * @return The position of the last move made by the player.
     */

    private Vector2D getLastMove(int pastMoves) {
        Vector2D vector = manager.getLastMoveForPlayer(this, pastMoves);
        return new Vector2D(vector.y(), vector.x());
    }

    /**
     * Creates and returns a copy of this HeroAI instance.
     * This method is used to create a new instance of HeroAI with the same properties
     * as the current instance.
     * It ensures that the copied HeroAI player retains its
     * behavior and state.
     *
     * @return A new HeroAI object that is a copy of the current instance.
     */
    public HeroAI copy() {
        return new HeroAI(manager);
    }


}
