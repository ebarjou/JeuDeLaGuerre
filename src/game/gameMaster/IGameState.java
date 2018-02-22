package game.gameMaster;

import game.EPlayer;
import game.board.Board;
import game.board.Unit;
import ruleEngine.Coordinates;

public interface IGameState {
    EPlayer getActualPlayer();
    int getActionLeft();
    boolean isUnitHasPriority(Coordinates coords);
    boolean isUnitCanMove(Coordinates coords);
    Unit getLastUnitMoved();
    Board getBoard();
    GameState clone();

}
