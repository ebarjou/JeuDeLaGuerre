package ruleEngine.GameMaster;

import game.EPlayer;
import ruleEngine.items.EBuildingData;
import ruleEngine.items.EUnitData;
import ruleEngine.GameAction.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Cloneable{

    private EPlayer actualPlayer;

    private List<EUnitData> unitsPlayer1;
    private List<EUnitData> unitsPlayer2;

    private List<EBuildingData> buildingPlayer1; // Maybe create a Building class
    private List<EBuildingData> buildingPlayer2; //   like the Unit class ... ?

    private List<Coordinates> priorityUnits; //units needed to be move

    private int actionLeft;
    //private List<Coordinates> priorityUnitsPlayer2; //Maybe just one List is necessary

    GameState(){
        unitsPlayer1 = new ArrayList<>();
        unitsPlayer2 = new ArrayList<>();

        buildingPlayer1 = new ArrayList<>();
        buildingPlayer2 = new ArrayList<>();

        priorityUnits = new ArrayList<>();
        actionLeft = 5;
    }

    void addPriorityUnit(Coordinates coords){
        priorityUnits.add(coords);
    }

    void addBuilding(EPlayer player, EBuildingData building){
        if(player == EPlayer.PLAYER1){
            buildingPlayer1.add(building);
        } else {
            buildingPlayer2.add(building);
        }
    }

    void addUnit(EPlayer player, EUnitData unit){
        if(player == EPlayer.PLAYER1){
            unitsPlayer1.add(unit);
        } else {
            unitsPlayer2.add(unit);
        }
    }

    boolean removePriorityUnit(Coordinates coords){
        for(Coordinates c : priorityUnits){
            if(c.getX() == coords.getX() && c.getY() == coords.getY()) {
                priorityUnits.remove(c);
                return true;
            }
        }
        return false;
    }

    void setPlayer(EPlayer player){
        actualPlayer = player;
    }

    void switchPlayer(){
        if(actualPlayer == EPlayer.PLAYER1)
            actualPlayer = EPlayer.PLAYER2;
        else
            actualPlayer = EPlayer.PLAYER1;
        actionLeft = 5;
    }

    public EPlayer getActualPlayer(){
        return actualPlayer;
    }

    void removeOneAction(){
        actionLeft = actionLeft - 1;
        if(actionLeft < 0){
            actionLeft = 0;
        }
    }

    //TODO: Have to check if the object returned need to clone the Lists
    @Override
    public GameState clone(){
        Object o = null;
        try{
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (GameState) o;
    }
}
