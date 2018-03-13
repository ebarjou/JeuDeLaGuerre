package game.gameState;

import game.EPlayer;
import game.board.Board;
import game.board.Building;
import game.board.Unit;
import ruleEngine.Coordinates;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.ArrayList;
import java.util.List;

public class GameState implements IGameState, Cloneable {

    private static final int MAX_ACTION = 5;
    // TODO: Maybe need this kind of enum to know in which phase we are (INIT / GAME / END ??)
    //private EStateGame actualPhase;
    private EPlayer actualPlayer;
    private List<Unit> allUnits;
    private List<Building> allBuildings; // Maybe create a Building class
    private List<Unit> priorityUnits; //units needed to be move
    private List<Unit> cantAttackUnits; //units which can't attack for this turn
    private Unit lastUnitMoved;
    private Board board;
    private int actionLeft;

    public GameState(int w, int h) {
        board = new Board(w, h);
        lastUnitMoved = null;
        allUnits      = new ArrayList<>();
        allBuildings  = new ArrayList<>();
        priorityUnits = new ArrayList<>();
        cantAttackUnits = new ArrayList<>();

        actionLeft   = 5;
        actualPlayer = EPlayer.PLAYER_NORTH;
    }

    public GameState(Board board) {
        this.board    = board;
        lastUnitMoved = null;
        allUnits      = new ArrayList<>();
        allBuildings  = new ArrayList<>();
        priorityUnits = new ArrayList<>();
        cantAttackUnits = new ArrayList<>();

        actionLeft   = 5;
        actualPlayer = EPlayer.PLAYER_NORTH;
    }

    public void addBuilding(Building building) {
        allBuildings.add(building); //get a copy of this ?
        board.setBuilding(building.getBuildingData(), building.getPlayer(), building.getX(), building.getY());
    }

    public void addUnit(Unit unit) {
        allUnits.add(unit); //get a copy of this ?
        board.setUnit(unit.getUnitData(), unit.getPlayer(), unit.getX(), unit.getY());
    }

    public void addPriorityUnit(Unit unit) {
        priorityUnits.add(unit);
    }

