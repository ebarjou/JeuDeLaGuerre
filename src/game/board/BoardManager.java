package game.board;

import game.EPlayer;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Stack;

public class BoardManager implements IBoardManager {
    private Board actualBoard;
    private Stack<Board> history;

    public BoardManager() {
        history = new Stack<>();
    }

    /**
     * If one of the parameters is negative, assert.
     *
     * @param width  Width of the game.board in cell
     * @param height Height of the game.board in cell
     */
    @Override
    public void initBoard(int width, int height) {
        actualBoard = new Board(width, height);
    }

    @Override
    public Board getBoard() {
        // TODO:Assert ou return null ?
        return actualBoard;
    }

    @Override
    public boolean revert() {
        if (history.empty())
            return false;
        actualBoard = history.pop().clone();
        return true;
    }

    @Override
    public void clearHistory() {
        history = new Stack<>();
    }

    @Override
    public boolean isEmptyHistory() {
        return history.empty();
    }

    @Override
    public void moveUnit(int x, int y, int x2, int y2) {
        Board tmp = actualBoard.clone();
        if (actualBoard.moveUnit(x, y, x2, y2))
            history.push(tmp);
    }

    @Override
    public void moveBuilding(int x, int y, int x2, int y2) {
        Board tmp = actualBoard.clone();
        if (actualBoard.moveBuilding(x, y, x2, y2))
            history.push(tmp);
    }

    @Override
    public void addUnit(EUnitData unitType, EPlayer player, int x, int y) {
        if (actualBoard.getUnit(x, y) == null) {
            Unit unit = new Unit(unitType, player);
            actualBoard.setUnit(unit, x, y);
        }
    }

    @Override
    public void removeUnit(int x, int y) {
        if (actualBoard.getUnit(x, y) == null) {
            history.push(actualBoard.clone());
            actualBoard.setUnit(null, x, y);
        }
    }

    @Override
    public void removeBuilding(int x, int y) {
        if (actualBoard.getBuilding(x, y) == null) {
            history.push(actualBoard.clone());
            actualBoard.setBuilding(null, x, y);
        }
    }

    @Override
    public void addBuilding(EBuildingData buildingType, EPlayer player, int x, int y) {
        if (actualBoard.getBuilding(x, y) == null) {
            Building buildingInfo = new Building(buildingType, player);
            actualBoard.setBuilding(buildingInfo, x, y);
        }
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
