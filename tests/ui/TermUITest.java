package ui;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ui.commands.UserToGameCall;

import java.io.ByteArrayInputStream;

public class TermUITest {
    TermUI term;
    enum COMMANDS{
        C1("move a2 b4", true),
        C2("move aa2 b66", true),
        C3("move abcsioih3455858 b9", true),
        C4("m j7 f2", true),
        C5("move A2 G4", true),
        C6("move a2", false),
        C7("move", false),
        C8("move a3 aag", false),
        C9("move 123 f6", false),
        C10("MoVe c4 g8", false);

        COMMANDS(String cmd, boolean isValid){
            this.cmd = cmd;
            this.isValid = isValid;
        }
        String cmd;
        boolean isValid;
    }

    @Before
    public void setUp(){
        //in = new ByteArrayInputStream(("move a3 b1\nmove E7 j23\nmove aaf4 f8\nmove E34t f5\nmove hgt A3\nmfd ef\nmove a3\nmove gg5 e6").getBytes());
        //System.setIn(in);
        term = new TermUI();

    }

    @Test
    public void process() {
        SharedCommand sharedCommand;
        for(COMMANDS cmd : COMMANDS.values()){
            sharedCommand = term.processCommand(cmd.cmd);
            Assert.assertTrue("Error at : "+cmd.toString(), (sharedCommand.getCommand()==UserToGameCall.CMD_ERROR)==!cmd.isValid );
        }
    }

    @After
    public void tearDown(){

    }
}