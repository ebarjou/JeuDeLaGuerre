package game.board;

import game.EPlayer;
import game.board.exceptions.IllegalBoardCallException;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Arrays;

public class Board implements IBoard{
    private final static int MASK_TYPE = 0x00FF;
    private final static int MASK_PLAYER = 0xFF00;
    private int height, width;
    private short[] units;
    private short[] buildings;

    private byte[] communication;
    private boolean[] marked;

    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        units = new short[height * width];
        buildings = new short[height * width];
        communication = new byte[height * width];
        marked = new boolean[height * width];
    }

    private Board(int width, int height, short[] units, short[] buildings, byte[] communications) {
        this.height = height;
        this.width = width;
        this.units = units;
        this.buildings = buildings;
        communication = communications;
        marked = new boolean[height * width];
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public boolean isInCommunication(EPlayer player, int x, int y) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        return (communication[getOffset(x, y)] & 1 << player.ordinal()) > 0;
    }

    public void setInCommunication(EPlayer player, int x, int y, boolean enable) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        if (enable) communication[getOffset(x, y)] |= 1 << player.ordinal();
        else communication[getOffset(x, y)] &= ~(1 << player.ordinal());
    }

    public void clearCommunication() {
        Arrays.fill(communication, (byte) 0);
    }

    public boolean isBuilding(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        return buildings[getOffset(x, y)] != 0;
    }

    //GS do this
    public void setBuilding(EBuildingData building, EPlayer player, int x, int y) {
        buildings[getOffset(x, y)] = setItemPlayer(player, setItemType(building, (short) 0));
    }

    public EBuildingData getBuildingType(int x, int y) {
        if(!isBuilding(x,y)) throw new IllegalBoardCallException("Not a building.");
        return getBuildingType(buildings[getOffset(x, y)]);
    }

    public EPlayer getBuildingPlayer(int x, int y) {
        if(!isBuilding(x,y)) throw new IllegalBoardCallException("Not a building.");
        return getPlayer(buildings[getOffset(x, y)]);
    }

    public boolean isUnit(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        return units[getOffset(x, y)] != 0;
    }

    //GS do this
    public void setUnit(EUnitData unit, EPlayer player, int x, int y) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        units[getOffset(x, y)] = setItemPlayer(player, setItemType(unit, (short) 0));
    }

    public void delUnit(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        units[getOffset(x, y)] = 0;
    }

    public void delBuilding(int x, int y){
        if(!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        buildings[getOffset(x, y)] = 0;
    }

    public EUnitData getUnitType(int x, int y) {
        if(!isUnit(x,y))
            throw new IllegalBoardCallException("Not a unit.");
        return getUnitType(units[getOffset(x, y)]);
    }

    public EPlayer getUnitPlayer(int x, int y) {
        if(!isUnit(x,y)) throw new IllegalBoardCallException("Not a unit.");
        return getPlayer(units[getOffset(x, y)]);
    }

    public Board clone() {
        return new Board(this.width, this.height, units.clone(), buildings.clone(), communication.clone());
    }

    public boolean isMarked(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        return marked[getOffset(x, y)];
    }

    public void setMarked(int x, int y, boolean mark) {
        if(!isValidCoordinate(x,y)) throw new IllegalBoardCallException("Invalid coordinates");
        marked[getOffset(x, y)] = mark;
    }

    public void clearMarked() {
        Arrays.fill(marked,false);
    }


    public void moveUnit(int xs, int ys, int xd, int yd){
        if(!isUnit(xs,ys) || isUnit(xd, yd)) throw new IllegalBoardCallException("Not a unit.");
        units[getOffset(xd, yd)] = units[getOffset(xs, ys)];
        units[getOffset(xs, ys)] = 0;
    }

    /**
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @return Return the distance in max between two coords
     */
    public int getDistance(int x, int y, int x2, int y2) {
        if (!isValidCoordinate(x, y) || !isValidCoordinate(x2, y2)) {
            return -1;
        }
        int diffX = Math.abs(x - x2);
        int diffY = Math.abs(y - y2);
        return Math.max(diffX, diffY);
    }

    private String cellToString(int x, int y){
        String res = "";
        EUnitData u;
        EBuildingData b;
        EPlayer pU = null;
        EPlayer pB = null;
        try {
            u = this.getUnitType(x, y);
            pU = this.getUnitPlayer(x, y);
            res += u + " ; " + pU + "\n";
        } catch (IllegalBoardCallException e){
            u = null;
        }
        try {
            pB = this.getBuildingPlayer(x, y);
            b = this.getBuildingType(x, y);
            res += b + " ; " + pB + "\n";
        } catch (IllegalBoardCallException e){
            b = null;
        }
        if(res.isEmpty())
            return res;

        return res + "Com1 : " + isInCommunication(EPlayer.PLAYER_NORTH, x, y)
                + "\nCom2: " + isInCommunication(EPlayer.PLAYER_SOUTH, x, y) + "\n";
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Width = " + width + " ; Height = " + height + "\n");
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                String str = cellToString(x, y);
                if(!str.isEmpty()) {
                    result.append("(").append(x).append(";").append(y).append(") -> ");
                    result.append(cellToString(x, y)).append("\n");
                }
            }
        }
        return result.toString();
    }


    /*
     * Private atomic methods to abstract the bit manipulation.
     */

    private int getOffset(int x, int y) {
        return y * width + x;
    }

    private EUnitData getUnitType(short unit) {
        return EUnitData.values()[((unit) >> 8)-1];
    }

    private EBuildingData getBuildingType(short building) {
        return EBuildingData.values()[(building >> 8)-1];
    }

    private short setItemType(Enum type, short item) {
        return (short) ((item & MASK_TYPE) | ((type.ordinal()+1) << 8));
    }

    private EPlayer getPlayer(short item) {
        return EPlayer.values()[(item & MASK_TYPE)];
    }

    private short setItemPlayer(EPlayer player, short item) {
        return (short) ((item & MASK_PLAYER) | player.ordinal());
    }
}