    //Need to be sure of the remove of arraylist ..
    //Remove a priority coords to the actual player
    public void removePriorityUnit(Coordinates coords) {
        Unit targetUnit = null;
        for (Unit unit : priorityUnits) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                targetUnit = unit;
                break;
            }
        }
        //TODO: If trying to remove a unit not in priority units... ?
        if(targetUnit == null)
            return;
        priorityUnits.remove(targetUnit);
    }

    public void setActualPlayer(EPlayer player) {
        actualPlayer = player;
    }

    public EPlayer getActualPlayer() {
        return actualPlayer;
    }

    public void switchPlayer() {
        actualPlayer = EPlayer.values()[(actualPlayer.ordinal() + 1) % EPlayer.values().length];
        cantAttackUnits.clear();
        for(Unit unit : allUnits) {
            unit.setCanMove(true);
            if(unit.getPlayer() == actualPlayer) {
                for (Unit pUnit : priorityUnits) {
                    if (unit.getX() == pUnit.getX() && unit.getY() == pUnit.getY()) {
                        cantAttackUnits.add(unit);
                    }
                }
            }
        }
        actionLeft = MAX_ACTION;
        lastUnitMoved = null;
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
        allUnits = new ArrayList<>();
        allBuildings = new ArrayList<>();
        priorityUnits = new ArrayList<>();
        cantAttackUnits = new ArrayList<>();
        board = new Board(board.getWidth(), board.getHeight()); // Sure ?
        lastUnitMoved = null;
    }

    //TODO: Should be cloned or not ? (less methods if not cloned)
    public List<Unit> getAllUnits(){
        return allUnits;
    }

    public List<Building> getAllBuildings(){
        return allBuildings;
    }

    public List<Unit> getPriorityUnits(){
        return priorityUnits;
    }

    public Unit getLastUnitMoved(){
        if(lastUnitMoved == null){
            throw new NullPointerException();
        }
        return lastUnitMoved;
    }

    public int getWidth(){
        return board.getWidth();
    }

    public int getHeight(){
        return board.getHeight();
    }

    public boolean isValidCoordinate(int x, int y){
        return board.isValidCoordinate(x, y);
    }

    public boolean isInCommunication(EPlayer player, int x, int y){
        return board.isInCommunication(player, x, y);
    }

    public void setInCommunication(EPlayer player, int x, int y, boolean enable){
        board.setInCommunication(player, x, y, enable);
    }

    public void clearCommunication(){
        board.clearCommunication();
    }

    public boolean isBuilding(int x, int y){
        return board.isBuilding(x, y);
    }

    public EBuildingData getBuildingType(int x, int y){
        return board.getBuildingType(x, y);
    }

    public EPlayer getBuildingPlayer(int x, int y){
        return board.getBuildingPlayer(x, y);
    }

    public boolean isUnit(int x, int y){
        return board.isUnit(x, y);
    }

    public void removeUnit(int x, int y){
        board.delUnit(x, y);
        Unit remove = null;
        for(Unit u : allUnits){
            if(u.getX() == x && u.getY() == y) {
                remove = u;
                break;
            }
        }
        if(remove != null)
            allUnits.remove(remove);

        for(Unit u : priorityUnits){
            if(u.getX() == x && u.getY() == y) {
                remove = u;
                break;
            }
        }
        if(remove != null)
            priorityUnits.remove(remove);
    }

    public EUnitData getUnitType(int x, int y){
        return board.getUnitType(x, y);
    }

    public EPlayer getUnitPlayer(int x, int y){
        return board.getUnitPlayer(x, y);
    }

    public boolean isMarked(int x, int y){
        return board.isMarked(x, y);
    }

    public void setMarked(int x, int y, boolean mark){
        board.setMarked(x, y, mark);
    }

    public void clearMarked(){
        board.clearMarked();
    }

    public void moveUnit(int srcX, int srcY, int tgtX, int tgtY){
        board.moveUnit(srcX, srcY, tgtX, tgtY);
        for(Unit u : allUnits){
            if(u.getX() == srcX && u.getY() == srcY){
                u.setPosition(tgtX, tgtY);
                break;
            }
        }
    }

    public int getDistance(int x, int y, int x2, int y2){
        return board.getDistance(x, y, x2, y2);
    }

    private List<Building> cloneBuilding(List<Building> array){
        List<Building> newArray = new ArrayList<>();
        for(Building building : array){
            newArray.add(building.clone());
        }
        return newArray;
    }

    private List<Unit> cloneUnits(List<Unit> array){
        List<Unit> newArray = new ArrayList<>();
        for(Unit unit : array){
            newArray.add(unit.clone());
        }
        return newArray;
    }

    @Override
    public GameState clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert o != null;
        ((GameState) o).allUnits = cloneUnits(allUnits);
        ((GameState) o).priorityUnits = cloneUnits(priorityUnits);
        ((GameState) o).cantAttackUnits = cloneUnits(cantAttackUnits);
        ((GameState) o).allBuildings = cloneBuilding(allBuildings);
        ((GameState) o).lastUnitMoved = null;
        if(lastUnitMoved != null)
            ((GameState) o).lastUnitMoved = lastUnitMoved.clone();
        ((GameState) o).board = board.clone();
        return (GameState) o;
    }
    /*
        public IBoard getBoard(){
        return board;
    }

    public Board getMutableBoard(){
        return board;
    }


    public boolean isUnitCanMove(Coordinates coords){
        for(Unit unit : allUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                if(unit.getCanMove())
                    return true;
        return false;
    }

    public boolean isUnitCanAttack(Coordinates coords){
        for(Unit unit : cantAttackUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;
        return true;
    }

    // one of units of player 'Player' has priority ?
    @Override
    public boolean isPriorityUnitPlayer(EPlayer player) {
        for(Unit unit : priorityUnits)
            if(unit.getPlayer() == player)
                return true;
        return false;
    }

        public void updateUnitPosition(Coordinates src, Coordinates target){
        for(Unit u : allUnits){
            if(u.getX() == src.getX() && u.getY() == src.getY()){
                u.setPosition(target.getX(), target.getY());
            }
        }
    }

    public void setUnitHasMoved(Coordinates coords){
        for(Unit unit : allUnits) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                unit.setCanMove(false);
                lastUnitMoved = unit;
                return;
            }
        }
    }

    public void setUnitHasAttacked(Coordinates coords) {
        for(Unit unit : allUnits) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                lastUnitMoved = null;
                return;
            }
        }

    }

        public boolean isUnitHasPriority(Coordinates coords) {
        if(priorityUnits.isEmpty())
            return true;

        boolean isUnitActualPlayer = false;
        for (Unit unit : priorityUnits) {
            if(unit.getPlayer() == actualPlayer)
                isUnitActualPlayer = true;
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                return true;
            }
        }

        return !isUnitActualPlayer;
    }

    */
}
