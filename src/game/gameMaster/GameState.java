package game.gameMaster;

import game.EPlayer;
import ruleEngine.GameAction.Coordinates;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Cloneable {

    private EPlayer actualPlayer;
    // TODO: Maybe need this kind of enum to know in which phase we are (INIT / GAME / END ??)
    //private EStateGame actualPhase;

    private List<EUnitData> unitsPlayer1;
    private List<EUnitData> unitsPlayer2;

    private List<EBuildingData> buildingPlayer1; // Maybe create a Building class
    private List<EBuildingData> buildingPlayer2; //   like the Unit class ... ?

    private List<Coordinates> priorityUnits1; //units needed to be move
    private List<Coordinates> priorityUnits2;

    private int actionLeft;
    private static final int MAX_ACTION = 5;

    GameState() {
        unitsPlayer1 = new ArrayList<>();
        unitsPlayer2 = new ArrayList<>();

        buildingPlayer1 = new ArrayList<>();
        buildingPlayer2 = new ArrayList<>();

        priorityUnits1 = new ArrayList<>();
        priorityUnits2 = new ArrayList<>();
        actionLeft = 5;
    }

    void addPriorityUnit(Coordinates coords) {
        priorityUnits1.add(coords);
    }

    void addBuilding(EPlayer player, EBuildingData building) {
        if (player == EPlayer.PLAYER1) {
            buildingPlayer1.add(building);
        } else {
            buildingPlayer2.add(building);
        }
    }

    void addUnit(EPlayer player, EUnitData unit) {
        if (player == EPlayer.PLAYER1) {
            unitsPlayer1.add(unit);
        } else {
            unitsPlayer2.add(unit);
        }
    }

    //Need to be sure of the remove of arraylist ..
    //Remove a priority coords to the actual player
    boolean removePriorityUnit(Coordinates coords) {
        List<Coordinates> priority;
        Coordinates targetCoords = null;
        if(actualPlayer == EPlayer.PLAYER1)
            priority = priorityUnits1;
        else
            priority = priorityUnits2;

        for(Coordinates c : priority){
            if (c.getX() == coords.getX() && c.getY() == coords.getY()) {
                targetCoords = c;
                break;
            }
        }
        if(targetCoords == null)
            return false;
        priority.remove(targetCoords);
        return true;
    }

    void setPlayer(EPlayer player) {
        actualPlayer = player;
    }

    void switchPlayer() {
        if (actualPlayer == EPlayer.PLAYER1)
            actualPlayer = EPlayer.PLAYER2;
        else
            actualPlayer = EPlayer.PLAYER1;
        actionLeft = MAX_ACTION;
    }

    public EPlayer getActualPlayer() {
        return actualPlayer;
    }

    public int getActionLeft() {
        return actionLeft;
    }

    public boolean isPriorityCoord(Coordinates coords){
        List<Coordinates> priority;
        if(actualPlayer == EPlayer.PLAYER1)
            priority = priorityUnits1;
        else
            priority = priorityUnits2;

        for(Coordinates c : priority)
            if(c.getX() == coords.getX() && c.getY() == coords.getY())
                return true;
        return false;

    }

    void setActionLeft(int n) {
        this.actionLeft = n;
    }

    void removeOneAction() {
        actionLeft = actionLeft - 1;
        if (actionLeft < 0)
            actionLeft = 0;
    }

    void removeAll() {
        unitsPlayer1    = new ArrayList<>();
        unitsPlayer2    = new ArrayList<>();
        buildingPlayer1 = new ArrayList<>();
        buildingPlayer2 = new ArrayList<>();
        priorityUnits1  = new ArrayList<>();
        priorityUnits2  = new ArrayList<>();
    }

    //TODO: Have to check if the object returned need to clone the Lists
    @Override
    public GameState clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        ((GameState) o).unitsPlayer1    = new ArrayList<>(unitsPlayer1);
        ((GameState) o).unitsPlayer2    = new ArrayList<>(unitsPlayer2);
        ((GameState) o).priorityUnits1  = new ArrayList<>(priorityUnits1);
        ((GameState) o).priorityUnits2  = new ArrayList<>(priorityUnits2);
        ((GameState) o).buildingPlayer1 = new ArrayList<>(buildingPlayer1);
        ((GameState) o).buildingPlayer2 = new ArrayList<>(buildingPlayer2);
        return (GameState) o;
    }
}
