package game.gameState;

import game.EPlayer;
import game.board.Building;
import game.board.Unit;
import ruleEngine.Coordinates;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.List;

public interface IGameState {
    EPlayer getActualPlayer();
    int getActionLeft();
    Unit getLastUnitMoved();
    GameState clone();

    void addBuilding(Building building);
    void addUnit(Unit unit);
    void addPriorityUnit(Unit unit);
    void removePriorityUnit(Coordinates coords);
    void setActualPlayer(EPlayer player);
    void switchPlayer();
    void setActionLeft(int n);
    void removeOneAction();
    void removeAll();
    List<Unit> getAllUnits();
    List<Unit> getPriorityUnits();
    List<Unit> getCantAttackUnits();
    List<Building> getAllBuildings();
    int getWidth();
    int getHeight();
    boolean isValidCoordinate(int x, int y);
    boolean isInCommunication(EPlayer player, int x, int y);
    void setInCommunication(EPlayer player, int x, int y, boolean enable);
    void clearCommunication();
    boolean isBuilding(int x, int y);
    EBuildingData getBuildingType(int x, int y);
    EPlayer getBuildingPlayer(int x, int y);
    boolean isUnit(int x, int y);
    void removeUnit(int x, int y);
    EUnitData getUnitType(int x, int y);
    EPlayer getUnitPlayer(int x, int y);
    void setMarked(int x, int y, boolean mark);
    void clearMarked();
    void moveUnit(int x, int y, int x2, int y2);
    int getDistance(int x, int y, int x2, int y2);
    void setUnitHasMoved(Coordinates coords);
    void setLastUnitMoved(Unit u);
    String toString();



    /*
    boolean isUnitHasPriority(Coordinates coords);
    boolean isUnitCanMove(Coordinates coords);
    boolean isUnitCanAttack(Coordinates coords);
    */
    //IBoard getBoard();
    //boolean isPriorityUnitPlayer(EPlayer player);
}
