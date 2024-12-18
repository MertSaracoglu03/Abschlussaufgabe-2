package edu.kit.informatik.ui;

/**
 * Represents an abstract command that can be executed by the user through the user interface.
 * This class acts as a base for all specific command implementations.
 * @author Programmieren-Team
 * @version 1.0
 */
public abstract class Command {
    /**
     * The name of the command.
     */
    private final String commandName;

    /**
     * Constructs a new Command with the specified name.
     *
     * @param commandName The name of the command.
     */

    protected Command(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Returns the name of the command.
     *
     * @return The name of the command.
     */

    public String getCommandName() {
        return commandName;
    }

    /**
     * Executes the command with the provided arguments.
     * This method should be overridden by all subclasses to provide specific command execution logic.
     *
     * @param commandArguments An array of arguments provided by the user for executing the command.
     */

    public abstract void execute(String[] commandArguments);
}
