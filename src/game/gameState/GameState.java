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

/**
 * GameState is a representation of the actual board' state and manages information about the turn.
 * It contains the player who can play this turn, lists of all units, buildings, priorityUnits,
 *  units which can't attack this turn, the last unit moved this turn, the number of remaining actions for the player and
 *  the state of the board.
 *  @See Board
 *  @See Unit
 *  @See Building
 *  @See EPlayer
 */
public class GameState implements Cloneable {

    private static final int MAX_ACTION = 5;
    // TODO: Maybe need this kind of enum to know in which phase we are (INIT / GAME / END ??)
    //private EStateGame actualPhase;
    private EPlayer actualPlayer;
    private List<Unit> allUnits;
    private List<Building> allBuildings;
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

    /**
     * Add the building reference to the list containing all buildings.
     * @param building The building you want to add.
     */
    public void addBuilding(Building building) {
        allBuildings.add(building); //get a copy of this ?
        board.setBuilding(building.getBuildingData(), building.getPlayer(), building.getX(), building.getY());
    }

    /**
     * Add the unit reference to the list containing all units.
     * @param unit The unit you want to add.
     */
    public void addUnit(Unit unit) {
        allUnits.add(unit); //get a copy of this ?
        board.setUnit(unit.getUnitData(), unit.getPlayer(), unit.getX(), unit.getY());
    }

    /**
     * Add the unit reference to the list containing units that must move.
     * @param unit The unit you want to add.
     */
    public void addPriorityUnit(Unit unit) {
        priorityUnits.add(unit);
    }

    //Need to be sure of the remove of arraylist ..
    //Remove a priority coords to the actual player

    /**
     * Remove the unit having the same coordinates as coords in the priority unit list.
     * @param coords The coordinate of the unit you want to remove from this list.
     */
    public void removePriorityUnit(Coordinates coords) {
        Unit targetUnit = null;
        for (Unit unit : priorityUnits) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                targetUnit = unit;
                break;
            }
        }
        //TODO: If trying to remove a unit not in priority units... ?
        if(targetUnit != null)
            priorityUnits.remove(targetUnit);
    }

    public void setLastUnitMoved(Unit unit){
        this.lastUnitMoved = unit;
    }

    public void setActualPlayer(EPlayer player) {
        actualPlayer = player;
    }

    public EPlayer getActualPlayer() {
        return actualPlayer;
    }

    /**
     * Switch the actual player for this turn to the next one.
     * Clear the units in cantAttackUnits and add the units that must move for the next player
     * in the cantAttackUnits list to prevent his priority units to attack or to participate for an attack this turn.
     * Set to null the lastUnitMoved
     */
    public void switchPlayer() {
        //actualPlayer = EPlayer.values()[(actualPlayer.ordinal() + 1) % EPlayer.values().length];
        actualPlayer = actualPlayer.other();
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

    /**
     * Remove one remaining action to the actual player.
     */
    public void removeOneAction() {
        actionLeft = actionLeft - 1;
        if (actionLeft < 0)
            actionLeft = 0;
    }

    /**
     * Set the variable canMove of the Unit having the same coordinates as coords to false.
     * @param coords The coordinates of the unit you want to disable his canMove.
     */
    public void setUnitHasMoved(Coordinates coords){
        for(Unit unit : allUnits) {
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY()) {
                unit.setCanMove(false);
                lastUnitMoved = unit;
                return;
            }
        }
    }

    /**
     * Create new empty lists and new empty board with the same dimensions,
     * and set the lastUnitMoved to null.
     */
    public void removeAll() {
        allUnits = new ArrayList<>();
        allBuildings = new ArrayList<>();
        priorityUnits = new ArrayList<>();
        cantAttackUnits = new ArrayList<>();
        board = new Board(board.getWidth(), board.getHeight()); // Sure ?
        lastUnitMoved = null;
    }


    /**
     * @return the reference of the list containing all units.
     */
    public List<Unit> getAllUnits(){
        return allUnits;
    }

    /**
     * @return the reference of the list containing all buildings.
     */
    public List<Building> getAllBuildings(){
        return allBuildings;
    }

    /**
     * @return the reference of the list containing all priority units.
     */
    public List<Unit> getPriorityUnits(){
        return priorityUnits;
    }

    /**
     * @return the reference of the list containing all units which can't attack this turn.
     */
    public List<Unit> getCantAttackUnits(){
        return cantAttackUnits;
    }

    /**
     * @return the last unit moved this turn.
     */
    public Unit getLastUnitMoved() throws NullPointerException{
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

    /**
     * @param x The coordinate of the x-axis
     * @param y The coordinate of the y-axis
     * @return True if the coordinates (x;y) is on the board, false otherwise.
     */
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

    /**
     * Remove the unit at position (x;y) from every lists and from the board.
     * @param x The coordinate on x-axis
     * @param y The coordinate on y-axis
     */
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

    public void removeBuilding(Building building){
        board.delBuilding(building.getX(), building.getY());
        allBuildings.remove(building);
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

    /**
     * Move the unit having the same coordinates as (srcX, srcY) to (tgtX, tgtY).
     * @param srcX The source position on x-axis
     * @param srcY The source position on y-axis
     * @param tgtX The target position on x-axis
     * @param tgtY The target position on y-axis
     */
    public void moveUnit(int srcX, int srcY, int tgtX, int tgtY){
        board.moveUnit(srcX, srcY, tgtX, tgtY);

        Unit tmp = null;
        for(Unit u : priorityUnits){
            if(u.getX() == srcX && u.getY() == srcY){
                tmp = u;
                break;
            }
        }
        if(tmp != null)
            priorityUnits.remove(tmp);

        for(Unit u : allUnits){
            if(u.getX() == srcX && u.getY() == srcY){
                u.setPosition(tgtX, tgtY);
                break;
            }
        }
    }

    /**
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param x2 Position2 on x-axis
     * @param y2 Position2 on y-axis
     * @return The distance between (x, y) and (x2, y2) => The max between abs(x - x2) and abs(y - y2).
     */
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

    public String toString(){
        return board.toString();
    }
}
