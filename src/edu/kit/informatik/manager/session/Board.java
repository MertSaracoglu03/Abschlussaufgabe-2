package edu.kit.informatik.manager.session;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.manager.exceptions.NotEmptyException;
import edu.kit.informatik.manager.exceptions.OutOfBoundsException;
import edu.kit.informatik.util.graph.ConnectivityChecker;
import edu.kit.informatik.util.vector.Direction2D;
import edu.kit.informatik.util.vector.Vector2D;

import java.util.*;

/**
 * Represents the board of the game.
 * This class encapsulates the state and logic of the game board. It provides functionalities
 * such as placing tokens, checking for player victories, generating a winning board representation,
 * and simulating board states.
 *
 * @author utobm
 * @version 1.0
 */

public class Board {
    /**
     * The size of the board.
     */
    private final int size;
    /**
     * The matrix of entries representing the board state.
     */
    private final Entry[][] entries;


    /**
     * Initializes a new board with the specified size.
     *
     * @param size The size of the board.
     */

    public Board(int size) {
        this.size = size;
        this.entries = new Entry[size][size];
        initializeBoard();


    }
    /**
     * Copy constructor for the {@code Board} class.
     * This constructor creates a new instance of the {@code Board} with the same size
     * and entries as the provided board.
     * It ensures that the created board is a deep
     * copy of the provided board, preventing any reference overlaps.
     *
     * @param board The board whose properties are to be copied to create a new instance.
     */
    private Board(Board board) {
        this.size = board.size;
        this.entries = new Entry[size][size];
        copyEntriesFrom(board);
    }

    /**
     * Initializes the board with empty entries.
     * This method sets all positions on the board to the {@code Entry.EMPTY} state.
     */
    private void initializeBoard() {
        for (Entry[] row : entries) {
            Arrays.fill(row, Entry.EMPTY);
        }
    }
    /**
     * Copies the entries from the provided board to the current board.
     * This method ensures a deep copy of the entries from the provided board to the
     * current board.
     * It avoids reference overlaps, ensuring that changes to one board
     * don't inadvertently affect another.
     *
     * @param board The board from which entries are to be copied.
     */

