package edu.kit.informatik.manager;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.entity.ai.BogoAI;
import edu.kit.informatik.entity.ai.HeroAI;
import edu.kit.informatik.manager.exceptions.GameAlreadyExistsException;
import edu.kit.informatik.manager.exceptions.GameNotFoundException;
import edu.kit.informatik.manager.session.Entry;
import edu.kit.informatik.manager.session.HexGame;
import edu.kit.informatik.manager.session.Moves;
import edu.kit.informatik.util.vector.Vector2D;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the games of Hex being played. Responsible for creating, switching, and monitoring games.
 * This class also keeps track of the players and the current game being played.
 *
 * @author utobm
 * @version 1.0
 */
public class GameManager {
    private static final String DEFAULT_GAME_NAME = "Prime";
    private final int size;
    private final boolean print;
    private final List<Player> players = new ArrayList<>();
    private final LinkedHashMap<String, HexGame> gameSessions = new LinkedHashMap<>();
    private HexGame currentGame;

    /**
     * Constructs a new game manager.
     *
     * @param size               The size of the board for the games.
     * @param nameOfFirstPlayer  The name of the first player.
     * @param nameOfSecondPlayer The name of the second player (could be an AI).
     * @param print              Flag indicating whether to print the game board after each move.
     */

    public GameManager(int size, String nameOfFirstPlayer, String nameOfSecondPlayer, boolean print) {
        this.size = size;
        initializePlayers(nameOfFirstPlayer, nameOfSecondPlayer);
        this.print = print;
        initializeDefaultGame();

    }
    /**
     * Initializes players for the game based on the provided names.
     * The first player is always initialized with the token {@code Entry.X}.
     * For the second player:
     * - If the name is "HeroAI", a {@code HeroAI} instance is created.
     * - If the name is "BogoAI", a {@code BogoAI} instance is created.
     * - Otherwise, a regular {@code Player} instance with the token {@code Entry.O} is created.
     * Both players are then added to the player list.
     * </p>
     *
     * @param nameOfFirstPlayer  The name of the first player.
     * @param nameOfSecondPlayer The name of the second player, which can also be the name of a specific AI.
     */

    private void initializePlayers(String nameOfFirstPlayer, String nameOfSecondPlayer) {
        Player firstPlayer = new Player(nameOfFirstPlayer, Entry.X);
        Player secondPlayer = switch (nameOfSecondPlayer) {
            case "HeroAI" -> new HeroAI(this);
            case "BogoAI" -> new BogoAI(this);
            default -> new Player(nameOfSecondPlayer, Entry.O);
        };

        players.add(firstPlayer);
        players.add(secondPlayer);
    }
    /**
     * Initializes the default game session with a fresh board and players.
     * The method first creates a deep copy of the player list.
     * Then, it initializes a new
     * {@code HexGame} instance with the default game name, board size, and copied players.
     * This game instance is added to the game sessions map.
     * If print mode is enabled, the
     * initial game state and the name of the player who has the first move are printed.
     */


    private void initializeDefaultGame() {
        List<Player> copiedPlayers = players.stream().map(Player::copy).collect(Collectors.toList());
        currentGame = new HexGame(DEFAULT_GAME_NAME, size, copiedPlayers);
        gameSessions.put(DEFAULT_GAME_NAME, currentGame);
        System.out.println("Welcome to " + DEFAULT_GAME_NAME);
        if (print) {
            System.out.print(currentGame.getBoard());
        }
        System.out.println(getCurrentPlayer().getName() + "'s turn");
    }

    /**
     * Returns the current game being played.
     *
     * @return The current game.
     */
    public HexGame getCurrentGame() {
        return currentGame;
    }

    /**
     * Returns the current player whose turn it is in the game.
     *
     * @return The current player.
     */

    public Player getCurrentPlayer() {
        return currentGame.getCurrentPlayer();
    }

    /**
     * Creates a new game with the given name.
     *
     * @param name The name of the new game.
     * @throws GameAlreadyExistsException If a game with the given name already exists.
     */

    public void addNewGame(String name) throws GameAlreadyExistsException {
        if (gameSessions.containsKey(name)) {
            throw new GameAlreadyExistsException(name);
        }
        List<Player> copiedPlayers = players.stream().map(Player::copy).collect(Collectors.toList());
        currentGame = new HexGame(name, size, copiedPlayers);
        gameSessions.put(name, currentGame);
    }

    /**
     * Retrieves a past move made by the specified player.
     *
     * @param player The player whose move is to be retrieved.
     * @param pastMoves The number of moves to go back in the history for the specified player.
     * @return The Vector2D representing the move, or null if the player hasn't made that many moves.
     */

    public Vector2D getLastMoveForPlayer(Player player, int pastMoves) {
        int counter = 0;
        List<Moves> moveHistory = getCurrentGame().getMoveHistory();
        for (int i = moveHistory.size() - 1; i >= 0; i--) {
            Moves move = moveHistory.get(i);
            if (move.player().equals(player)) {
                if (pastMoves == counter) {
                    return (move.vector());

                }
                counter++;
            }
        }
        return null;  // This player hasn't made a move yet.
    }

    /**
     * Switches the active game session to another game specified by its name.
     * If the provided game name is the same as the currently active game or if the game name does not exist
     * within the managed game sessions, appropriate actions or exceptions are triggered.
     *
     * @param name The name of the game session to switch to.
     * @return A boolean value indicating if the game switch was successful.
     * @throws GameNotFoundException If there is no game session with the specified name in the managed sessions.
     */
    public boolean switchGame(String name) throws GameNotFoundException {
        if (!gameSessions.containsKey(name)) {
            throw new GameNotFoundException(name);
        }
        if (currentGame.getName().equals(name)) {
            return false;
        }
        currentGame = gameSessions.get(name);
        return true;
    }

    private List<HexGame> getActiveGames() {
        return gameSessions.values().stream()
                .filter(game -> game.getWinningPlayer() == null)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of active games being managed.
     *
     * @return A formatted string containing the list of active games.
     */
    public String getGameList() {
        List<HexGame> games = getActiveGames();


        return games.stream()
                .map(game -> game.getName() + ": " + (game.getMoveHistory().size()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Checks whether the game board should be printed after each move.
     *
     * @return True if the game board should be printed, otherwise false.
     */
    public boolean isPrint() {
        return print;
    }
}
