package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;

public interface IBoard {
    int getWidth();

    int getHeight();

    boolean isValidCoordinate(int x, int y);

    boolean isInCommunication(EPlayer player, int x, int y);

    boolean isBuilding(int x, int y);

    EBuildingProperty getBuildingType(int x, int y);

    EPlayer getBuildingPlayer(int x, int y);

    boolean isUnit(int x, int y);

    EUnitProperty getUnitType(int x, int y);

    EPlayer getUnitPlayer(int x, int y);

    boolean isMarked(int x, int y);

    void setMarked(int x, int y, boolean mark);

    void clearMarked();

    int getDistance(int x, int y, int x2, int y2);
}
