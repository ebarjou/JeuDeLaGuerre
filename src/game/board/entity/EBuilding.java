package game.board.entity;

public enum EBuilding{
    MOUNTAIN("Mountain"),
    PASS("Pass"),
    FORTERESS("Forteress"),
    ARSENAL("Arsenal");

    private String name;

    EBuilding(String str){
        name = str;
    }

    public String getName(){
        return name;
    }
}
