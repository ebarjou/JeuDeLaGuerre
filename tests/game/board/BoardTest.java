package game.board;

import org.junit.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.Random;

import static org.junit.Assert.*;
import static game.EPlayer.*;

public class BoardTest {

    private IBoardManager master;
    private int w = 25;
    private int h = 20;
    private int x, y, x2, y2;

    @Before
    public void setUp() throws Exception {
        master = BoardManager.getInstance();
        master.initBoard(w, h);

        Random r = new Random();

        x = r.nextInt(w);
        y = r.nextInt(h);

        x2 = r.nextInt(w);
        y2 = r.nextInt(h);
    }

    @Test
    public void singleton(){
        assertTrue(master == BoardManager.getInstance());
    }

    @Test
    public void dimension(){
        assertTrue(master.getBoard().getWidth() == w && master.getBoard().getHeight() == h);
    }

    @Test
    public void addUnitTest(){
        //Test if adding an information to a cell is really added
        Unit soldier = new Unit(EUnitData.INFANTRY, PLAYER1);
        master.addUnit(soldier.getUnit(), soldier.getPlayer(), x, y);

        Unit result = master.getBoard().getUnit(x, y);

        String src = "result : " + soldier.getUnit() + " : joueur" + soldier.getPlayer() + "\n";
        String res = "result : " + result.getUnit() + " : joueur" + result.getPlayer() + "\n";

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
        assertTrue(b1.getBuilding() == b2.getBuilding());
        assertTrue(b1.getPlayer() == b2.getPlayer());

        Unit u1 = board1.getUnit(x, y);
        Unit u2 = board2.getUnit(x, y);

        assertTrue(u1 != u2);
        assertTrue(u1.getUnit() == u2.getUnit());
        assertTrue(u1.getPlayer() == u2.getPlayer());
    }


    @Test
    public void move(){
        Unit soldier = new Unit(EUnitData.INFANTRY, PLAYER1);
        master.addUnit(soldier.getUnit(), soldier.getPlayer(), x, y);
        master.moveUnit(x, y, x2, y2);

        Unit soldier2 = master.getBoard().getUnit(x2, y2);

        assertTrue(soldier.getUnit() == soldier2.getUnit());
        assertTrue(soldier.getPlayer() == soldier2.getPlayer());

        assertTrue(master.moveUnit(x2, y2, x2, y2));
        assertFalse(master.moveUnit(x2, y2, x2 + w, y2 + h));
        assertFalse(master.moveUnit(x2, y2, x2 - w, y2 - h));
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
        assertTrue(soldier1.getUnit() == soldier2.getUnit()
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

    @After
    public void tearDown() throws Exception {
    }
}