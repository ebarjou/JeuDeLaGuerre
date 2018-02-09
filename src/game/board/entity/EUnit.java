package game.board.entity;

public enum EUnit {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery"),
    ARTILLERY_HORSE("Artillery_horse"),
    RELAY("Relay"),
    RELAY_HORSE("Relay_horse");

    private String name;

    EUnit(String s){
        name = s;
    }

    public String getName(){
        return name;
    }
}
