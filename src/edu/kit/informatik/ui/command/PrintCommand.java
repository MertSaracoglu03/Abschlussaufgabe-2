package edu.kit.informatik.ui.command;


import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents the "print" command, which outputs the current state of the game board.
 * When executed, this command displays the current game board to the console.
 *
 * @author utobm
 * @version 1.0
 */
public class PrintCommand extends GameManagerCommand {
    /**
     * The name of the print command.
     */
    private static final String COMMAND_NAME = "print";
    /**
     * The expected number of arguments for this command.
     * Since the command does not require any arguments, the value is set to 0.
     */
    private static final int NUMBER_OF_ARGUMENTS = 0;

    /**
     * Constructs a new PrintCommand with the specified game manager.
     *
     * @param manager The game manager whose current game board will be printed.
     */

    public PrintCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the print command.
     * Outputs the current state of the game board to the console.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     *                         This command does not expect any arguments, so the array should be empty.
     */
    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        System.out.print(manager.getCurrentGame().getBoard());
    }
}