    private void copyEntriesFrom(Board board) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(board.entries[i], 0, this.entries[i], 0, size);
        }
    }

    /**
     * Places a token on the board at the specified position for the given player.
     *
     * @param y      The y-coordinate of the placement.
     * @param x      The x-coordinate of the placement.
     * @param player The player placing the token.
     * @throws NotEmptyException    If the position is already occupied.
     * @throws OutOfBoundsException if the position is out of boundaries.
     */

    public void placeToken(int y, int x, Player player) throws NotEmptyException, OutOfBoundsException {
        if (!isPositionValid(new Vector2D(x, y))) {
            throw new OutOfBoundsException();
        }
        if (!isEmpty(x, y)) {
            throw new NotEmptyException();
        }
        entries[x][y] = player.getToken();
    }

    /**
     * Checks if the specified player has won the game.
     *
     * @param player The player to check.
     * @return True if the player has won, otherwise false.
     */

    public boolean hasPlayerWon(Player player) {
        ConnectivityChecker checker = new ConnectivityChecker(this);
        Entry token = player.getToken();

        if (token == Entry.X) {
            for (int j = 0; j < size; j++) {
                if (entries[0][j] == token && checker.isConnected(new Vector2D(0, j), player)) {
                    return true;
                }
            }
        } else if (token == Entry.O) {
            for (int i = 0; i < size; i++) {
                if (entries[i][0] == token && checker.isConnected(new Vector2D(i, 0), player)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines if the specified player has won and returns the winning position if applicable.
     * The method checks for winning conditions based on the player's token (`Entry.X` or `Entry.O`).
     * For a player with `Entry.X`, a win is determined by checking if any token in the northernmost row
     * is connected through the board, signaling a completed path.
     * For a player with `Entry.O`, a win is determined by checking if any token in the westernmost column
     * is connected through the board.
     *
     * @param player The player whose win condition is being checked.
     * @return The position of the winning token if the player has won, or null if the player has not won.
     */

    private Vector2D playerWonLocation(Player player) {
        ConnectivityChecker checker = new ConnectivityChecker(this);
        Entry token = player.getToken();

        if (token == Entry.X) {
            for (int j = 0; j < size; j++) {
                if (entries[0][j] == token && checker.isConnected(new Vector2D(0, j), player)) {
                    return new Vector2D(0, j);
                }
            }
        } else if (token == Entry.O) {
            for (int i = 0; i < size; i++) {
                if (entries[i][0] == token && checker.isConnected(new Vector2D(i, 0), player)) {
                    return new Vector2D(i, 0);
                }
            }
        }

        return null;
    }


    /**
     * Generates a string representation of the board highlighting the winning path for the specified player.
     *
     * @param player The winning player.
     * @return A string representation of the winning board.
     */

    public String generateWinningBoardRepresentation(Player player) {
        char[][] representation = Entry.getRepr(entries);
        traverseWinningPath(representation, player);
        return convertToString(representation);
    }


    /**
     * Marks the winning path of the given player on the provided board representation.
     * This method identifies and traverses the path through which the player has achieved a win,
     * marking each position in the path with an asterisk ('*') on the given 2D char array representation.
     * It uses Breadth-First Search (BFS) starting from the player's winning position to traverse the entire
     * winning path.
     * Positions that are part of the winning path are added to the visited set to ensure
     * no position is processed more than once.
     *
     * @param representation The 2D char array representing the game board.
     * @param player         The player whose winning path needs to be marked.
     */

    private void traverseWinningPath(char[][] representation, Player player) {
        Set<Vector2D> visited = new HashSet<>();
        Vector2D startNode = playerWonLocation(player);
        Queue<Vector2D> queue = new ArrayDeque<>(Collections.singleton(startNode));

        while (!queue.isEmpty()) {
            Vector2D current = queue.poll();
            if (!visited.contains(current) && isPositionValid(current)) {
                representation[current.x()][current.y()] = '*';
                visited.add(current);
                queue.addAll(getValidNeighbors(current, player));
            }
        }
    }


    /**
     * Retrieves valid neighboring positions for a given position on the board that also match the player's token.
     *
     * @param position The position for which neighbors are to be found.
     * @param player   The player for which the valid neighbors are being determined.
     * @return A list of valid neighboring positions.
     */
    private List<Vector2D> getValidNeighbors(Vector2D position, Player player) {
        List<Vector2D> neighbors = new ArrayList<>();
        for (Direction2D direction : Direction2D.values()) {
            Vector2D neighbor = position.add(direction.getDirection());
            if (isPositionValid(neighbor) && entries[neighbor.x()][neighbor.y()] == player.getToken()) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Simulates game states to determine a winning location for the given player on the board.
     *
     * @param player The player for which the winning location is being determined.
     * @return The winning location as a Vector2D object or null if no winning location is found.
     */
    public Vector2D getWinningLocation(Player player) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (entries[j][i].equals(Entry.EMPTY)) {
                    Board simulatedBoard = this.copy();
                    try {
                        simulatedBoard.placeToken(i, j, player);
                        if (simulatedBoard.hasPlayerWon(player)) {
                            return new Vector2D(j, i);
                        }
                    } catch (NotEmptyException e) {
                        // This shouldn't happen since we are checking for empty entries
                    } catch (OutOfBoundsException e) {
                        // This shouldn't happen since we are generating in boundaries.

                    }
                }
            }
        }
        return null;
    }


    /**
     * Checks if a given position lies within the boundaries of the board.
     *
     * @param pos The position to be checked.
     * @return True if the position is valid and lies within the board's boundaries, otherwise false.
     */

    public boolean isPositionValid(Vector2D pos) {
        return pos.liesWithinBoundaries(0, size - 1);
    }

    /**
     * Converts a given board representation to a string format.
     *
     * @param representation The board's representation to be converted.
     * @return The string representation of the board.
     */
    private String convertToString(char[][] representation) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(" ".repeat(i));
            for (int j = 0; j < size; j++) {
                builder.append(representation[i][j]);
                if (j < size - 1) {
                    builder.append(' ');
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * returns size of the board.
     *
     * @return size of the board
     */

    public int getSize() {
        return size;
    }

    /**
     * Checks if the specified position on the board is empty.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return True if the position is empty, otherwise false.
     */

    public boolean isEmpty(int x, int y) {
        return entries[x][y].equals(Entry.EMPTY);
    }

    /**
     * Retrieves the entry (token) present at the specified position on the game board.
     *
     * @param position The position in the form of a {@link Vector2D} object specifying the x and y coordinates.
     * @return The {@link Entry} (token) present at the given position.
     *         It can be an Entry.X, Entry.O, or whatever other entries your game supports.
     *         If the position is outside the board boundaries or unoccupied,
     *         the behavior is determined by the underlying data structure (could return null or throw an exception).
     */

    public Entry getEntryAt(Vector2D position) {
        return entries[position.x()][position.y()];
    }


    /**
     * Creates a copy of the current board state.
     *
     * @return A new Board object representing the current board state.
     */

    public Board copy() {
        return new Board(this);
    }

    /**
     * Returns a string representation of the board.
     *
     * @return The string representation of the board.
     */

    @Override
    public String toString() {
        return convertToString(Entry.getRepr(entries));
    }
}
