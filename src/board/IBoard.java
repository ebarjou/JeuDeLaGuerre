package board;

public interface IBoard {
    int getWidth();
    int getHeight();

    Info getInfo(String key, int x, int y);
    boolean isInfo(String key, int x, int y);

    //boolean moveUnit(int x, int y, int x2, int y2);
    //boolean moveBuilding(int x, int y, int x2, int y2);
    /*
    boolean getCommunication(int x, int y);
    void clearCommunication();

    EntityID getBuilding(int x, int y);
    EntityID getUnit(int x, int y);

    boolean isEmptyUnit(int x, int y);
    boolean isEmptyBuilding(int x, int y);
    */
}
