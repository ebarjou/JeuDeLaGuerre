package game.gameMaster;

import game.EPlayer;
import ruleEngine.GameAction;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

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

    public void setPlayer(EPlayer player){
        actualState.setPlayer(player);
    }

    public void setActionLeft(int n){
        actualState.setActionLeft(n);
    }

    public boolean revert(){
        if(history.isEmpty())
            return false;
        actualState = history.pop();
        return true;
    }

    public void addUnit(EPlayer player, EUnitData unit){
        history.push(actualState.clone());
        actualState.addUnit(player, unit);
    }

    public void addBuilding(EPlayer player, EBuildingData building){
        history.push(actualState.clone());
        actualState.addBuilding(player, building);
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
        history.push(actualState.clone());
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

    public void removeAll(){
        actualState.removeAll();
    }

    //Some getter / setter / add / remove / revert methods to manipulate the GameMaster
    // ...

}
