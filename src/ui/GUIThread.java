package ui;

import javafx.application.Application;
import ui.TermGUI;

public class GUIThread extends Thread {

    public GUIThread(){
        super();
    }

    /**
     * Start the TermGUI JavaFX application in this GUIThread
     */
    public void run() {
        Application.launch(TermGUI.class);
        System.exit(0);
    }

}