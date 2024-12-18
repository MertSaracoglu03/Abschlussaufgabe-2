package edu.kit.informatik.util.graph;

import edu.kit.informatik.entity.Player;
import edu.kit.informatik.manager.session.Board;
import edu.kit.informatik.manager.session.Entry;
import edu.kit.informatik.util.vector.Direction2D;
import edu.kit.informatik.util.vector.Vector2D;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * This class is responsible for checking the connectivity of a given player's tokens
 * on a board, ensuring a path exists from a starting position to the board's edge.
 * The class utilizes Breadth-First Search (BFS) to determine the connectivity.
 * The connectivity rules are based on the game's mechanics:
 * - For Entry.X, the goal is to connect from the left edge to the right edge.
 * - For Entry.O, the goal is to connect from the top edge to the bottom edge.
 *
 * @author utobm
 * @version 1.0
 */

public class ConnectivityChecker {

    private final Board board;
    private final int size;

    /**
     * Initializes a new ConnectivityChecker with the provided board.
     *
     * @param board The game board on which connectivity is to be checked.
     */

    public ConnectivityChecker(Board board) {
        this.board = board;
        this.size = board.getSize();
    }

    /**
     * Checks if there's a connected path for the given player's token from the
     * starting position to the respective edge of the board.
     *
     * <p>
     * For Entry.X, the method checks for a path from the starting position to the right edge.
     * For Entry.O, the method checks for a path from the starting position to the bottom edge.
     * </p>
     *
     * @param start  The starting position on the board from which to begin the check.
     * @param player The player whose token's connectivity is to be verified.
     * @return {@code true} if a connected path exists, {@code false} otherwise.
     */
    public boolean isConnected(Vector2D start, Player player) {
        Entry targetToken = player.getToken();
        Set<Vector2D> visited = new HashSet<>();
        Queue<Vector2D> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Vector2D current = queue.poll();
            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            if (targetToken == Entry.X && current.x() == size - 1) {
                return true;
            } else if (targetToken == Entry.O && current.y() == size - 1) {
                return true;
            }

            for (Direction2D direction : Direction2D.values()) {
                Vector2D neighbor = current.add(direction.getDirection());
                if (board.isPositionValid(neighbor) && board.getEntryAt(neighbor) == targetToken && !visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }

        return false;
    }
}
