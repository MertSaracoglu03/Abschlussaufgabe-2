package edu.kit.informatik.ui;

/**
 * Represents a command to quit the game or application.
 * This command will stop the game execution and exit the application when invoked.
 *
 * @author Programmieren-Team
 * @version 1.0
 */
public class QuitCommand extends Command {
    /**
     * The name for the quit command.
     */

    private static final String COMMAND_NAME = "quit";
    /**
     * Error message displayed when the quit command is invoked with arguments.
     */
    private static final String QUIT_WITH_ARGUMENTS_ERROR = "Error: quit does not allow args.";
    /**
     * The command handler responsible for handling the quit action.
     */
    private final CommandHandler commandHandler;

    /**
     * Constructs a new QuitCommand with the specified command handler.
     *
     * @param commandHandler The command handler responsible for handling the quit action.
     */

    public QuitCommand(CommandHandler commandHandler) {
        super(COMMAND_NAME);
        this.commandHandler = commandHandler;
    }

    /**
     * Executes the quit command.
     * If any arguments are provided, an error message is displayed.
     * Otherwise, the game or application is exited.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     */
    @Override
    public void execute(String[] commandArguments) {
        if (commandArguments.length != 0) {
            System.err.println(QUIT_WITH_ARGUMENTS_ERROR);
            return;
        }
        commandHandler.quit();
    }
}
