package edu.kit.informatik.util.vector;

/**
 * Enumerates the possible 2D directions using {@link Vector2D} representations.
 * This enum provides six standard directions in a 2D space: UP_LEFT, UP_RIGHT, LEFT, RIGHT, DOWN_LEFT, and DOWN_RIGHT.
 * Each direction is associated with a {@link Vector2D} that represents the movement in that direction.
 *
 * @author utobm
 * @version 1.0
 */

public enum Direction2D {
    /**
     * Represents the upward and leftward direction in a 2D space.
     */
    UP_LEFT(new Vector2D(0, -1)),
    /**
     * Represents the upward and rightward direction in a 2D space.
     */
    UP_RIGHT(new Vector2D(1, -1)),
    /**
     * Represents the leftward direction in a 2D space.
     */
    LEFT(new Vector2D(-1, 0)),
    /**
     * Represents the rightward direction in a 2D space.
     */
    RIGHT(new Vector2D(1, 0)),
    /**
     * Represents the downward and leftward direction in a 2D space.
     */
    DOWN_LEFT(new Vector2D(-1, 1)),
    /**
     * Represents the downward and rightward direction in a 2D space.
     */
    DOWN_RIGHT(new Vector2D(0, 1));

    /**
     * The vector representation of the direction.
     */
    private final Vector2D direction;

    /**
     * Constructor to initialize the vector representation of the direction.
     *
     * @param direction The vector representing the direction.
     */

    Direction2D(Vector2D direction) {
        this.direction = direction;
    }

    /**
     * Retrieves the vector representation of the direction.
     *
     * @return The vector representation of the direction.
     */

    public Vector2D getDirection() {
        return direction;
    }
}
