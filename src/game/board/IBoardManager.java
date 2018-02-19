package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

public interface IBoardManager {

    void initBoard(int w, int h);

    Board getBoard();

    void revert();

    void clearHistory();

    boolean isEmptyHistory();

    void moveUnit(int x, int y, int x2, int y2);

    void moveBuilding(int x, int y, int x2, int y2);

    void addUnit(EUnitData unitType, EPlayer player, int x, int y);

    void addBuilding(EBuildingData buildingType, EPlayer player, int x, int y);

    void removeUnit(int x, int y);

    void removeBuilding(int x, int y);

    void setCommunication(EPlayer player, int x, int y, boolean value);

    void deleteCommunication(EPlayer player, int x, int y);

    void clearCommunication();
}
