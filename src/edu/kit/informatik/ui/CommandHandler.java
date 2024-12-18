package edu.kit.informatik.ui;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.entity.ai.BogoAI;
import edu.kit.informatik.entity.ai.HeroAI;
import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.session.HexGame;
import edu.kit.informatik.ui.command.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles user input and executes corresponding game commands.
 * This class represents the main interface for the user to interact with the game.
 * Users can input commands, and the `CommandHandler` will process the input and execute the appropriate game action.
 *
 * @author Programmieren-Team
 * @version 1.0
 */
public class CommandHandler {
    /**
     * Defines the pattern for splitting the user's command and arguments.
     */
    private static final String COMMAND_SEPARATOR = "\\s+";
    /**
     * Error message format for an unrecognized command.
     */
    private static final String COMMAND_NOT_FOUND = "Error: Command '%s' not found%n";
    /**
     * Game manager that manages different game sessions and players.
     */
    private final GameManager gameManager;
    /**
     * Map that holds the different game commands available to the user.
     */
    private final Map<String, Command> commands;
    /**
     * Flag that indicates if the command handler is currently running.
     */
    private boolean running = false;

    /**
     * Constructs a new CommandHandler with the specified game parameters.
     *
     * @param size             The size of the game board.
     * @param firstPlayerName  Name of the first player.
     * @param secondPlayerName Name of the second player.
     * @param print            Flag indicating if the board should be printed after each move.
     */
    public CommandHandler(int size, String firstPlayerName, String secondPlayerName, boolean print) {
        gameManager = new GameManager(size, firstPlayerName, secondPlayerName, print);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    /**
     * Gets the next command input from the user or AI.
     *
     * @param scanner The scanner for reading user input.
     * @return The next command input.
     */
    private String getInput(Scanner scanner) {
        String input;
        HexGame currentGame = gameManager.getCurrentGame();
        Player currentPlayer = currentGame.getCurrentPlayer();
        if (currentPlayer instanceof HeroAI && currentGame.getWinningPlayer() == null) {
            input = ((HeroAI) currentPlayer).nextMove(0);
        } else if (currentPlayer instanceof BogoAI && currentGame.getWinningPlayer() == null) {
            input = ((BogoAI) currentPlayer).nextMove();
        } else {
            input = scanner.nextLine();

        }
        return input;
    }

    /**
     * Handles user input by reading and executing commands until the user quits.
     */
    public void handleUserInput() {
        this.running = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                executeCommand(getInput(scanner));
            }
        }
    }

    /**
     * Signals the command handler to stop processing input.
     */
    protected void quit() {
        this.running = false;
    }

    /**
     * Processes and execute the specified command with its arguments.
     *
     * @param commandWithArguments The command and its arguments as a single string.
     */
    private void executeCommand(String commandWithArguments) {
        String[] splitCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR);
        String commandName = splitCommand[0];
        String[] arguments = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);
        if (!commands.containsKey(commandName)) {
            System.err.printf(COMMAND_NOT_FOUND, commandName);
            return;
        }
        commands.get(commandName).execute(arguments);
    }

    /**
     * Adds a new game command to the command map.
     *
     * @param command The command to add.
     */
    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }

    /**
     * Initializes the available game commands.
     */
    private void initCommands() {
        addCommand(new PlaceCommand(gameManager));
        addCommand(new HistoryCommand(gameManager));
        addCommand(new SwapCommand(gameManager));
        addCommand(new PrintCommand(gameManager));
        addCommand(new ListGamesCommand(gameManager));
        addCommand(new NewGameCommand(gameManager));
        addCommand(new SwitchGameCommand(gameManager));
        addCommand(new QuitCommand(this));
        addCommand(new HelpCommand(gameManager));
    }
}
