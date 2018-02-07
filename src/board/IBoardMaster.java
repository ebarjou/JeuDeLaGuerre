package board;

public interface IBoardMaster {

    void init(int w, int h);
    IBoard getBoard();
    boolean revert();
    void clearHistory();

    boolean move(int x, int y, int x2, int y2, boolean unit);
    void addInfo(String key, Info i, int x, int y);
    void clearCommunication();

    //void setCommunication(int x, int y, boolean isCommunicate);
    //void setBuilding(EntityID e, int x, int y);
    //void setUnit(EntityID e, int x, int y);


}
