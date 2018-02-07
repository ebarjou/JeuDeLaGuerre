package board;

public class Cell {
    private int x, y;
    private boolean isCommunicate;
    private EntityID building;
    private EntityID unit;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        isCommunicate = false;
        building = null;
        unit = null;


    }

    public boolean isCommunicate() {
        return isCommunicate;
    }

    public void setCommunicate(boolean communicate) {
        isCommunicate = communicate;
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public EntityID getBuilding() {
        return building;
    }

    public void setBuilding(EntityID building) {
        this.building = building;
    }

    public EntityID getUnit() {
        return unit;
    }

    public void setUnit(EntityID unit) {
        this.unit = unit;
    }
}
