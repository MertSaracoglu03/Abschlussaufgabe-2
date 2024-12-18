package edu.kit.informatik;

/**
 * Main entry point for the Hex game application.
 * This class contains the main method that delegates the execution to the Application class.
 *
 * @author utobm
 * @version 1.0
 */
public final class Main {
    /**
     * Private constructor to prevent instantiation.
     * Throws an exception if there's an attempt to instantiate.
     */
    private Main() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * Main method to start the application.
     *
     * @param args Command-line arguments provided to the application.
     */
    public static void main(String[] args) {
        Application.run(args);
    }
}
