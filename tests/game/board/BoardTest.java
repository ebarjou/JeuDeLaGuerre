package game.board;

import org.junit.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Random;

import static org.junit.Assert.*;
import static game.EPlayer.*;

public class BoardTest {

    private IBoardManager master;
    private int width = 25;
    private int height = 20;
    private int x, y, x2, y2;

    @Before
    public void setUp() throws Exception {
        master = new BoardManager();
        master.initBoard(width, height);

        Random r = new Random();

        x = r.nextInt(width);
        y = r.nextInt(height);

        x2 = r.nextInt(width);
        y2 = r.nextInt(height);
    }

    @Test
    public void dimension(){
        assertTrue(master.getBoard().getWidth() == width && master.getBoard().getHeight() == height);
    }

    @Test
    public void addUnit(){
        Unit soldier = new Unit(EUnitData.INFANTRY, PLAYER1);
        master.addUnit(soldier.getUnitData(), soldier.getPlayer(), x, y);

        Unit result = master.getBoard().getUnit(x, y);

        String src = "result : " + soldier.getUnitData() + " : player " + soldier.getPlayer() + "\n";
        String res = "result : " + result.getUnitData() + " : player " + result.getPlayer() + "\n";

        assertTrue(src.equalsIgnoreCase(res));
    }

    @Test
    public void cloneTest(){
        //Here we test if we clone the board, we clone its attributes too. So we don't have twice
        //  the same reference.

        master.addUnit(EUnitData.INFANTRY, PLAYER1, x, y);
        master.addBuilding(EBuildingData.ARSENAL, PLAYER1, x, y);

        Board board1 = master.getBoard();
        Board board2 = board1.clone();

        assertTrue(board1 != board2);

        Building building1 = board1.getBuilding(x, y);
        Building building2 = board2.getBuilding(x, y);

        assertTrue(building1 != building2);
        assertTrue(building1.getBuildingData() == building2.getBuildingData());
        assertTrue(building1.getPlayer() == building2.getPlayer());

        Unit unit1 = board1.getUnit(x, y);
        Unit unit2 = board2.getUnit(x, y);

        assertTrue(unit1 != unit2);
        assertTrue(unit1.getUnitData() == unit2.getUnitData());
        assertTrue(unit1.getPlayer() == unit2.getPlayer());
    }

    @Test
    public void moveBuilding(){
        Building building = new Building(EBuildingData.ARSENAL, PLAYER1);
        master.addBuilding(building.getBuildingData(), building.getPlayer(), x, y);
        master.moveBuilding(x, y, x2, y2);
        Building building2 = master.getBoard().getBuilding(x2, y2);
        //
        assertTrue(building.getBuildingData() == building2.getBuildingData());
        assertTrue(building.getPlayer() == building2.getPlayer());

        String initialBoardState = master.getBoard().toString();
        //IMPOSSIBLE MOVE, SHOULD DO NOTHING
        master.moveBuilding(x2, y2, x2, y2);
        master.moveBuilding(x2, y2, x2 + width, y2 + height);
        master.moveBuilding(x2, y2, x2 - width, y2 - height);

        assertTrue(initialBoardState.equals(master.getBoard().toString()));

    }

    @Test
    public void move(){
        Unit soldier = new Unit(EUnitData.INFANTRY, PLAYER1);
        master.addUnit(soldier.getUnitData(), soldier.getPlayer(), x, y);
        master.moveUnit(x, y, x2, y2);

        Unit soldier2 = master.getBoard().getUnit(x2, y2);

        assertTrue(soldier.getUnitData() == soldier2.getUnitData());
        assertTrue(soldier.getPlayer() == soldier2.getPlayer());

        String initialBoardState = master.getBoard().toString();
        //IMPOSSIBLE MOVE, SHOULD DO NOTHING
        master.moveUnit(x2, y2, x2, y2);
        master.moveUnit(x2, y2, x2 + width, y2 + height);
        master.moveUnit(x2, y2, x2 - width, y2 - height);

        assertTrue(initialBoardState.equals(master.getBoard().toString()));
    }

    @Test
    public void revert(){
        //Add a unit infantery at a random location on the board
        //  and move it to a random position.
        master.addUnit(EUnitData.INFANTRY, PLAYER1, x, y);
        Unit soldier1 = master.getBoard().getUnit(x, y);
        String actualBoardState = master.getBoard().toString();
        master.moveUnit(x, y, x2, y2);

        //Test if after a modification on the board, the history has stacked a board
        assertTrue(!master.isEmptyHistory());

        //Now revert the modification
        master.revert();

        assertTrue(master.getBoard() != null);
        assertTrue(actualBoardState.equals(master.getBoard().toString()));
    }

    @Test
    public void displayBoard(){
        master.addUnit(EUnitData.INFANTRY, PLAYER1, x, y);
        master.addBuilding(EBuildingData.ARSENAL, PLAYER1, x, y);
        master.setCommunication(PLAYER1, x, y, true);

        master.addUnit(EUnitData.CAVALRY, PLAYER2, x2, y2);
        master.addBuilding(EBuildingData.FORTRESS, PLAYER2, x2, y2);

        Board board = master.getBoard();
        System.out.println(board.toString());
    }

    @Test
    public void distance(){
        int distance = master.getBoard().getDistance(x, y, x2, y2);
        assertTrue(distance >= 0);
    }

    @Test
    public void clearCommunication(){
        Random r = new Random();
        for(int i = 0; i < 100; i++)
            master.setCommunication(PLAYER1, r.nextInt(width), r.nextInt(height), true);

        master.clearCommunication();
        boolean noCommunication = true;
        for(int i = 0; i < master.getBoard().getWidth(); i++){
            for(int j = 0; j < master.getBoard().getHeight(); j++){
                noCommunication = noCommunication && !master.getBoard().getCommunication(PLAYER1, i, j);
                noCommunication = noCommunication && !master.getBoard().getCommunication(PLAYER2, i, j);
            }
        }
        assertTrue(noCommunication);
    }
}