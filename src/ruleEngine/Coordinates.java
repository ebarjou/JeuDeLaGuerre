package ruleEngine;

/**
 * Wrapper for 2D coordinates.
 */
public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The column index of the coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The row index of the coordinate.
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + ";" + y + "]";
    }
}
