import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ui.SharedCommand;
import ui.TermCommand;
import ui.UserCommand;
import ui.commands.UserToGameCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TermCommandTest {
    TermCommand term;
    ByteArrayInputStream in;
    String cmd;

    @Before
    public void setUp(){
        in = new ByteArrayInputStream(("move A3 b18\n").getBytes());
        System.setIn(in);
        term = new TermCommand();
    }

    @Test
    public void process() {
        UserCommand term = new TermCommand();

        SharedCommand cmd = term.getNextCommand();
        System.out.println("CMD:"+ cmd +" -> " + cmd.getCommand());
        if(cmd.getCommand() == UserToGameCommand.MOVE){
            try {
                int[] a = cmd.commandGetCoords1();
                int[] b = cmd.commandGetCoords2();
                System.out.println("From ["+a[0]+","+a[1]+"] To ["+b[0]+","+b[1]+"]");
            } catch (Exception e){
                Assert.assertTrue(false);
            }
        }
        Assert.assertTrue(true);
    }

    @After
    public void tearDown(){

    }
}