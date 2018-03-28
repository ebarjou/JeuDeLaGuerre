package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingProperty;

public class Building implements Cloneable {
    private final EBuildingProperty building;
    private final EPlayer player;
    private int x, y;

    public Building(EBuildingProperty building, EPlayer player) {
        this.building = building;
        this.player = player;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EBuildingProperty getBuildingData() {
        return building;
    }

    public EPlayer getPlayer() {
        return player;
    }

    @Override
    public Building clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Building) o;
    }

    public String toString() {
        return "Type " + building + " ; player = " + player + " ; pos (" + x + ";" + y + ")\n";
    }
}
