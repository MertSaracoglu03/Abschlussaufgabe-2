package edu.kit.informatik.ui.command;


import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.exceptions.GameNotFoundException;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents the "switch-game" command in the game, allowing a player to switch between different game sessions.
 * When executed, the command checks if the specified game session exists, and if so, switches to it.
 *
 * @author utobm
 * @version 1.0
 */
public class SwitchGameCommand extends GameManagerCommand {
    /**
     * The name of the switch-game command.
     */
    private static final String COMMAND_NAME = "switch-game";
    private static final String CURRENT_GAME_ERROR_FORMAT = "Switch to current game not allowed.";
    private static final String SWITCH_SUCCESS_FORMAT = "Switched to %s%n";
    /**
     * The expected number of arguments for this command.
     * This command expects one argument - the name of the game session to switch to.
     */
    private static final int NUMBER_OF_ARGUMENTS = 1;
    /**
     * Index position of the game name in the command arguments array.
     */
    private static final int NAME_INDEX = 0;

    /**
     * Constructs a new SwitchGameCommand with the specified game manager.
     *
     * @param manager The game manager that manages the different game sessions.
     */
    public SwitchGameCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the switch-game command.
     * The command attempts to switch to the specified game session.
     * If the game session does not exist, an error message is printed.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     *                         This command expects one argument - the name of the game session to switch to.
     */
    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        String name = commandArguments[NAME_INDEX];
        boolean check;
        try {
            check = manager.switchGame(name);
        } catch (GameNotFoundException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }
        if (!check) {
            System.err.println(createError(CURRENT_GAME_ERROR_FORMAT));
            return;
        }

        System.out.printf(SWITCH_SUCCESS_FORMAT.formatted(name));
    }

}
