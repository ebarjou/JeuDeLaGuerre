package game.board;

import game.EPlayer;
import game.board.exceptions.IllegalBoardCallException;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;

import java.util.Arrays;

public class Board implements IBoard {
    private final static int MASK_TYPE = 0x00FF;
    private final static int MASK_PLAYER = 0xFF00;
    private int height, width;
    private short[] units;
    private short[] buildings;

    private byte[] communication;
    private boolean[] marked;

    /**
     * @param width
     * @param height Create a board of the specified size.
     *               The board contain for each cell an unit and a building with associated player(s),
     *               the communication for each player and a mark boolean.
     */
    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        this.units = new short[height * width];
        this.buildings = new short[height * width];
        this.communication = new byte[height * width];
        this.marked = new boolean[height * width];
    }

    /**
     * @param width
     * @param height
     * @param units, buildings, communications
     *               Create a board of the specified size, that use the given arrays as it's data.
     *               Those arrays must be width*height of length.
     */
    private Board(int width, int height, short[] units, short[] buildings, byte[] communications) {
        this.height = height;
        this.width = width;
        this.units = units;
        this.buildings = buildings;
        this.communication = communications;
        this.marked = new boolean[height * width];
    }

    /**
     * @return the number of column of this board
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the number of row of this board
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param x
     * @param y
     * @return true if those coordinates are within the board, else false
     */
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    /**
     * @param player
     * @param x
     * @param y
     * @return true if the cell [x,y] is in communication for this player
     */
    public boolean isInCommunication(EPlayer player, int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        return (communication[getOffset(x, y)] & 1 << player.ordinal()) > 0;
    }

    /**
     * @param player
     * @param x
     * @param y
     * @param enable : true to set in communication, false to remove the communication
     *               Set the cell [x,y]communication to [enable]
     */
    public void setInCommunication(EPlayer player, int x, int y, boolean enable) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        if (enable) communication[getOffset(x, y)] |= 1 << player.ordinal();
        else communication[getOffset(x, y)] &= ~(1 << player.ordinal());
    }

    /**
     * Set all communication to false for all players
     */
    public void clearCommunication() {
        Arrays.fill(communication, (byte) 0);
    }

    public boolean isBuilding(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        return buildings[getOffset(x, y)] != 0;
    }

    /**
     * @param building
     * @param player
     * @param x
     * @param y        Set the building on the cell [x,y] for the player
     */
    public void setBuilding(EBuildingProperty building, EPlayer player, int x, int y) {
        buildings[getOffset(x, y)] = setItemPlayer(player, setItemType(building, (short) 0));
    }

    /**
     * @param x
     * @param y
     * @return the EBuildingProperty of the cell [x,y].
     * @throws IllegalBoardCallException if there is no building on the cell
     */
    public EBuildingProperty getBuildingType(int x, int y) {
        if (!isBuilding(x, y)) throw new IllegalBoardCallException("Not a building.");
        return getBuildingType(buildings[getOffset(x, y)]);
    }

    /**
     * @param x
     * @param y
     * @return the EPlayer of the building on the cell [x,y].
     * @throws IllegalBoardCallException if there is no building on the cell
     */
    public EPlayer getBuildingPlayer(int x, int y) {
        if (!isBuilding(x, y)) throw new IllegalBoardCallException("Not a building.");
        return getPlayer(buildings[getOffset(x, y)]);
    }

    /**
     * @param x
     * @param y
     * @return true is there is an unit on the cell [x,y]
     */
    public boolean isUnit(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        return units[getOffset(x, y)] != 0;
    }

    /**
     * @param unit
     * @param player
     * @param x
     * @param y      Set the unit on the cell [x,y] for the player
     */
    public void setUnit(EUnitProperty unit, EPlayer player, int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        units[getOffset(x, y)] = setItemPlayer(player, setItemType(unit, (short) 0));
    }

    /**
     * @param x
     * @param y Remove the unit on cell [x,y]
     */
    public void delUnit(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        units[getOffset(x, y)] = 0;
    }

    /**
     * @param x
     * @param y Remove the building on cell [x,y]
     */
    public void delBuilding(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        buildings[getOffset(x, y)] = 0;
    }

    /**
     * @param x
     * @param y
     * @return the EUnitProperty of the cell [x,y]
     */
    public EUnitProperty getUnitType(int x, int y) {
        if (!isUnit(x, y))
            throw new IllegalBoardCallException("Not a unit.");
        return getUnitType(units[getOffset(x, y)]);
    }

    /**
     * @param x
     * @param y
     * @return the EPlayer of the unit on the cell [x,y].
     */
    public EPlayer getUnitPlayer(int x, int y) {
        if (!isUnit(x, y)) throw new IllegalBoardCallException("Not a unit.");
        return getPlayer(units[getOffset(x, y)]);
    }

    public Board clone() {
        return new Board(this.width, this.height, units.clone(), buildings.clone(), communication.clone());
    }

    public boolean isMarked(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        return marked[getOffset(x, y)];
    }

    public void setMarked(int x, int y, boolean mark) {
        if (!isValidCoordinate(x, y)) throw new IllegalBoardCallException("Invalid coordinates");
        marked[getOffset(x, y)] = mark;
    }

    /**
     * Remove all mark on the board
     */
    public void clearMarked() {
        Arrays.fill(marked, false);
    }

    public void moveUnit(int xs, int ys, int xd, int yd) {
        if (!isUnit(xs, ys) || isUnit(xd, yd)) throw new IllegalBoardCallException("Not a unit.");
        units[getOffset(xd, yd)] = units[getOffset(xs, ys)];
        units[getOffset(xs, ys)] = 0;
    }

    /**
     * Return the max distance between y,y2 and x,x2
     *
     * @param x,y   coordinates of the first point
     * @param x2,y2 coordinates of the second point
     * @return the max distance between (y-y2) and (x-x2)
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

    private EUnitProperty getUnitType(short unit) {
        return EUnitProperty.values()[((unit) >> 8) - 1];
    }

    private EBuildingProperty getBuildingType(short building) {
        return EBuildingProperty.values()[(building >> 8) - 1];
    }

    private short setItemType(Enum type, short item) {
        return (short) ((item & MASK_TYPE) | ((type.ordinal() + 1) << 8));
    }

    private EPlayer getPlayer(short item) {
        return EPlayer.values()[(item & MASK_TYPE)];
    }

    private short setItemPlayer(EPlayer player, short item) {
        return (short) ((item & MASK_PLAYER) | player.ordinal());
    }

    /* toString methods */

    private String cellToString(int x, int y) {
        String result = "";
        EUnitProperty unitProperty;
        EBuildingProperty buildingProperty;
        EPlayer playerUnit, playerBuilding;
        try {
            unitProperty = this.getUnitType(x, y);
            playerUnit = this.getUnitPlayer(x, y);
            result += unitProperty + " ; " + playerUnit + "\n";
        } catch (IllegalBoardCallException ignored) {
        }

        try {
            playerBuilding = this.getBuildingPlayer(x, y);
            buildingProperty = this.getBuildingType(x, y);
            result += buildingProperty + " ; " + playerBuilding + "\n";
        } catch (IllegalBoardCallException ignored) {
        }

        if (result.isEmpty())
            return result;
        return result + "Com1 : " + isInCommunication(EPlayer.PLAYER_NORTH, x, y)
                + "\nCom2: " + isInCommunication(EPlayer.PLAYER_SOUTH, x, y) + "\n";
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Width = ").append(width).append(" ; Height = ").append(height).append("\n");
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                String str = cellToString(x, y);
                if (!str.isEmpty()) {
                    result.append("(").append(x).append(";").append(y).append(") -> ");
                    result.append(cellToString(x, y)).append("\n");
                }
            }
        }

        return result.toString();
    }
}
