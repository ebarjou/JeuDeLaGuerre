package game.gameMaster;

import game.EPlayer;
import game.board.Board;
import game.board.Unit;
import ruleEngine.Coordinates;
import ruleEngine.entity.EBuildingData;

import java.util.ArrayList;
import java.util.List;

public class GameState implements IGameState, Cloneable {

    private static final int MAX_ACTION = 5;
    // TODO: Maybe need this kind of enum to know in which phase we are (INIT / GAME / END ??)
    //private EStateGame actualPhase;
    private EPlayer actualPlayer;
    private List<Unit> unitsPlayerNorth;
    private List<Unit> unitsPlayerSouth;
    private List<EBuildingData> buildingPlayerNorth; // Maybe create a Building class
    private List<EBuildingData> buildingPlayerSouth; //   like the Unit class ... ?
    private List<Unit> priorityUnitsNorth; //units needed to be move
    private List<Unit> priorityUnitsSouth;
    private Unit lastUnitMoved;
    private Board board;
    private int actionLeft;

    public GameState(int w, int h) {
        board = new Board(w, h);
        lastUnitMoved = null;

        unitsPlayerNorth = new ArrayList<>();
        unitsPlayerSouth = new ArrayList<>();

        buildingPlayerNorth = new ArrayList<>();
        buildingPlayerSouth = new ArrayList<>();

        priorityUnitsNorth = new ArrayList<>();
        priorityUnitsSouth = new ArrayList<>();
        actionLeft = 5;

        actualPlayer = EPlayer.PLAYER_NORTH;
    }

    public GameState(Board board) {
        this.board = board;
        lastUnitMoved = null;

        unitsPlayerNorth = new ArrayList<>();
        unitsPlayerSouth = new ArrayList<>();

        buildingPlayerNorth = new ArrayList<>();
        buildingPlayerSouth = new ArrayList<>();

        priorityUnitsNorth = new ArrayList<>();
        priorityUnitsSouth = new ArrayList<>();
        actionLeft = 5;

        actualPlayer = EPlayer.PLAYER_NORTH;
    }

    public void addBuilding(EPlayer player, EBuildingData building) {
        if (player == EPlayer.PLAYER_NORTH) {
            buildingPlayerNorth.add(building);
        } else {
            buildingPlayerSouth.add(building);
        }
    }

    public void addUnit(Unit unit) {
        if (unit.getPlayer() == EPlayer.PLAYER_NORTH) {
            unitsPlayerNorth.add(unit);
        } else {
            unitsPlayerSouth.add(unit);
        }
    }

    public void addPriorityUnit(Unit unit) {
        if(unit.getPlayer() == EPlayer.PLAYER_NORTH)
            priorityUnitsNorth.add(unit);
        else
            priorityUnitsSouth.add(unit);
    }

    //Need to be sure of the remove of arraylist ..
    //Remove a priority coords to the actual player
    public void removePriorityUnit(Coordinates coords) {
        List<Unit> priority;
        Unit targetUnit = null;
        if (actualPlayer == EPlayer.PLAYER_NORTH)
            priority = priorityUnitsNorth;
        else
            priority = priorityUnitsSouth;

        for (Unit unit : priority) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                targetUnit = unit;
                break;
            }
        }
        priority.remove(targetUnit);
    }

    public boolean isUnitHasPriority(Coordinates coords) {
        List<Unit> priority;
        if (actualPlayer == EPlayer.PLAYER_NORTH)
            priority = priorityUnitsNorth;
        else
            priority = priorityUnitsSouth;

        if(priority.isEmpty())
            return true;

        for (Unit unit : priority)
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return true;
        return false;
    }

    public void setActualPlayer(EPlayer player) {
        actualPlayer = player;
    }

    public EPlayer getActualPlayer() {
        return actualPlayer;
    }

    public void switchPlayer() {
        List<Unit> units = unitsPlayerSouth;
        if(actualPlayer == EPlayer.PLAYER_NORTH)
            units = unitsPlayerNorth;
        for(Unit unit : units) {
            unit.setCanAttack(true);
            unit.setCanMove(true);
        }
        actualPlayer = EPlayer.values()[(actualPlayer.ordinal() + 1) % EPlayer.values().length];
        actionLeft = MAX_ACTION;
    }

    public int getActionLeft() {
        return actionLeft;
    }

    public void setActionLeft(int n) {
        this.actionLeft = n;
    }

    public void removeOneAction() {
        actionLeft = actionLeft - 1;
        if (actionLeft < 0)
            actionLeft = 0;
    }

    public void removeAll() {
        unitsPlayerNorth = new ArrayList<>();
        unitsPlayerSouth = new ArrayList<>();
        buildingPlayerNorth = new ArrayList<>();
        buildingPlayerSouth = new ArrayList<>();
        priorityUnitsNorth = new ArrayList<>();
        priorityUnitsSouth = new ArrayList<>();
        lastUnitMoved = null;
    }

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board board){this.board = board;}

    public void updateUnitPosition(EPlayer player, Coordinates src, Coordinates target){
        List<Unit> units;
        if(player == EPlayer.PLAYER_NORTH)
            units = unitsPlayerNorth;
        else
            units = unitsPlayerSouth;
        for(Unit u : units){
            if(u.getX() == src.getX() && u.getY() == src.getY()){
                u.setPosition(target.getX(), target.getY());
            }
        }
    }

    public void addUnitMoved(Coordinates coords){
        for(Unit unit : unitsPlayerNorth) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                unit.setCanMove(false);
                try {
                    lastUnitMoved.setCanAttack(false);
                } catch (NullPointerException e){}
                lastUnitMoved = unit;
                return;
            }
        }
        for(Unit unit : unitsPlayerSouth) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                unit.setCanMove(false);
                try {
                    lastUnitMoved.setCanAttack(false);
                } catch (NullPointerException e){}
                lastUnitMoved = unit;
                return;
            }
        }
    }
/*
    public Unit getUnit(Coordinates coords){
        for(Unit u : unitsPlayerSouth)
            if(u.getX() == coords.getX() && u.getY() == coords.getY())
                return u;
        for(Unit u : unitsPlayerNorth)
            if(u.getX() == coords.getX() && u.getY() == coords.getY())
                return u;
        throw new NullPointerException();
    }*/

    public Unit getLastUnitMoved(){
        if(lastUnitMoved == null){
            throw new NullPointerException();
        }
        return lastUnitMoved;
    }

    public boolean isUnitCanMove(Coordinates coords){
        for(Unit unit : unitsPlayerNorth)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                if(unit.getCanMove())
                    return true;
        for(Unit unit : unitsPlayerSouth)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                if(unit.getCanMove())
                    return true;
        return false;
    }

    private List cloneArrays(List<Unit> array){
        List<Unit> newArray = new ArrayList<>();
        for(Unit unit : array){
            newArray.add(unit.clone());
        }
        return newArray;
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
        assert o != null;
        ((GameState) o).unitsPlayerNorth = cloneArrays(unitsPlayerNorth);
        ((GameState) o).unitsPlayerSouth = cloneArrays(unitsPlayerSouth);
        ((GameState) o).priorityUnitsNorth = cloneArrays(priorityUnitsNorth);
        ((GameState) o).priorityUnitsSouth = cloneArrays(priorityUnitsSouth);
        ((GameState) o).buildingPlayerNorth = new ArrayList<>(buildingPlayerNorth);
        ((GameState) o).buildingPlayerSouth = new ArrayList<>(buildingPlayerSouth);
        ((GameState) o).lastUnitMoved = null;
        ((GameState) o).board = board.clone();
        return (GameState) o;
    }
}
