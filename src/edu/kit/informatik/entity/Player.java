package edu.kit.informatik.entity;


import edu.kit.informatik.manager.session.Entry;

/**
 * Represents a player in a game.
 * This class encapsulates the properties of a player, including the player's name and the associated game token
 * (e.g., X or O in Tic-Tac-Toe).
 * It provides functionality to switch the player's token and retrieve the current token.
 *
 * @author Programmieren-Team
 * @version 1.0
 */

public class Player {
    /**
     * The name of the player.
     */

    private final String name;
    /**
     * The game token associated with the player.
     */
    private Entry token;

    /**
     * Initializes a new player with the specified name and game token.
     *
     * @param name  The name of the player.
     * @param token The game token associated with the player.
     */

    public Player(String name, Entry token) {
        this.name = name;
        this.token = token;
    }

    /**
     * Switches the player's token to the other token (e.g., from X to O or vice versa).
     */

    public void switchToken() {
        token = token.equals(Entry.X) ? Entry.O : Entry.X;
    }

    /**
     * Retrieves the player's name.
     *
     * @return The name of the player.
     */

    public String getName() {
        return name;
    }

    /**
     * Retrieves the game token associated with the player.
     *
     * @return The player's game token.
     */

    public Entry getToken() {
        return token;
    }

    /**
     * Creates and returns a copy of this Player instance.
     * This method is used to generate a new instance of Player with the same attributes
     * as the current instance.
     * It ensures that the copied Player object maintains its
     * state and properties.
     *
     * @return A new Player object that is a copy of the current instance.
     */
    public Player copy() {
        return new Player(this.name, this.token);
    }

    /**
     * Returns a string representation of the player's game token.
     *
     * @return The string representation of the player's game token.
     */

    @Override
    public String toString() {
        return String.valueOf(token.getToken());
    }
}
