import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ui.TermCommand;

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
        in = new ByteArrayInputStream(("azeaqsdqds\nqrvdfvdvf\n").getBytes());
        System.setIn(in);
        term = new TermCommand();
    }

    @Test
    public void process() {
        do{
            cmd = term.ask();
            term.respond("Got <" + cmd + ">");
        } while(!cmd.equalsIgnoreCase("exit"));
        Assert.assertTrue(true);
    }

    @After
    public void tearDown(){

    }
}