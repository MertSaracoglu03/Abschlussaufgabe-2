package edu.kit.informatik.manager.session;


import edu.kit.informatik.entity.Player;
import edu.kit.informatik.util.vector.Vector2D;

/**
 * Represents a single move made by a player in the game of Hex.
 * This record captures the player who made the move and the position on the board where the move was made.
 */
public record Moves(Player player, Vector2D vector) {

}
