package edu.kit.informatik.ui.command;


import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.session.HexGame;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents the "swap" command in the game, which allows a player to swap tokens after the first move.
 * When executed, the command checks if a swap is allowed, and if so, performs the swap.
 *
 * @author utobm
 * @version 1.0
 */
public class SwapCommand extends GameManagerCommand {
    /**
     * The name of the swap command.
     */
    private static final String COMMAND_NAME = "swap";
    /**
     * Error message indicating that a swap is not allowed.
     */
    private static final String SWAP_ERROR = "Swap not allowed.";
    /**
     * Success message format for a successful swap.
     */
    private static final String SWAP_SUCCESS_FORMAT = "%s swaps%n";
    /**
     * The expected number of arguments for this command.
     * Since the command does not require any arguments, the value is set to 0.
     */
    private static final int NUMBER_OF_ARGUMENTS = 0;

    /**
     * Constructs a new SwapCommand with the specified game manager.
     *
     * @param manager The game manager that manages the current game session.
     */
    public SwapCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the swap command.
     * The swap is only allowed after the first move of the game. If the swap is permitted, the tokens of the players are swapped.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     *                         This command does not expect any arguments, so the array should be empty.
     */
    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        HexGame currentGame = manager.getCurrentGame();
        if (currentGame.getMoveHistory().size() != 1 || currentGame.hasSwapped()) {
            System.err.println(createError(SWAP_ERROR));
            return;
        }
        System.out.printf(SWAP_SUCCESS_FORMAT.formatted(currentGame.getCurrentPlayer().getName()));
        currentGame.swapTokens();
        if (manager.isPrint()) {
            System.out.print(currentGame.getBoard());
        }
        System.out.printf(TURN_FORMAT.formatted(currentGame.getCurrentPlayer().getName()));


    }
}
