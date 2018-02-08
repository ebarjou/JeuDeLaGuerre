package game.board;

import game.EPlayer;

class UnitInfo implements Cloneable{
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

    @Override
    public UnitInfo clone(){
        Object o = null;
        try{
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (UnitInfo)o;
    }
}
