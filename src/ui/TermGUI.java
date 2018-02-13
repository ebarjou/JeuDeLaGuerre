package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import game.board.BoardManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.display.BoardCanvas;

import static ui.commands.UserToGameCall.CMD_ERROR;
import static ui.commands.UserToGameCall.EXIT;

public class TermGUI extends Application{
    private static final int WINDOW_WIDHT = 650;
    private static final int WINDOW_HEIGHT = 520;
    private BoardCanvas canvas;
    private TextField textField;
    private CommandParser parser;
    private Shell shell;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);

        primaryStage.setTitle("JdlG");
        Group root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDHT, WINDOW_HEIGHT+28, Color.LIGHTGREY);
        canvas = new BoardCanvas(WINDOW_WIDHT, WINDOW_HEIGHT);
        textField = new TextField();
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    processCommand(textField.getText());
                    textField.clear();
                }
                canvas.draw(BoardManager.getInstance().getBoard());
            }
        });
        VBox layout = new VBox();
        layout.getChildren().add(canvas);
        layout.getChildren().add(textField);
        root.getChildren().add(layout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        canvas.draw(BoardManager.getInstance().getBoard());
    }

    private UIAction parse(String cmd) throws CLIException, CommandException{
        if(cmd == null) return new UIAction(EXIT, null);
        shell.processLine(cmd);
        if(parser.getResult().getCommand() == CMD_ERROR) throw new CommandException(parser.getResult().getErrorMessage());
        return parser.getResult();
    }

    protected void processCommand(String cmd){
        try {
            processResponse(Game.getInstance().processCommand(this.parse(cmd)));
        } catch (CommandException|CLIException e){
            if(e.getMessage() != null) System.out.println(e.getMessage());
            processResponse(Game.getInstance().processCommand(new UIAction(CMD_ERROR, null)));
        }
    }

    protected void processResponse(GameResponse response) {
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
