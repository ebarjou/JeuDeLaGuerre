package ui;

import javafx.application.Application;
import ui.TermGUI;

public class GUIThread extends Thread {

    public GUIThread(){
        super();
    }

    public void run() {
        Application.launch(TermGUI.class);
    }

}