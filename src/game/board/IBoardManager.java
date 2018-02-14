package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

public interface IBoardManager {

    void initBoard(int w, int h);

    Board getBoard();

    boolean revert();

    void clearHistory();

    boolean isEmptyHistory();

    boolean moveUnit(int x, int y, int x2, int y2);

    boolean moveBuilding(int x, int y, int x2, int y2);

    boolean addUnit(EUnitData unitType, EPlayer player, int x, int y);

    boolean addBuilding(EBuildingData buildingType, EPlayer player, int x, int y);

    boolean removeUnit(int x, int y);

    boolean removeBuilding(int x, int y);

    void setCommunication(EPlayer player, int x, int y, boolean value);

    void deleteCommunication(EPlayer player, int x, int y);

    void clearCommunication();
}
