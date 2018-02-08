import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ui.SharedCommand;
import ui.TermUI;
import ui.UserInterface;
import ui.commands.UserToGameCall;

import java.io.ByteArrayInputStream;

public class TermUITest {
    TermUI term;
    ByteArrayInputStream in;
    String cmd;

    @Before
    public void setUp(){
        in = new ByteArrayInputStream(("move a3 b1\nmove E7 j23\nmove E34t f5\nmove hgt A3\nmfd ef\nmove a3\nmove gg5 e6").getBytes());
        System.setIn(in);
        term = new TermUI();
    }

    @Test
    public void process() {
        UserInterface term = new TermUI();
        while(true) {
            SharedCommand cmd = term.getNextCommand();
            System.out.println("CMD -> " + cmd.getCommand());
            if (cmd.getCommand() == UserToGameCall.MOVE) {
                try {
                    int[] a = cmd.getCoords1();
                    int[] b = cmd.getCoords2();
                    System.out.println("From [" + a[0] + "," + a[1] + "] To [" + b[0] + "," + b[1] + "]");
                } catch (Exception e) {
                    Assert.assertTrue(false);
                }
            } else if (cmd.getCommand() == UserToGameCall.CMD_ERROR) {
                try {
                    System.out.println("Error info : " + cmd.getString());
                } catch (Exception e) {
                    Assert.assertTrue(false);
                }
            }else if(cmd.getCommand() == UserToGameCall.EXIT) {
                break;
            }
        }
    }

    @After
    public void tearDown(){

    }
}