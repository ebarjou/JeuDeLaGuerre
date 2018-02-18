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
import player.Player;

import static ui.commands.UserToGameCall.CMD_ERROR;
import static ui.commands.UserToGameCall.EXIT;
import static ui.commands.UserToGameCall.REFRESH;

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

    @Override
    public void start(Stage primaryStage) {
        createScene();
        primaryStage.setTitle("JdlG");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        Player player = Game.getInstance().getPlayer();
        player.setCommand(new UIAction(REFRESH, null));
        processResponse(player.getResponse());
    }

    private void createScene() {
        Group root = new Group();
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT + COMMAND_PANEL_HEIGHT, Color.LIGHTGREY);

        canvas = new BoardCanvas(WINDOW_WIDTH, WINDOW_HEIGHT);

        textField = new TextField("Enter command here");
        textField.setOnKeyPressed(new CommandEvent());
        textField.setPrefWidth(WINDOW_WIDTH);
        textField.setPrefHeight(COMMAND_PANEL_HEIGHT);

        labelPlayerTurn = new Label(Game.getInstance().getGameMaster().getActualState().getActualPlayer().name());
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

    private void processResponse(GameResponse response) {
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
