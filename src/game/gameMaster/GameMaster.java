package game.gameMaster;

import game.EPlayer;
import ruleEngine.GameAction;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Random;
import java.util.Stack;

public class GameMaster {
    private Stack<GameState> history;
    private GameState actualState;

    public GameMaster() {
        history = new Stack<>();
        Random r = new Random();
        actualState = new GameState();
        actualState.setPlayer(r.nextInt(1) == 0 ? EPlayer.PLAYER1 : EPlayer.PLAYER2);
    }

    public void setPlayer(EPlayer player) {
        actualState.setPlayer(player);
    }

    public void setActionLeft(int n) {
        actualState.setActionLeft(n);
    }

    public void revert() {
        if (!history.isEmpty())
            actualState = history.pop();
    }

    public void addUnit(EPlayer player, EUnitData unit) {
        history.push(actualState.clone());
        actualState.addUnit(player, unit);
    }

    public void addBuilding(EPlayer player, EBuildingData building) {
        history.push(actualState.clone());
        actualState.addBuilding(player, building);
    }

    public GameState getActualState() {
        return actualState;
    }

    public void switchPlayer() {
        history = new Stack<>();
        actualState.switchPlayer();
    }

    //Should be called after each move valid.
    public void removeAction() {
        history.push(actualState.clone());
        actualState.removeOneAction();
    }

    public void removePriorityUnit(GameAction.Coordinates coords) {
        GameState tmp = actualState.clone();
        if (actualState.removePriorityUnit(coords)) {
            history.push(tmp);
        }
    }

    public void removeAll() {
        actualState.removeAll();
    }

}
