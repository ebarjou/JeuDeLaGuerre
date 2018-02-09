package game.board;

import game.EPlayer;

import java.util.Stack;

public class BoardMaster implements IBoardMaster{
    private Board actualBoard;
    private static BoardMaster boardMaster;
    private Stack<Board> history;

    private BoardMaster(){
        history = new Stack<>();
    }

    public static IBoardMaster getInstance(){
        if(boardMaster == null){
            boardMaster = new BoardMaster();
        }
        return boardMaster;
    }

    /**
     * If one of the parameters is negative, assert.
     * @param width Width of the game.board in cell
     * @param height Height of the game.board in cell
     */
    @Override
    public void initBoard(int width, int height){
        actualBoard = new Board(width, height);
    }

    @Override
    public Board getBoard(){
        return actualBoard;
    }

    @Override
    public boolean revert(){
        if(history.empty())
            return false;
        actualBoard = history.pop().clone();
        return true;
    }

    @Override
    public void clearHistory() {
        while(!history.empty())
            history.pop();
    }

    @Override
    public boolean isEmptyHistory() {
        return history.empty();
    }

    @Override
    public boolean moveUnit(int x, int y, int x2, int y2) {
        Board tmp = actualBoard.clone();
        if(actualBoard.moveUnit(x, y, x2, y2)){
            history.push(tmp);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveBuilding(int x, int y, int x2, int y2) {
        Board tmp = actualBoard.clone();
        if(actualBoard.moveBuilding(x, y, x2, y2)){
            history.push(tmp);
            return true;
        }
        return false;
    }

    @Override
    public boolean addUnit(EUnit unitType, EPlayer player, int x, int y) {
        if(actualBoard.getUnit(x, y) != null){
            System.out.println("Error: already a unit at this location");
            return false;
        }

        UnitInfo unitInfo = new UnitInfo(unitType, player);
        actualBoard.setUnit(unitInfo, x, y);
        return true;
    }

    @Override
    public boolean removeUnit(int x, int y){
        if(actualBoard.getUnit(x, y) != null) {
            history.push(actualBoard.clone());
            actualBoard.setUnit(null, x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeBuilding(int x, int y) {
        if(actualBoard.getBuilding(x, y) != null){
            history.push(actualBoard.clone());
            actualBoard.setBuilding(null, x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean addBuilding(EBuilding buildingType, EPlayer player, int x, int y) {
        if(actualBoard.getBuilding(x, y) != null){
            System.out.println("Error: already a unit at this location");
            return false;
        }

        BuildingInfo buildingInfo = new BuildingInfo(buildingType, player);
        actualBoard.setBuilding(buildingInfo, x, y);
        return true;
    }

    @Override
    public void setCommunication(EPlayer player, int x, int y, boolean value) {
        actualBoard.setCommunication(player, x, y, value);
    }

    @Override
    public void deleteCommunication(EPlayer player, int x, int y) {
        setCommunication(player, x, y, false);
    }

    @Override
    public void clearCommunication() {
        actualBoard.clearCommunication();
    }
}
