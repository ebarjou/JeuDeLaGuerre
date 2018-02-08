package game.board;

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
        if(actualBoard.move(x, y, x2, y2, EInfoType.UNIT)){
            history.push(tmp);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveBuilding(int x, int y, int x2, int y2) {
        Board tmp = actualBoard.clone();
        if(actualBoard.move(x, y, x2, y2, EInfoType.BUILDING)){
            history.push(tmp);
            return true;
        }
        return false;
    }

    @Override
    public Object getValueInfo(EInfoType key, int x, int y) {
        return actualBoard.getInfo(key, x, y).getValue();
    }

    @Override
    public boolean addUnit(EUnit unitType, int player, int x, int y) {
        if(actualBoard.getInfo(EInfoType.UNIT, x, y) != null){
            System.out.println("Error: already a unit at this location");
            return false;
        }

        UnitInfo unitInfo = new UnitInfo(unitType, player);
        Info newUnit = new Info(unitInfo);
        actualBoard.addInfo(EInfoType.UNIT, newUnit, x, y);
        return true;
    }

    @Override
    public boolean addBuilding(EBuilding buildingType, int player, int x, int y) {
        if(actualBoard.getInfo(EInfoType.BUILDING, x, y) != null){
            System.out.println("Error: already a building at this location");
            return false;
        }
        BuildingInfo unitInfo = new BuildingInfo(buildingType, player);
        Info newBuilding = new Info(unitInfo);
        actualBoard.addInfo(EInfoType.BUILDING, newBuilding, x, y);
        return true;
    }

    @Override
    public void setCommunication(int player, int x, int y) {
        EInfoType communication;
        if(player == 1)
            communication = EInfoType.COMMUNICATION1;
        else
            communication = EInfoType.COMMUNICATION2;

        actualBoard.addInfo(communication, new Info(true), x, y);
    }

    @Override
    public void deleteCommunication(int player, int x, int y) {
        EInfoType communication;
        if(player == 1)
            communication = EInfoType.COMMUNICATION1;
        else
            communication = EInfoType.COMMUNICATION2;
        actualBoard.addInfo(communication, new Info(false), x, y);
    }

    @Override
    public void addInfo(EInfoType key, Info info, int x, int y) {
        actualBoard.addInfo(key, info, x, y);
    }

    @Override
    public void clearCommunication() {
        actualBoard.clearCommunication();
    }
}
