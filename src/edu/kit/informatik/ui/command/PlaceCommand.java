package edu.kit.informatik.ui.command;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.entity.ai.BogoAI;
import edu.kit.informatik.entity.ai.HeroAI;
import edu.kit.informatik.manager.GameManager;
import edu.kit.informatik.manager.exceptions.GameAlreadyWonException;
import edu.kit.informatik.manager.exceptions.NotEmptyException;
import edu.kit.informatik.manager.exceptions.OutOfBoundsException;
import edu.kit.informatik.manager.session.HexGame;
import edu.kit.informatik.ui.GameManagerCommand;

/**
 * Represents the "place" command, allowing players to place a token on the board.
 * This command, when executed, will attempt to place a token for the current player at the specified location on the game board.
 * The command can handle various scenarios such as if the tile is already occupied if the placement is outside the board boundaries,
 * or if the game has already been won.
 *
 * @author utobm
 * @version 1.0
 */
public class PlaceCommand extends GameManagerCommand {
    /**
     * The name for the place command.
     */
    private static final String COMMAND_NAME = "place";
    /**
     * Message prefix to display when a player wins.
     */
    private static final String WINNING_PREFIX = "%s wins!%n";
    /**
     * The expected number of arguments for this command.
     * Since the command takes two arguments, the X and Y coordinates, the value is set to 2.
     */
    private static final int NUMBER_OF_ARGUMENTS = 2;
    /**
     * Indexes for the X and Y coordinates in the command arguments array.
     */
    private static final int X = 0;
    private static final int Y = 1;

    /**
     * Constructs a new PlaceCommand with the specified game manager.
     *
     * @param manager The game manager used to place a token.
     */
    public PlaceCommand(GameManager manager) {
        super(COMMAND_NAME, manager, NUMBER_OF_ARGUMENTS, NUMBER_OF_ARGUMENTS);
    }

    /**
     * Executes the place command.
     * Attempts to place a token for the current player at the specified location on the game board.
     * Handles various scenarios and outputs appropriate messages for each.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     *                         Expected to contain the X and Y coordinates where the token should be placed.
     */

    @Override
    protected void executeTaskManagerCommand(String[] commandArguments) {
        int x;
        int y;
        try {
            x = Integer.parseInt(commandArguments[X]);
            y = Integer.parseInt(commandArguments[Y]);
        } catch (NumberFormatException ignored) {
            System.err.println(INVALID_ARGUMENTS_ERROR);
            return;
        }
        HexGame currentGame = manager.getCurrentGame();
        Player currentPlayer = currentGame.getCurrentPlayer();

        try {
            currentGame.placeToken(x, y);
        } catch (NotEmptyException | GameAlreadyWonException | OutOfBoundsException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }
        if (currentPlayer instanceof BogoAI) {
            System.out.printf(BOGO_AI_PLACE_PREFIX.formatted(x, y));
        } else if (currentPlayer instanceof HeroAI) {
            System.out.printf(HERO_AI_PLACE_PREFIX.formatted(x, y));
        }
        Player winningPlayer = (currentGame.getWinningPlayer());
        if (winningPlayer != null) {
            System.out.printf(WINNING_PREFIX.formatted(winningPlayer.getName()));
            System.out.print(currentGame.getBoard().generateWinningBoardRepresentation(winningPlayer));
            return;
        }
        if (manager.isPrint()) {
            System.out.print(currentGame.getBoard());
        }
        System.out.printf(TURN_FORMAT.formatted(manager.getCurrentPlayer().getName()));
    }
}
