package game.board;

import game.EPlayer;

class BuildingInfo {
    private EBuilding id;
    private EPlayer player;

    public BuildingInfo(EBuilding id, EPlayer player){
        this.id = id;
        this.player = player;
    }

    public EBuilding getId() {
        return id;
    }

    public void setId(EBuilding id) {
        this.id = id;
    }

    public EPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EPlayer player) {
        this.player = player;
    }
}
