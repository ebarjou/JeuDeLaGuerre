package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;

public class Building implements Cloneable {
    private EBuildingData building;
    private EPlayer player;

    public Building(EBuildingData building, EPlayer player) {
        this.building = building;
        this.player = player;
    }

    public EBuildingData getBuilding() {
        return building;
    }

    void setBuilding(EBuildingData building) {
        this.building = building;
    }

    public EPlayer getPlayer() {
        return player;
    }

    void setPlayer(EPlayer player) {
        this.player = player;
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
}
