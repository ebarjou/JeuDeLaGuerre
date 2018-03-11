package ui;

import game.Game;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import player.GUIPlayer;
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
        C11("revert", false),
        C12("load presets/debord.txt", true),
        C13("revert", true),
        C14("revert fvds", false),
        C15("attack", false),
        C16("attack a0 a1", true),
        C17("attack A12 g8", true),
        C18("attack 2k g8", false),
        C19("end", true),
        C20("exit", true)
        ;

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
    public void appInit() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        new TermGUI().start(new Stage());
                    }
                });
            }
        });
        thread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {
        }
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