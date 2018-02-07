package game.board;

public interface IBoardMaster {

    void init(int w, int h);
    Board getBoard();
    boolean revert();
    void clearHistory();

    boolean moveUnit(int x, int y, int x2, int y2);
    boolean moveBuilding(int x, int y, int x2, int y2);

    boolean addUnit(EUnit unitType, int player, int x, int y);
    boolean addBuilding(EBuilding buildingType, int player, int x, int y);

    Object getValueInfo(EInfoType key, int x, int y);
    void addInfo(EInfoType key, Info info, int x, int y);

    void clearCommunication();
}
