package game.board;

class UnitInfo {
    private EUnit id;
    private int player;

    public UnitInfo(EUnit id, int player){
        this.id = id;
        this.player = player;
    }

    public EUnit getId() {
        return id;
    }

    public void setId(EUnit id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
