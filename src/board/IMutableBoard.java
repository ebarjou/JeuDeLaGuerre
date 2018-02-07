package board;

public interface IMutableBoard extends IBoard, Cloneable {

    boolean move(int x, int y, int x2, int y2, boolean unit);
    void addInfo(String key, Info i, int x, int y);
    void clearCommunication();
    /*
    void setBuilding(EntityID e, int x, int y);
    void setCommunication(int x, int y, boolean isCommunicate);
    void setUnit(EntityID e, int x, int y);
    */
    IMutableBoard clone();
}
