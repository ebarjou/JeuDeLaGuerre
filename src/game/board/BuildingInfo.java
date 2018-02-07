package game.board;

class BuildingInfo {
    private EBuilding id;
    private int player;

    public BuildingInfo(EBuilding id, int player){
        this.id = id;
        this.player = player;
    }

    public EBuilding getId() {
        return id;
    }

    public void setId(EBuilding id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
