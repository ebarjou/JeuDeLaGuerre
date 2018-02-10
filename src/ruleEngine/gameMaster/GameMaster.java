package ruleEngine.gameMaster;

import game.EPlayer;
import ruleEngine.GameAction;
import ruleEngine.items.EUnitData;

import java.util.Random;
import java.util.Stack;

public class GameMaster {

    private static GameMaster instance;
    private Stack<GameState> history;
    private GameState actualState;

    private GameMaster(){ //Maybe singleton
        history = new Stack<>();
        Random r = new Random();
        actualState = new GameState();
        actualState.setPlayer(r.nextInt(1) == 0 ? EPlayer.PLAYER1 : EPlayer.PLAYER2);
    }

    public static GameMaster getInstance(){
        if(instance == null)
            instance = new GameMaster();
        return instance;
    }

    public boolean revert(){
        if(!history.isEmpty())
            return false;
        actualState = history.pop();
        return true;
    }

    public void addUnit(EPlayer player, EUnitData unit){
        history.push(actualState.clone());
        actualState.addUnit(player, unit);
    }

    public void addBuilding(EPlayer player, EUnitData unit){
        history.push(actualState.clone());
        actualState.addUnit(player, unit);
    }

    public GameState getActualState() {
        return actualState;
    }

    public void switchPlayer(){
        while(!history.isEmpty())
            history.pop();

        actualState.switchPlayer();
    }

    //Should be called after each move valid.
    public void removeAction(){
        actualState.removeOneAction();
    }

    public boolean removePriorityUnit(GameAction.Coordinates coords){
        GameState tmp = actualState.clone();
        if(actualState.removePriorityUnit(coords)){
            history.push(tmp);
            return true;
        }
        return false;
    }

    //Some getter / setter / add / remove / revert methods to manipulate the GameMaster
    // ...

}
