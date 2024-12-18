package edu.kit.informatik.ui.command;


import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.exceptions.GameAlreadyExistsException;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents a command to start a new game in the game manager.
 * The command, when executed, will attempt to create a new game with a given name.
 * If a game with the provided name already exists or the name is invalid, an error message will be displayed.
 *
 * @author utobm
 * @version 1.0
 */
public class NewGameCommand extends GameManagerCommand {
    /**
     * The name for the new-game command.
     */
    private static final String COMMAND_NAME = "new-game";
    /**
     * Regular expression to validate the name of the game.
     * The game name should not contain any white-space.
     */
    private static final String GAME_NAME_REGEX = "^\\S+$";
    /**
     * Message prefix to display when a new game starts.
     */
    private static final String GAME_BEGIN_PREFIX = "Welcome to %s%n";
    /**
     * Error message to display when the game name is invalid.
     */
    private static final String INVALID_GAME_NAME_ERROR = "Game name is invalid";
    /**
     * The expected number of arguments for this command.
     * Since the command takes one argument, the game's name, the value is set to 1.
     */
    private static final int NUMBER_OF_ARGUMENTS = 1;
    /**
     * Index for the game name argument in the command arguments array.
     */
    private static final int NAME_INDEX = 0;

    /**
     * Constructs a new NewGameCommand with the specified game manager.
     *
     * @param manager The game manager used to create a new game.
     */
    public NewGameCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the new-game command.
     * Tries to create a new game with the specified name in the game manager.
     * If successful, a welcome message for the new game is displayed along with the game board and the name of the current player.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     *                         Expected to contain the game's name as the only argument.
     */
    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        String name = commandArguments[NAME_INDEX];
        if (!name.matches(GAME_NAME_REGEX)) {
            System.err.println(createError(INVALID_GAME_NAME_ERROR));
            return;
        }
        try {
            manager.addNewGame(name);
        } catch (GameAlreadyExistsException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }
        System.out.printf(GAME_BEGIN_PREFIX.formatted(name));
        if (manager.isPrint()) {
            System.out.print(manager.getCurrentGame().getBoard());
        }
        System.out.printf(TURN_FORMAT.formatted(manager.getCurrentPlayer().getName()));
    }
}
