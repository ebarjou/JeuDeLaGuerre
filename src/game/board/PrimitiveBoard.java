package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Arrays;

public class PrimitiveBoard {
    private int height, width;
    private short[] units;
    private short[] buildings;

    private byte[] communication;

    public PrimitiveBoard(int width, int height) {
        this.height = height;
        this.width = width;
        units = new short[height * width];
        buildings = new short[height * width];
        communication = new byte[height * width];
    }

    private PrimitiveBoard(int width, int height, short[] units, short[] buildings) {
        this.height = height;
        this.width = width;
        this.units = units;
        this.buildings = buildings;
        communication = new byte[height * width];
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < height && y < width;
    }

    public boolean getCommunication(EPlayer player, int x, int y) {
        return (communication[getOffset(x, y)] & 1 << player.ordinal()) == 1;
    }

    public void setCommunication(EPlayer player, int x, int y, boolean enable) {
        if (enable) communication[getOffset(x, y)] |= 1 << player.ordinal();
        else communication[getOffset(x, y)] &= 0 << player.ordinal();
    }

    public void clearCommunication() {
        Arrays.fill(communication, (byte) 0);
    }

    public boolean isBuilding(int x, int y) {
        return buildings[getOffset(x, y)] != 0;
    }

    public void setBuilding(EBuildingData building, EPlayer player, int x, int y) {
        buildings[getOffset(x, y)] = setItemPlayer(player, setItemType(building, (short) 0));
    }

    public EBuildingData getBuildingType(int x, int y) {
        return getBuildingType(buildings[getOffset(x, y)]);
    }

    public EPlayer getBuildingPlayer(int x, int y) {
        return getPlayer(buildings[getOffset(x, y)]);
    }

    public boolean isUnit(int x, int y) {
        return units[getOffset(x, y)] != 0;
    }

    public void setUnit(EUnitData unit, EPlayer player, int x, int y) {
        units[getOffset(x, y)] = setItemPlayer(player, setItemType(unit, (short) 0));
    }

    public EUnitData getUnitType(int x, int y) {
        return getUnitType(units[getOffset(x, y)]);
    }

    public EPlayer getUnitPlayer(int x, int y) {
        return getPlayer(units[getOffset(x, y)]);
    }

    protected PrimitiveBoard clone() {
        return new PrimitiveBoard(this.width, this.height, units.clone(), buildings.clone());
    }

    /*
     * Private atomic methods to abstract the bit manipulation.
     */

    private int getOffset(int x, int y) {
        return y * width + x;
    }

    private EUnitData getUnitType(short unit) {
        return EUnitData.values()[(unit >> 8)];
    }

    private EBuildingData getBuildingType(short building) {
        return EBuildingData.values()[building >> 8];
    }

    private short setItemType(Enum unit, short item) {
        return (short) ((item & 0x00FF) | (unit.ordinal() << 8));
    }

    private EPlayer getPlayer(short item) {
        return EPlayer.values()[(item & 0x00FF)];
    }

    private short setItemPlayer(EPlayer player, short item) {
        return (short) ((item & 0xFF00) | player.ordinal());
    }
}
