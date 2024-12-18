package edu.kit.informatik.ui;


import edu.kit.informatik.manager.GameManager;

/**
 * Represents an abstract command that interacts with the game manager.
 * This class acts as a base for all specific game manager command implementations.
 * @author Programmieren-Team
 * @version 1.0
 */
public abstract class GameManagerCommand extends Command {
    protected static final String INVALID_ARGUMENTS_ERROR = createError("Given arguments are invalid.");
    protected static final String EXPECTED_INNER_ARGUMENTS_ERROR = createError("Invalid number of arguments.");
    protected static final String BOGO_AI_PLACE_PREFIX = "BogoAI places at %d %d%n";
    protected static final String HERO_AI_PLACE_PREFIX = "HeroAI places at %d %d%n";
    protected static final String ERROR_PREFIX = "Error: ";
    protected static final String TURN_FORMAT = "%s's turn%n";

    /**
     * The game manager instance that the command interacts with.
     */
    protected final GameManager manager;
    /**
     * The minimum number of arguments required by the command.
     */
    private final int minNumberOfArguments;
    /**
     * The maximum number of arguments allowed by the command.
     */
    private final int maxNumberOfArguments;

    /**
     * Constructs a new GameManagerCommand with the specified name, game manager,
     * and range of valid arguments.
     *
     * @param commandName          The name of the command.
     * @param manager              The game manager that the command interacts with.
     * @param minNumberOfArguments The minimum number of arguments required by the command.
     * @param maxNumberOfArguments The maximum number of arguments allowed by the command.
     */
    protected GameManagerCommand(String commandName, GameManager manager, int minNumberOfArguments, int maxNumberOfArguments) {
        super(commandName);

        this.manager = manager;
        this.minNumberOfArguments = minNumberOfArguments;
        this.maxNumberOfArguments = maxNumberOfArguments;
    }

    /**
     * Executes the command with the provided arguments.
     * This method validates the number of arguments before executing the specific command logic.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     */
    @Override
    public final void execute(String[] commandArguments) {
        if (commandArguments.length > maxNumberOfArguments || commandArguments.length < minNumberOfArguments) {
            System.err.println(EXPECTED_INNER_ARGUMENTS_ERROR);
            return;
        }
        executeTaskManagerCommand(commandArguments);
    }

    /**
     * Executes the specific game manager command logic with the provided arguments.
     * This method should be overridden by all subclasses to provide specific command execution logic.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     */
    protected abstract void executeTaskManagerCommand(String[] commandArguments);

    /**
     * Creates and returns a formatted error message with the provided message.
     *
     * @param message The error message content.
     * @return The formatted error message.
     */
    protected static String createError(String message) {
        return ERROR_PREFIX + message;
    }
}
