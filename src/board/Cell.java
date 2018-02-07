package board;

public class Cell{
    private boolean isCommunicate;
    private EntityID building;
    private EntityID unit;

    public Cell(){
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

    /*public Cell clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (Cell)o;
    }
    */

}
