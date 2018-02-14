package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import game.board.BoardManager;
import game.gameMaster.GameMaster;
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
import ui.display.BoardCanvas;

import static ui.commands.UserToGameCall.CMD_ERROR;
import static ui.commands.UserToGameCall.EXIT;

public class TermGUI extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 640;
    private static final int COMMAND_PANEL_HEIGHT = 28;
    private static final int COMMAND_PANEL_MARGIN = 10;

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

    public void createScene() {
        Group root = new Group();
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT + COMMAND_PANEL_HEIGHT, Color.LIGHTGREY);

        canvas = new BoardCanvas(WINDOW_WIDTH, WINDOW_HEIGHT);

        textField = new TextField("Enter command here");
        textField.setOnKeyPressed(new CommandEvent());
        textField.setPrefWidth(WINDOW_WIDTH);
        textField.setPrefHeight(COMMAND_PANEL_HEIGHT);

        labelPlayerTurn = new Label(GameMaster.getInstance().getActualState().getActualPlayer().name());
        labelPlayerTurn.setPrefHeight(COMMAND_PANEL_HEIGHT);

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        createScene();
        primaryStage.setTitle("JdlG");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        canvas.draw(BoardManager.getInstance().getBoard());
    }

    void processCommand(String cmd) {
        UIAction action = this.parse(cmd);
        if (action.getCommand() == CMD_ERROR) {
            if (action.getErrorMessage() != null) System.out.println(action.getErrorMessage());
            else System.out.println("Incorrect command.");
            return;
        }
        processResponse(Game.getInstance().processCommand(action));
    }

    UIAction parse(String cmd) {
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

    void processResponse(GameResponse response) {
        switch (response.getResponse()) {
            case VALID: {
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
            case REFRESH: {
                break;
            }
            case GAME_ERROR: {
                break;
            }
        }
    }

    private class CommandEvent implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                processCommand(textField.getText());
                textField.clear();
                labelPlayerTurn.setText(GameMaster.getInstance().getActualState().getActualPlayer().name());
            }
            canvas.draw(BoardManager.getInstance().getBoard());
        }
    }
}
