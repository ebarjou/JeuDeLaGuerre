package game.board;

public enum EInfoType {

    UNIT(UnitInfo.class),
    BUILDING(BuildingInfo.class),
    COMMUNICATION1(Boolean.class),
    COMMUNICATION2(Boolean.class);

    private Class type;

    EInfoType(Class type){
        this.type = type;
    }

    Class getType(){
        return type;
    }
}
