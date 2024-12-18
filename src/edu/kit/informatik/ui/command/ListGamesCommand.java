package edu.kit.informatik.ui.command;


import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents a command to list all the active games in the game manager.
 * The command, when executed, will retrieve a list of all the active games
 * (games that haven't been won yet) and display them to the user.
 * @author utobm
 * @version 1.0
 */
public class ListGamesCommand extends GameManagerCommand {
    /**
     * The name for the list-games command.
     */
    private static final String COMMAND_NAME = "list-games";
    /**
     * The number of arguments expected by the list-games command.
     * Since the command doesn't take any arguments, the value is set to 0.
     */
    private static final int NUMBER_OF_ARGUMENTS = 0;

    /**
     * Constructs a new ListGamesCommand with the specified game manager.
     *
     * @param manager The game manager used to retrieve the list of active games.
     */
    public ListGamesCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the list-games command.
     * Retrieves and displays the list of all active games from the game manager.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     *                         Expected to be empty for this command.
     */
    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        System.out.println(manager.getGameList());
    }
}
