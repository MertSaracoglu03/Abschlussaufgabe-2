package edu.kit.informatik;

import edu.kit.informatik.ui.CommandHandler;

/**
 * Main application class for the Hex game.
 * This class validates command-line arguments, creates a new CommandHandler instance, and starts the game loop.
 *
 * @author utobm
 * @version 1.0
 */
public final class Application {

    // Various constants that define argument positions and validation rules
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 12345;
    private static final int SIZE_INDEX = 0;
    private static final int FIRST_PLAYER_NAME_INDEX = 1;
    private static final int SECOND_PLAYER_NAME_INDEX = 2;
    private static final int AUTO_PRINT_INDEX = 3;
    private static final int MIN_NUMBER_OF_ARGUMENTS = 3;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 4;
    private static final int ODD_NUMBERS_MODULO = 2;
    private static final String BOGO_AI_NAME = "BogoAI";
    private static final String HERO_AI_NAME = "HeroAI";
    private static final String NAME_REGEX = "^(?!.*[;" + System.lineSeparator() + "]).*$";
    private static final String AUTO_PRINT = "auto-print";
    private static final String INVALID_ARGUMENTS_ERROR = "Error: Invalid arguments provided!";
    private static final String INVALID_NUMBER_OF_ARGUMENTS_ERROR = "Error: Incorrect number of arguments. "
            + "Expected between " + MIN_NUMBER_OF_ARGUMENTS + " and " + MAX_NUMBER_OF_ARGUMENTS + " arguments.";
    private static final String NAME_REGEX_ERROR = "Error: Player names are in an invalid format.";
    private static final String NAME_EMPTY_ERROR = "Error: Player names cannot be empty.";
    private static final String FIRST_PLAYER_AI_ERROR = "Error: The first player's name cannot be the name of an AI.";
    private static final String SAME_NAME_ERROR = "Error: The names of the players cannot be the same.";
    private static final String INVALID_BOARD_SIZE_ERROR = "Error: Invalid Argument for board size.";
    private static boolean print = false;

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private Application() {
        throw new UnsupportedOperationException("Application class cannot be instantiated");
    }

    /**
     * Starts the game loop with the provided command-line arguments.
     *
     * @param args The command-line arguments, including game board size, player names, and optional auto-print flag.
     */
    public static void run(String[] args) {
        if (isArgumentsValid(args)) {
            CommandHandler commandHandler = new CommandHandler(
                    Integer.parseInt(args[SIZE_INDEX]),
                    args[FIRST_PLAYER_NAME_INDEX],
                    args[SECOND_PLAYER_NAME_INDEX],
                    print
            );

            commandHandler.handleUserInput();
        }
    }

    /**
     * Validates player names. Two players should have different names.
     * Names should match a certain regex pattern and shouldn't be empty.
     *
     * @param args The command-line arguments.
     * @return True if player names are valid, false otherwise.
     */
    private static boolean isNamesValid(String[] args) {
        String nameOfFirstPlayer = args[FIRST_PLAYER_NAME_INDEX];
        String nameOfSecondPlayer = args[SECOND_PLAYER_NAME_INDEX];
        if (nameOfFirstPlayer.equals(nameOfSecondPlayer)) {
            System.err.println(SAME_NAME_ERROR);
            return false;
        }
        if (nameOfFirstPlayer.equals(BOGO_AI_NAME) || nameOfFirstPlayer.equals(HERO_AI_NAME)) {
            System.err.println(FIRST_PLAYER_AI_ERROR);
            return false;
        }
        if (nameOfFirstPlayer.isEmpty() || nameOfSecondPlayer.isEmpty()) {
            System.err.println(NAME_EMPTY_ERROR);
            return false;
        }
        if (!nameOfFirstPlayer.matches(NAME_REGEX) || !nameOfSecondPlayer.matches(NAME_REGEX)) {
            System.err.println(NAME_REGEX_ERROR);
            return false;
        }
        return true;
    }

    /**
     * Validates the game board size.
     * Size should be an odd number between defined minimum and maximum values.
     *
     * @param args The command-line arguments.
     * @return True if the board size is valid, false otherwise.
     */
    private static boolean isSizeValid(String[] args) {
        int size;
        try {
            size = Integer.parseInt(args[SIZE_INDEX]);
        } catch (NumberFormatException e) {
            System.err.println(INVALID_BOARD_SIZE_ERROR);
            return true;
        }

        if (size < MIN_SIZE || size > MAX_SIZE || size % ODD_NUMBERS_MODULO != 1) {
            System.err.println(INVALID_BOARD_SIZE_ERROR);
            return true;
        }

        return false;
    }


    /**
     * Validates the auto-print flag. If the 'auto-print' flag is set, set the print boolean variable to true.
     *
     * @param args The command-line arguments.
     * @return True if the auto-print flag is valid or not provided, false otherwise.
     */
    private static boolean isAutoPrintValid(String[] args) {
        if (AUTO_PRINT.equals(args[AUTO_PRINT_INDEX])) {
            print = true;
            return true;
        }
        System.err.println(INVALID_ARGUMENTS_ERROR);
        return false;
    }

    /**
     * Validates all provided command-line arguments.
     * Checks if the board size, player names, and optional auto-print flag are valid.
     *
     * @param args The command-line arguments.
     * @return True if all arguments are valid, false otherwise.
     */
    private static boolean isArgumentsValid(String[] args) {
        switch (args.length) {
            case MIN_NUMBER_OF_ARGUMENTS -> {
                if (isSizeValid(args)) {
                    return false;
                }
                return isNamesValid(args);
            }
            case MAX_NUMBER_OF_ARGUMENTS -> {
                if (isSizeValid(args)) {
                    return false;
                }
                if (!isNamesValid(args)) {
                    return false;
                }
                return isAutoPrintValid(args);
            }
            default -> {
                System.err.println(INVALID_NUMBER_OF_ARGUMENTS_ERROR);
                return false;
            }
        }
    }
}
