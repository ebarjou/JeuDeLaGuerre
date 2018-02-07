package board;

public class EntityID {
    private int player;
    private String id;
    private boolean isUnit;

    public EntityID(int player, boolean isUnit, String id){
        this.player = player;
        this.id = id;
        this.isUnit = isUnit;
    }

    public String getID(){
        return id;
    }

    public boolean isUnit(){
        return isUnit;
    }

    public int getPlayer(){
        return player;
    }
}
