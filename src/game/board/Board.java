package game.board;

import game.EPlayer;
import ruleEngine.GameAction;

import java.util.PriorityQueue;
import java.util.Queue;

public class Board implements Cloneable{
    private int width, height;
    private Cell[][] board;

    Board(int width, int height) {
        assert(width > 0 && height > 0);

        this.width = width;
        this.height = height;

        board = new Cell[width][height];

        for(int x = 0; x < width; ++x){
            for(int y = 0; y < height; ++y){
                board[x][y] = new Cell();
            }
        }
    }

    //return true if coord (x;y) is beyond board's edges

    public boolean edge(int x, int y) throws ArithmeticException{
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    /**
     * Swap the information concerning the unit/building at coord (x;y) with the cell at coord (x2, y2);
     *
     * @param x    coord X source
     * @param y    coord Y source
     * @param x2   coord X destination
     * @param y2   coord Y destination
     * @return true if the move is done, else false
     */
    boolean moveUnit(int x, int y, int x2, int y2) {
        if(edge(x, y) || edge(x2, y2)) {
            return false;
        }

        if(board[x][y].getUnit() == null && board[x2][y2].getUnit() == null) {
            return false;
        }

        UnitInfo tmp = board[x][y].getUnit();
        board[x][y].setUnit(board[x2][y2].getUnit());
        board[x2][y2].setUnit(tmp);
        return true;
    }

    boolean moveBuilding(int x, int y, int x2, int y2) {
        if(edge(x, y) || edge(x2, y2)) {
            return false;
        }

        if(board[x][y].getBuilding() == null && board[x2][y2].getBuilding() == null) {
            return false;
        }

        BuildingInfo tmp = board[x][y].getBuilding();
        board[x][y].setBuilding(board[x2][y2].getBuilding());
        board[x2][y2].setBuilding(tmp);
        return true;
    }

    void clearCommunication(){
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                board[x][y].setCommunication1(false);
                board[x][y].setCommunication2(false);
            }
        }
    }

    void setCommunication(EPlayer player, int x, int y, boolean value){
        if(!edge(x, y)){
            if(player == EPlayer.PLAYER1)
                board[x][y].setCommunication1(value);
            else
                board[x][y].setCommunication2(value);
        }
    }

    Cell getCell(int x, int y){
        if(edge(x, y))
            return null;
        return board[x][y];
    }

    void setUnit(UnitInfo unit, int x, int y){
        if(edge(x, y))
            return;
        board[x][y].setUnit(unit);
    }

    void setBuilding(BuildingInfo building, int x, int y){
        if(edge(x, y))
            return;
        board[x][y].setBuilding(building);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public UnitInfo getUnit(int x, int y){
        if(edge(x, y))
            return null;
        return board[x][y].getUnit();
    }

    public BuildingInfo getBuilding(int x, int y){
        if(edge(x, y))
            return null;
        return board[x][y].getBuilding();
    }

    public int getDistance(int x, int y, int x2, int y2){
        if(edge(x, y) || edge(x2, y2)){
            return -1;
        }
        int diffX = Math.abs(x - x2);
        int diffY = Math.abs(y - y2);
        return Math.max(diffX, diffY);
    }

    public boolean getCommunication(EPlayer player, int x, int y){
        if(edge(x, y))
            return false;
        return board[x][y].getCommunication(player);
    }

    @Override
    public Board clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assert o != null;
        ((Board)o).board = new Cell[width][height];
        for(int x = 0; x < width; ++x){
            for(int y = 0; y < height; ++y){
                ((Board)o).board[x][y] = this.board[x][y].clone();
            }
        }

        return (Board) o;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Width = " + width + " ; Height = " + height + "\n");
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                String str = board[x][y].toString();
                if(!str.isEmpty()) {
                    result.append("(").append(x).append(";").append(y).append(") -> ");
                    result.append(board[x][y].toString()).append("\n");
                }
            }
        }
        return result.toString();
    }

}
