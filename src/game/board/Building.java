package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;

public class Building implements Cloneable {
    private EBuildingData building;
    private EPlayer player;
    private boolean isBroken;
    private int x, y;

    public Building(EBuildingData building, EPlayer player) {
        this.building = building;
        this.player = player;
        isBroken = false;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isBroken(){
        return isBroken;
    }

    public void setBroken(boolean isBroken){
        this.isBroken = isBroken;
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
