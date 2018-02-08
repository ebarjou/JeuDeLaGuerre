package game.board;

public interface IBoardMaster {

    void initBoard(int w, int h);
    Board getBoard();
    boolean revert();
    void clearHistory();
    boolean isEmptyHistory();

    boolean moveUnit(int x, int y, int x2, int y2);
    boolean moveBuilding(int x, int y, int x2, int y2);

    boolean addUnit(EUnit unitType, int player, int x, int y);
    boolean addBuilding(EBuilding buildingType, int player, int x, int y);

    void setCommunication(int player, int x, int y);
    void deleteCommunication(int player, int x, int y);
    void clearCommunication();

    //boolean moveData(int x, int y, int x2, int y2);
    //boolean addData(Data d, int x, int y);

    //void setData(Data d, int x, int y);
    Object getValueInfo(EInfoType key, int x, int y);

    void addInfo(EInfoType key, Info info, int x, int y);

}
