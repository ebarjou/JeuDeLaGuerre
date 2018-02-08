package game.board;

import org.junit.*;

import java.util.Random;

import static org.junit.Assert.*;

public class BoardTest {

    private IBoardMaster master;
    private int w = 25;
    private int h = 20;
    private int x, y, x2, y2;

    @Before
    public void setUp() throws Exception {
        master = BoardMaster.getInstance();
        master.initBoard(w, h);

        Random r = new Random();

        x = r.nextInt(w);
        y = r.nextInt(h);

        x2 = r.nextInt(w);
        y2 = r.nextInt(h);
    }

    @Test
    public void singleton(){
        assertTrue(master == BoardMaster.getInstance());
    }

    @Test
    public void dimension(){
        assertTrue(master.getBoard().getWidth() == w && master.getBoard().getHeight() == h);
    }

    @Test
    public void addInfoTest(){
        //Test if adding an information to a cell is really added

        UnitInfo ui = new UnitInfo(EUnit.INFANTERY, 1);
        Info info = new Info(ui);
        master.addInfo(EInfoType.UNIT, info, x, y);

        UnitInfo result = (UnitInfo)(master.getValueInfo(EInfoType.UNIT, x, y));

        String src = "result : " + ui.getId() + " : joueur" + ui.getPlayer() + "\n";
        String res = "result : " + result.getId() + " : joueur" + result.getPlayer() + "\n";

        assertTrue(src.equalsIgnoreCase(res));
    }

    @Test
    public void cloneTest(){
        //Here we test if we clone the board, we clone its attributes too. So we don't have twice
        //  the same reference.

        master.addUnit(EUnit.INFANTERY, 1, x, y);
        Board board1 = master.getBoard();
        Board board2 = board1.clone();

        assertTrue(board1 != board2);

        Info i1 = board1.getInfo(EInfoType.UNIT, x, y);
        Info i2 = board2.getInfo(EInfoType.UNIT, x, y);

        assertTrue(i1 != i2);

        UnitInfo u1 = (UnitInfo)i1.getValue();
        UnitInfo u2 = (UnitInfo)i2.getValue();

        assertTrue(u1.getId() == u2.getId());
        assertTrue(u1.getPlayer() == u2.getPlayer());
    }


    @Test
    public void revert(){
        //Add a unit infantery at a random location on the board
        //  and move it to a random position.
        master.addUnit(EUnit.INFANTERY, 1, x, y);
        master.moveUnit(x, y, x2, y2);

        //Test if after a modification on the board, the history has stacked a board
        assertTrue(!master.isEmptyHistory());

        //Now revert the modification
        master.revert();

        //Test if after a move, the revert method add a non-null Board in its attribute
        assertTrue(master.getBoard() != null);

        //Get info from the initial state of the board about the unit we put
        UnitInfo soldier1 = (UnitInfo)(master.getValueInfo(EInfoType.UNIT, x, y));

        //Same but with the actual state of the board
        UnitInfo soldier2 = (UnitInfo)(master.getValueInfo(EInfoType.UNIT, x, y));

        //Test if we still have the same information we put.
        assertTrue(soldier1.getId() == soldier2.getId()
                && soldier1.getPlayer() == soldier2.getPlayer()
        );
    }

    @Test
    public void move(){

    }

    @After
    public void tearDown() throws Exception {

    }
}