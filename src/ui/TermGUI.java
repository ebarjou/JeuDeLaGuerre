package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import game.board.BoardManager;
import game.gameMaster.GameMaster;
import javafx.application.Application;
import javafx.event.EventHandler;
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

public class TermGUI extends Application{
    private static final int WINDOW_WIDTH = 650;
    private static final int WINDOW_HEIGHT = 520;
    private BoardCanvas canvas;
    private TextField textField;
    private CommandParser parser;
    private Shell shell;
    private Label displayPlayerTurn;

    @Override
    public void init() throws Exception{
        super.init();
        System.out.println("INIT");
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JdlG");
        Group root = new Group();

        textField = new TextField("Enter command here");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT + 2 * textField.getFont().getSize(), Color.LIGHTGREY);

        canvas = new BoardCanvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        displayPlayerTurn = new Label(GameMaster.getInstance().getActualState().getActualPlayer().name());
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    processCommand(textField.getText());
                    textField.clear();
                    displayPlayerTurn.setText(GameMaster.getInstance().getActualState().getActualPlayer().name());
                }
                canvas.draw(BoardManager.getInstance().getBoard());
            }
        });

        HBox layoutPlayer = new HBox();
        layoutPlayer.getChildren().add(displayPlayerTurn);
        layoutPlayer.getChildren().add(textField);

        textField.setPrefWidth(WINDOW_WIDTH);

        VBox layout = new VBox();
        layout.getChildren().add(canvas);
        layout.getChildren().add(layoutPlayer);
        root.getChildren().add(layout);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        canvas.draw(BoardManager.getInstance().getBoard());
    }

    void processCommand(String cmd){
        UIAction action = this.parse(cmd);
        if(action.getCommand() == CMD_ERROR){
            if(action.getErrorMessage() != null) System.out.println(action.getErrorMessage());
            else System.out.println("Incorrect command.");
            return;
        }
        processResponse(Game.getInstance().processCommand(action));
    }

    UIAction parse(String cmd){
        if(cmd == null) return new UIAction(EXIT, null);
        UIAction result;
        try {
            shell.processLine(cmd);
            result = parser.getResult();
        } catch(CLIException e) {
            result = new UIAction(CMD_ERROR, null);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    void processResponse(GameResponse response) {
        switch (response.getResponse()){
            case VALID:{
                System.out.println("Valid !");
                break;
            }
            case INVALID:{
                if(response.getMessage() == null) System.out.println("Invalid !");
                else System.out.println(response.getMessage());
                break;
            }
            case APPLIED:{
                break;
            }
            case REFRESH:{
                break;
            }
            case GAME_ERROR:{
                break;
            }
        }
    }
}
