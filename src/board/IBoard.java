package board;

public interface IBoard {
    int getWidth();
    int getHeight();

    boolean move(int x, int y, int x2, int y2, boolean unit);
    //boolean moveUnit(int x, int y, int x2, int y2);
    //boolean moveBuilding(int x, int y, int x2, int y2);

    void setCommunication(int x, int y, boolean isCommunicate);
    boolean getCommunication(int x, int y);
    void clearCommunication();


    EntityID getBuilding(int x, int y);
    EntityID getUnit(int x, int y);

    void setBuilding(EntityID e, int x, int y);
    void setUnit(EntityID e, int x, int y);

}
