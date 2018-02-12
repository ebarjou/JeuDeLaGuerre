package ruleEngine.entity;


public enum EBuildingData {
    MOUNTAIN("M", false, 0),
    PASS("CO", true, 2),
    FORTRESS("F", true, 4),
    ARSENAL("AR", true, 0);

    private String id;
    private boolean accessible; // the name was crossable before.. ?
    private int bonusDef;

    EBuildingData(String id, boolean cross, int def){
        this.id = id;
        this.accessible = cross;
        this.bonusDef = def;
    }

    public String getID(){
        return id;
    }

    public boolean isAccessible() { return accessible; }

    public int getBonusDef() { return bonusDef; }
}
