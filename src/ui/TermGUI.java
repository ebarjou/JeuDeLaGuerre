package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import player.Player;
import ruleEngine.EGameActionType;

import static ui.commands.UserToGameCall.*;

public class TermGUI extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 640;
    private static final int COMMAND_PANEL_HEIGHT = 28;
    private static final int COMMAND_PANEL_MARGIN = 10;
    private static final int BOARD_INDICES_SIZE = 20;

    private Shell shell;
    private Scene scene;
    private BoardCanvas canvas;
    private TextField textField;
    private CommandParser parser;
    private Label labelPlayerTurn;

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
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

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
        scene = new Scene(root, WINDOW_WIDTH + BOARD_INDICES_SIZE, WINDOW_HEIGHT + COMMAND_PANEL_HEIGHT + BOARD_INDICES_SIZE, Color.LIGHTGREY);

        textField = new TextField("Enter command here");
        textField.setOnKeyPressed(new CommandEvent());
        textField.setPrefWidth(WINDOW_WIDTH);
        textField.setPrefHeight(COMMAND_PANEL_HEIGHT);

        labelPlayerTurn = new Label(Game.getInstance().getGameState().getActualPlayer().name());
        labelPlayerTurn.setPrefHeight(COMMAND_PANEL_HEIGHT);

        canvas = new BoardCanvas(WINDOW_WIDTH, WINDOW_HEIGHT, BOARD_INDICES_SIZE, BOARD_INDICES_SIZE, textField);

        HBox layoutCommand = new HBox();
        layoutCommand.setAlignment(Pos.CENTER_LEFT);
        layoutCommand.setSpacing(COMMAND_PANEL_MARGIN);
        layoutCommand.getChildren().add(labelPlayerTurn);
        layoutCommand.getChildren().add(textField);

        VBox layout = new VBox();
        layout.getChildren().add(canvas);
        layout.getChildren().add(layoutCommand);

        root.getChildren().add(layout);
    }


    /**
     * @param cmd the command that need to be parsed
     * @return the corresponding UIAction, with CMD_ERROR type if cmd was not a correct command.
     */
    UIAction parseCommand(String cmd) {
        if (cmd == null) return new UIAction(EXIT, null);
        UIAction result;
        try {
            shell.processLine(cmd);
            result = parser.getResult();
        } catch (CLIException e) {
            result = new UIAction(CMD_ERROR, null);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    /**
     * @param cmd the command that need to be processed
     * Call parseCommand to get a valid UIAction and send it to the current Player.
     * Then, wait for the response and process it.
     */
    private void processCommand(String cmd) {
        parser.clearResult();
        UIAction action = this.parseCommand(cmd);
        if (action.getCommand() == CMD_ERROR) {
            if (action.getErrorMessage() != null) System.out.println(action.getErrorMessage());
            else System.out.println("Incorrect command.");
            return;
        }

        Player player = Game.getInstance().getPlayer();
        player.setCommand(action);
        processResponse(player.getResponse());
    }

    /**
     * @param response the GameResponse that need to be processed
     * Process the response and refresh the UI accordingly.
     */
    private void processResponse(GameResponse response) {
        switch (response.getResponse()) {
            case VALID: {
                System.out.println(response.getMessage());
                System.out.println("Valid !");
                break;
            }
            case INVALID: {
                if (response.getMessage() == null) System.out.println("Invalid !");
                else System.out.println(response.getMessage());
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
        labelPlayerTurn.setText(response.getPlayer().name());
        canvas.draw(response.getBoard());
    }

    private class CommandEvent implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                processCommand(textField.getText());
                textField.clear();
            }
        }
    }
}
