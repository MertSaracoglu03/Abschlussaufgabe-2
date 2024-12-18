package edu.kit.informatik.ui.command;

import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents the 'help' command in the game. This command is used to display
 * a list of all available commands along with a brief description of each.
 *
 * @author utobm
 * @version 1.0
 */
public class HelpCommand extends GameManagerCommand {
    private static final String COMMAND_NAME = "help";
    private static final int NUMBER_OF_ARGUMENTS = 0;

    /**
     * Constructs a new HelpCommand with the specified game manager.
     *
     * @param manager The game manager that the command interacts with.
     */
    public HelpCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        System.out.println("* help: Prints this help message");
        System.out.println("* history: Shows the move history of the current game");
        System.out.println("* list-games: Lists all active games being managed");
        System.out.println("* new-game: Starts a new game with the given name");
        System.out.println("* place: Places the current player's token on the board at the specified (x, y) coordinates");
        System.out.println("* print: Displays the current state of the game board");
        System.out.println("* quit: Quit all games and end program");
        System.out.println("* swap: Swaps the players");
        System.out.println("* switch-game: Switches to another game session with the provided name");

    }
}
