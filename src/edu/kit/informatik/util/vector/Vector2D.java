package edu.kit.informatik.util.vector;

/**
 * Represents a 2D vector with integer coordinates (x, y).
 * This record provides a concise representation for a 2D vector along with methods for
 * basic vector operations such as addition and boundary checking.
 *
 * @author utobm
 * @version 1.0
 */

public record Vector2D(int x, int y) {
    /**
     * Adds the given vector to the current vector and returns the resultant vector.
     *
     * @param vector The vector to be added.
     * @return A new {@link Vector2D} that represents the sum of the current vector and the given vector.
     */
    public Vector2D add(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Checks if the current vector lies within the specified boundaries.
     *
     * @param min The minimum boundary value for both x and y coordinates.
     * @param max The maximum boundary value for both x and y coordinates.
     * @return True if the vector lies within the boundaries, otherwise false.
     */

    public boolean liesWithinBoundaries(int min, int max) {
        return this.x >= min && this.x <= max && this.y >= min && this.y <= max;
    }
}
