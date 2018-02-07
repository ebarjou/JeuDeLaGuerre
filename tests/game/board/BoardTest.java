package game.board;

import org.junit.*;
import static org.junit.Assert.*;

public class BoardTest {

    private IBoardMaster master;
    private int w = 25;
    private int h = 20;

    @Before
    public void setUp() throws Exception {
        master = BoardMaster.getInstance();
        master.init(w, h);
        //System.out.println(master.getBoard().getInfo(EInfoType.UNIT, 1, 1).toString());
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
        UnitInfo ui = new UnitInfo(EUnit.INFANTERY, 1);
        Info info = new Info(ui);
        master.addInfo(EInfoType.UNIT, info, 1, 1);

        UnitInfo result = (UnitInfo)(master.getValueInfo(EInfoType.UNIT, 1, 1));

        String src = "result : " + ui.getId() + " : joueur" + ui.getPlayer() + "\n";
        String res = "result : " + result.getId() + " : joueur" + result.getPlayer() + "\n";

        assertTrue(src.equalsIgnoreCase(res));
    }

    @Test
    public void addInfo(){

    }

    @After
    public void tearDown() throws Exception {
    }
}