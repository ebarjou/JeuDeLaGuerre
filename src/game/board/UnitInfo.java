package game.board;

import game.EPlayer;

class UnitInfo {
    private EUnit id;
    private EPlayer player;

    public UnitInfo(EUnit id, EPlayer player){
        this.id = id;
        this.player = player;
    }

    public EUnit getId() {
        return id;
    }

    void setId(EUnit id) {
        this.id = id;
    }

    public EPlayer getPlayer() {
        return player;
    }

    void setPlayer(EPlayer player) {
        this.player = player;
    }
}
