package edu.kit.informatik.ui.command;


import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.exceptions.HistoryExceededException;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents a command to retrieve the recent move history of the current game.
 * The history command allows players to view recent moves made in the game.
 * The number of recent moves to be retrieved can be specified as an argument.
 * If no argument is provided, the default is to retrieve the most recent move.
 *
 * @author utobm
 * @version 1.0
 */
public class HistoryCommand extends GameManagerCommand {
    /**
     * The name for the history command.
     */
    private static final String COMMAND_NAME = "history";
    /**
     * Default number of recent moves to retrieve if no argument is provided.
     */
    private static final int DEFAULT_MOVES_COUNT = 1;
    /**
     * Index for the number of turns in the command arguments.
     */
    private static final int NUMBER_OF_TURNS_INDEX = 0;
    /**
     * Minimum number of arguments that can be provided to the history command.
     */
    private static final int MIN_NUMBER_OF_ARGUMENTS = 0;
    /**
     * Maximum number of arguments that can be provided to the history command.
     */
    private static final int MAX_NUMBER_OF_ARGUMENTS = 1;

    /**
     * Constructs a new HistoryCommand with the specified game manager.
     *
     * @param manager The game manager used to retrieve the recent move history.
     */
    public HistoryCommand(GameManager manager) {
        super(COMMAND_NAME, manager, MIN_NUMBER_OF_ARGUMENTS, MAX_NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the history command.
     * Retrieves and displays the recent move history based on the provided argument or the default count.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     */
    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        int requestedMoves = (commandArguments.length == MIN_NUMBER_OF_ARGUMENTS)
                ? DEFAULT_MOVES_COUNT
                : parseRequestedMoves(commandArguments);

        if (requestedMoves == -1) {
            System.err.println(INVALID_ARGUMENTS_ERROR);
            return;
        }

        try {
            String recentMovesOutput = manager.getCurrentGame().retrieveRecentMoves(requestedMoves);
            System.out.print(recentMovesOutput);
        } catch (HistoryExceededException e) {
            System.err.println(createError(e.getMessage()));
        }
    }

    /**
     * Parses the requested number of recent moves from the command arguments.
     *
     * @param commandArguments An array of arguments provided by the user.
     * @return The parsed number of requested moves or -1 if the parsing fails.
     */
    private int parseRequestedMoves(String[] commandArguments) {
        int requestedMoves;
        try {
            requestedMoves = Integer.parseInt(commandArguments[NUMBER_OF_TURNS_INDEX]);
        } catch (NumberFormatException ignored) {
            return -1;
        }
        if (requestedMoves == 0) {
            return -1;
        }
        return requestedMoves;
    }
}
