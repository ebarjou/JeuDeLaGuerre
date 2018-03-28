package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import player.Player;
import ruleEngine.EGameActionType;
import ui.UIElements.MainLayout;
import ui.UIElements.ResultAlert;

import static ui.commands.UserToGameCall.*;

public class TermGUI extends Application {

    private Shell shell;
    private Scene scene;
    private CommandParser parser;
    private MainLayout mainLayout;

    @Override
    public void init() throws Exception {
        super.init();
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
    }

    @Override
    public void start(Stage primaryStage) {
        createScene();
        primaryStage.setTitle("JdlG");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(MainLayout.CANVAS_WIDTH + MainLayout.INFOS_WIDTH + MainLayout.COORDINATES_BAR_WIDTH + 16);      /*      */
        primaryStage.setMinHeight(MainLayout.CANVAS_HEIGHT + MainLayout.COMMAND_HEIGHT + MainLayout.COORDINATES_BAR_WIDTH + 40); /* JavaFx's stage size seems to contains window's edges */
        primaryStage.sizeToScene();
        primaryStage.show();

        //Init the canvas with the Game's Board
        Player player = Game.getInstance().getPlayer();
        UIAction startAction = new UIAction(LOAD, EGameActionType.COMMUNICATION);
        startAction.setText("presets/debord.txt");
        player.setCommand(startAction);
        processResponse(player.getResponse());
    }

    /**
     * Create the GUI layout, should not be called outside of start method.
     */
    private void createScene() {
        Group root = new Group();
        scene = new Scene(root, Color.WHITESMOKE);

        mainLayout = new MainLayout();
        mainLayout.prefHeightProperty().bind(scene.heightProperty());
        mainLayout.prefWidthProperty().bind(scene.widthProperty());

        mainLayout.setCommandHandler(new TermGUI.CommandEvent());

        root.getChildren().add(mainLayout);
    }


    /**
     * @param cmd the command that need to be parsed
     * @return the corresponding UIAction, with CMD_ERROR type if cmd was not a correct command.
     */
    UIAction parseCommand(String cmd) {
        if (cmd == null) return new UIAction(EXIT, EGameActionType.NONE);
        UIAction result;
        try {
            shell.processLine(cmd);
            result = parser.getResult();
        } catch (CLIException e) {
            result = new UIAction(CMD_ERROR, EGameActionType.NONE);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    /**
     * @param cmd the command that need to be processed
     *            Call parseCommand to get a valid UIAction and send it to the current Player.
     *            Then, wait for the response and process it.
     */
    private void processCommand(String cmd) {
        parser.clearResult();
        UIAction action = this.parseCommand(cmd);
        /*if (action.getCommand() == CMD_ERROR) {
            if (action.getErrorMessage() != null) System.out.println(action.getErrorMessage());
            else System.out.println("Incorrect command.");
            return;
        }*/

        Player player = Game.getInstance().getPlayer();
        player.setCommand(action);
        processResponse(player.getResponse());
    }

    /**
     * @param response the GameResponse that need to be processed
     *                 Process the response and refresh the UI accordingly.
     */
    private void processResponse(GameResponse response) {
        switch (response.getResponse()) {
            case VALID: {
                //System.out.println(response.getMessage());
                if (response.getMessage() != null && !response.getMessage().isEmpty()) {
                    ResultAlert.getInstance().setMessage("Valid action", "Message : ", "Detailed result : ", response.getMessage());
                    ResultAlert.getInstance().show();
                }
                break;
            }
            case INVALID: {
                //resetDisplayText() just changes the invalid case text.
                ResultAlert.getInstance().resetDisplayText();
                if (response.getMessage() == null) ResultAlert.getInstance().setMessage("This is not a valid command.");
                else ResultAlert.getInstance().setMessage(response.getMessage());
                ResultAlert.getInstance().show();
                break;
            }
            case APPLIED: {
                break;
            }
            case GAME_ERROR: {
                break;
            }
            case REFRESH: {
                break;
            }
        }
        mainLayout.refresh(response.getBoard());
    }

    private class CommandEvent implements EventHandler<KeyEvent> {
        private boolean couldBeMove(String command) {
            return command.matches("[ ]*([a-zA-Z]+)([0-9]+)[ ]+([a-zA-Z]+)([0-9]+)[ ]*");
        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                String command = mainLayout.getCommandText();
                if (couldBeMove(command))
                    command = "move " + command;
                processCommand(command);
                mainLayout.clearCommandText();
            }
        }
    }
}
