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
    public void addUnitTest(){
        //Test if adding an information to a cell is really added
        Unit soldier = new Unit(EUnitData.INFANTRY, PLAYER1);
        master.addUnit(soldier.getUnitData(), soldier.getPlayer(), x, y);

        Unit result = master.getBoard().getUnit(x, y);

        String src = "result : " + soldier.getUnitData() + " : joueur" + soldier.getPlayer() + "\n";
        String res = "result : " + result.getUnitData() + " : joueur" + result.getPlayer() + "\n";

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

        Building b1 = board1.getBuilding(x, y);
        Building b2 = board2.getBuilding(x, y);

        assertTrue(b1 != b2);
        assertTrue(b1.getBuildingData() == b2.getBuildingData());
        assertTrue(b1.getPlayer() == b2.getPlayer());

        Unit u1 = board1.getUnit(x, y);
        Unit u2 = board2.getUnit(x, y);

        assertTrue(u1 != u2);
        assertTrue(u1.getUnitData() == u2.getUnitData());
        assertTrue(u1.getPlayer() == u2.getPlayer());
    }

    @Test
    public void moveBuilding(){
        Building building = new Building(EBuildingData.ARSENAL, PLAYER1);
        master.addBuilding(building.getBuildingData(), building.getPlayer(), x, y);
        master.moveBuilding(x, y, x2, y2);

        Building building2 = master.getBoard().getBuilding(x2, y2);

        assertTrue(building.getBuildingData() == building2.getBuildingData());
        assertTrue(building.getPlayer() == building2.getPlayer());
        //TODO: NEED TO DO NEW TESTS ( REMOVE BOOLEAN FROM SOME METHODS IN BOARD )
        /*
        assertTrue(master.moveBuilding(x2, y2, x2, y2));
        assertFalse(master.moveBuilding(x2, y2, x2 + width, y2 + height));
        assertFalse(master.moveBuilding(x2, y2, x2 - width, y2 - height));
        */
    }

    @Test
    public void move(){
        Unit soldier = new Unit(EUnitData.INFANTRY, PLAYER1);
        master.addUnit(soldier.getUnitData(), soldier.getPlayer(), x, y);
        master.moveUnit(x, y, x2, y2);

        Unit soldier2 = master.getBoard().getUnit(x2, y2);

        assertTrue(soldier.getUnitData() == soldier2.getUnitData());
        assertTrue(soldier.getPlayer() == soldier2.getPlayer());

        /*
        assertTrue(master.moveUnit(x2, y2, x2, y2));
        assertFalse(master.moveUnit(x2, y2, x2 + width, y2 + height));
        assertFalse(master.moveUnit(x2, y2, x2 - width, y2 - height));
        */
    }

    @Test
    public void revert(){
        //Add a unit infantery at a random location on the board
        //  and move it to a random position.
        master.addUnit(EUnitData.INFANTRY, PLAYER1, x, y);
        Unit soldier1 = master.getBoard().getUnit(x, y);

        master.moveUnit(x, y, x2, y2);

        //Test if after a modification on the board, the history has stacked a board
        assertTrue(!master.isEmptyHistory());

        //Now revert the modification
        assertTrue(master.revert());

        //Test if after a move, the revert method add a non-null Board in its attribute
        assertTrue(master.getBoard() != null);

        //(soldier1)Get info from the initial state of the board about the unit we put
        //(soldier2)Same but with the actual state of the board
        Unit soldier2 = master.getBoard().getUnit(x, y);

        //Test if we still have the same information we put.
        assertTrue(soldier1.getUnitData() == soldier2.getUnitData()
                && soldier1.getPlayer() == soldier2.getPlayer()
        );
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
        System.out.println("(" + x + ";" + y + ")");
        System.out.println("(" + x2 + ";" + y2 + ")");

        int distance = master.getBoard().getDistance(x, y, x2, y2);
        System.out.println(distance + "\n");
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

    @After
    public void tearDown() throws Exception {
    }
}