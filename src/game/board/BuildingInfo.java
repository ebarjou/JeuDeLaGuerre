package game.board;

import game.EPlayer;
import game.board.entity.EBuilding;

public class BuildingInfo implements Cloneable{
    private EBuilding id;
    private EPlayer player;

    public BuildingInfo(EBuilding id, EPlayer player){
        this.id = id;
        this.player = player;
    }

    public EBuilding getId() {
        return id;
    }

    void setId(EBuilding id) {
        this.id = id;
    }

    public EPlayer getPlayer() {
        return player;
    }

    void setPlayer(EPlayer player) {
        this.player = player;
    }

    @Override
    public BuildingInfo clone(){
        Object o = null;
        try{
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (BuildingInfo)o;
    }
}
