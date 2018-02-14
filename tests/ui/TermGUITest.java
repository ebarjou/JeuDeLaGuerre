package ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ui.commands.UserToGameCall;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TermGUITest {
    TermGUI term;
    List<COMMANDS> test_fail;

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
        C10("MoVe c4 g8", false),
        C11("revert", false);

        COMMANDS(String cmd, boolean isValid){
            this.cmd = cmd;
            this.isValid = isValid;
        }
        String cmd;
        boolean isValid;
    }

    @Before
    public void setUp() throws Exception {
        term = new TermGUI();
        term.init();
        test_fail = new ArrayList<>();
    }

    @Test
    public void parse() {
        UIAction UIAction;
        for(COMMANDS cmd : COMMANDS.values()){
            UIAction = term.parseCommand(cmd.cmd);
            if((UIAction.getCommand()==UserToGameCall.CMD_ERROR) && cmd.isValid ) test_fail.add(cmd);
        }
    }

    @After
    public void tearDown() throws Exception {
        for(COMMANDS c: test_fail) System.out.println(c.toString() + " Failed.");
        assertTrue(test_fail.size() == 0);
    }

}