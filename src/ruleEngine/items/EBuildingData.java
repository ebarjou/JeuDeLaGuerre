package ruleEngine.items;

import game.board.entity.EBuilding;

public enum EBuildingData {
    MOUNTAIN_STATS(EBuilding.MOUNTAIN, false, 0),
    PASS_STATS(EBuilding.PASS, true, 2),
    FORTRESS_STATS(EBuilding.FORTERESS, true, 4),
    ARSENAL_STATS(EBuilding.ARSENAL, true, 0);

    private EBuilding buildingID;
    private boolean accessible; // the name was crossable before.. ?
    private int bonusDef;

    public static EBuildingData getDataFromEBuilding(EBuilding unitType) {
        switch (unitType) {
            case MOUNTAIN:  return MOUNTAIN_STATS;
            case PASS:      return PASS_STATS;
            case FORTERESS: return FORTRESS_STATS;
            case ARSENAL:   return ARSENAL_STATS;
            default:    return null;
        }
    }

    EBuildingData(EBuilding bID, boolean cross, int def){
        this.buildingID = bID;
        this.accessible = cross;
        this.bonusDef = def;
    }

    public EBuilding getBuildingID(){
        return buildingID;
    }

    public boolean isAccessible() { return accessible; }

    public int getBonusDef() { return bonusDef; }
}
