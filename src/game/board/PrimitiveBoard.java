package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Arrays;

public class PrimitiveBoard implements IBoard{
    private int height, width;
    private short[] units;
    private short[] buildings;

    private byte[] communication;
    private boolean[] marked;

    public PrimitiveBoard(int width, int height) {
        this.height = height;
        this.width = width;
        units = new short[height * width];
        buildings = new short[height * width];
        communication = new byte[height * width];
        marked = new boolean[height * width];
    }

    private PrimitiveBoard(int width, int height, short[] units, short[] buildings) {
        this.height = height;
        this.width = width;
        this.units = units;
        this.buildings = buildings;
        communication = new byte[height * width];
        marked = new boolean[height * width];
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    public boolean isInCommunication(EPlayer player, int x, int y) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        return (communication[getOffset(x, y)] & 1 << player.ordinal()) == 1;
    }

    public void setInCommunication(EPlayer player, int x, int y, boolean enable) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        if (enable) communication[getOffset(x, y)] |= 1 << player.ordinal();
        else communication[getOffset(x, y)] &= ~(1 << player.ordinal());
    }

    public void clearCommunication() {
        Arrays.fill(communication, (byte) 0);
    }

    public boolean isBuilding(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        return buildings[getOffset(x, y)] != 0;
    }

    public void setBuilding(EBuildingData building, EPlayer player, int x, int y) {
        buildings[getOffset(x, y)] = setItemPlayer(player, setItemType(building, (short) 0));
    }

    public EBuildingData getBuildingType(int x, int y) {
        if(!isBuilding(x,y)) throw new NullPointerException();
        return getBuildingType(buildings[getOffset(x, y)]);
    }

    public EPlayer getBuildingPlayer(int x, int y) {
        if(!isBuilding(x,y)) throw new NullPointerException();
        return getPlayer(buildings[getOffset(x, y)]);
    }

    public boolean isUnit(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        return units[getOffset(x, y)] != 0;
    }

    public void setUnit(EUnitData unit, EPlayer player, int x, int y) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        units[getOffset(x, y)] = setItemPlayer(player, setItemType(unit, (short) 0));
    }

    public EUnitData getUnitType(int x, int y) {
        if(!isUnit(x,y)) throw new NullPointerException();
        return getUnitType(units[getOffset(x, y)]);
    }

    public EPlayer getUnitPlayer(int x, int y) {
        if(!isUnit(x,y)) throw new NullPointerException();
        return getPlayer(units[getOffset(x, y)]);
    }

    protected PrimitiveBoard clone() {
        return new PrimitiveBoard(this.width, this.height, units.clone(), buildings.clone());
    }

    public boolean isMarked(int x, int y) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        return marked[getOffset(x, y)];
    }

    public void setMarked(int x, int y, boolean mark) {
        if(!isValidCoordinate(x,y)) throw new NullPointerException();
        marked[getOffset(x, y)] = mark;
    }

    public void clearMarked() {
        Arrays.fill(marked,false);
    }


    public void moveUnit(int xs, int ys, int xd, int yd){
        if(!isUnit(xs,ys) || isUnit(xd, yd)) throw new NullPointerException();
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
        return (short) ((item & 0x00FF) | ((type.ordinal()+1) << 8));
    }

    private EPlayer getPlayer(short item) {
        return EPlayer.values()[(item & 0x00FF)];
    }

    private short setItemPlayer(EPlayer player, short item) {
        return (short) ((item & 0xFF00) | player.ordinal());
    }
}
