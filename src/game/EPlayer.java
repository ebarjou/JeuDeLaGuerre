package game;

public enum EPlayer {
    PLAYER_NORTH(0),
    PLAYER_SOUTH(1);

    private final int val;

    EPlayer(int val){
        this.val = val;
    }

    public EPlayer other(){
        if (val == 0)
            return PLAYER_SOUTH;
        else
            return PLAYER_NORTH;
    }
}
