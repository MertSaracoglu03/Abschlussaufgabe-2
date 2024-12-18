package edu.kit.informatik.manager.session;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.manager.exceptions.GameAlreadyWonException;
import edu.kit.informatik.manager.exceptions.HistoryExceededException;
import edu.kit.informatik.manager.exceptions.NotEmptyException;
import edu.kit.informatik.manager.exceptions.OutOfBoundsException;
import edu.kit.informatik.util.vector.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of Hex with a given size and set of players.
 * Hex is a strategy board game for two players, with the objective to form a path connecting opposite sides of the board.
 *
 * @author utobm
 * @version 1.0
 */

public class HexGame {
    private final String name;
    private final Board board;
    private final List<Player> players;
    private final List<Moves> moveHistory = new ArrayList<>();
    private int currentPlayerIndex;
    private boolean hasSwapped;
    private Player winningPlayer;


    /**
     * Constructs a Hexagon game with the given name and size.
     *
     * @param name    Name of the game.
     * @param size    Size of the game board.
     * @param players List of players participating in the game.
     */
    public HexGame(String name, int size, List<Player> players) {
        this.name = name;
        this.board = new Board(size);
        this.players = players;
    }

    /**
     * Returns the name of the game.
     *
     * @return The game name.
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the player who has won the game.
     *
     * @return The winning player or null if no player has won yet.
     */

    public Player getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * Checks if the players have swapped their tokens.
     *
     * @return True if the players have swapped, false otherwise.
     */

    public boolean hasSwapped() {
        return hasSwapped;
    }

    /**
     * Returns the current player whose turn it is to make a move.
     *
     * @return The current player.
     */

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Retrieves the opponent of the current player.
     * This method leverages the fact that there are only two players in the game.
     * By subtracting the index of the current player from 1, the method returns
     * the index of the opponent player.
     * For instance, if the current player has
     * an index of zero, the opponent will have an index of one, and vice versa.
     *
     * @return The {@link Player} object representing the opponent of the current player.
     */

    public Player getOpponent() {
        return players.get(1 - currentPlayerIndex);
    }

    /**
     * Moves the game's turn to the next player.
     */
    private void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Retrieves the list of moves made during the game.
     * This method provides a historical record of all moves made by players
     * since the beginning of the game. Each entry in the list represents a move
     * made by a player at a specific point in time.
     *
     * @return A list of {@link Moves} objects representing the moves made.
     */
    public List<Moves> getMoveHistory() {
        return moveHistory;
    }

    /**
     * Places a token on the game board at the specified coordinates for the current player.
     * The method will place the token of the current player at the given coordinates. If the position
     * is already occupied by another token or if the game has already been won, an exception will be thrown.
     * After successfully placing the token, the game checks if the current player has won. If so, the
     * winning player is set; otherwise, the turn is passed to the next player.
     *
     * @param x The x-coordinate of the position where the token should be placed.
     * @param y The y-coordinate of the position where the token should be placed.
     * @throws NotEmptyException       If the specified position is already occupied by a token.
     * @throws GameAlreadyWonException If a player has already won the game and no further moves are allowed.
     * @throws OutOfBoundsException    if the position is out of boundaries.
     */
    public void placeToken(int x, int y) throws NotEmptyException, GameAlreadyWonException, OutOfBoundsException {
        if (winningPlayer != null) {
            throw new GameAlreadyWonException(winningPlayer);
        }
        Player currentPlayer = getCurrentPlayer();
        // Note: The game's coordinate system is inverse to Java's typical array indexing.
        board.placeToken(x, y, currentPlayer);
        moveHistory.add(new Moves(currentPlayer, new Vector2D(x, y)));
        if (board.hasPlayerWon(currentPlayer)) {
            winningPlayer = currentPlayer;
        }
        moveToNextPlayer();
    }

    /**
     * Swaps the tokens of the players.
     */
    public void swapTokens() {
        hasSwapped = true;
        players.forEach(Player::switchToken);
        Moves initialMove = moveHistory.get(0);
        Player otherPlayer = players.stream()
                .filter(p -> !p.equals(initialMove.player()))
                .findFirst()
                .orElseThrow();
        Vector2D movePosition = initialMove.vector();
        moveHistory.set(0, new Moves(otherPlayer, movePosition));
        moveToNextPlayer();
    }

    /**
     * Retrieves the current game board.
     *
     * @return The current state of the game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns a string representation of the most recent moves in the game.
     * The method fetches the specified number of recent moves from the game's history and formats them
     * into a string. Each move is represented as "{playerName}: {x-coordinate} {y-coordinate}". If the
     * requested number of moves exceeds the available history, a `HistoryExceededException` is thrown.
     *
     * @param moveCount The number of recent moves to retrieve.
     * @return A formatted string representing the specified number of recent moves.
     * @throws HistoryExceededException If the requested number of recent moves exceeds the available history.
     */
    public String retrieveRecentMoves(int moveCount) throws HistoryExceededException {
        if (moveCount > moveHistory.size()) {
            throw new HistoryExceededException();
        }
        StringBuilder builder = new StringBuilder();
        int startIndex = Math.max(0, moveHistory.size() - moveCount);
        List<Moves> recentMoves = moveHistory.subList(startIndex, moveHistory.size());

        for (int i = recentMoves.size() - 1; i >= 0; i--) {
            Moves move = recentMoves.get(i);
            builder.append(String.format("%s: %d %d%n", move.player().getName(), move.vector().x(), move.vector().y()));
        }

        return builder.toString();
    }
}
