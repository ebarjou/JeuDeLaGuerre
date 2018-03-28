package game;

import javafx.scene.paint.Color;

public enum EPlayer {
    PLAYER_NORTH(0, Color.ORANGERED),
    PLAYER_SOUTH(1, Color.BLUE);

    private final int val;
    private final Color preferedColor;

    EPlayer(int val, Color preferedColor) {
        this.val = val;
        this.preferedColor = preferedColor;
    }

    public EPlayer next() {
        if (val == 0)
            return PLAYER_SOUTH;

        return PLAYER_NORTH;
    }

    @Override
    public String toString() {
        switch (val) {
            case 0:
                return "North Player";
            default:
                return "South Player";
        }

    }

    public Color getPreferedColor() {
        return preferedColor;
    }
}
