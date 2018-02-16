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

    public EBuildingData getBuildingData() {
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
}
