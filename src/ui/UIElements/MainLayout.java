package ui.UIElements;

import game.Game;
import game.gameState.GameState;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ui.TermGUI;


public class MainLayout extends BorderPane{
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 640;
    public static final int COMMAND_HEIGHT = 50;
    public static final int INFOS_WIDTH = 200;
    public static final int COORDINATES_BAR_WIDTH = 20;

    private static final int COMMAND_PANEL_HEIGHT = 28;
    private static final int COMMAND_PANEL_MARGIN = 10;

    private BoardCanvas canvas;
    CommandPane commandPane;
    InfosPane infosPane;
    CanvasPane gamePane;
    private TextField textField;

    public MainLayout(){
        this.setMinHeight(CANVAS_HEIGHT + COMMAND_HEIGHT + COORDINATES_BAR_WIDTH);
        this.setMinWidth(CANVAS_WIDTH + INFOS_WIDTH + COORDINATES_BAR_WIDTH);

        textField = new TextField();
        textField.setPrefWidth(Double.MAX_VALUE);
        canvas = new BoardCanvas(CANVAS_WIDTH + COORDINATES_BAR_WIDTH, CANVAS_HEIGHT + COORDINATES_BAR_WIDTH, COORDINATES_BAR_WIDTH, textField);
        textField.setPrefWidth(canvas.getWidth());

        commandPane = new CommandPane(textField);
        infosPane = new InfosPane();
        gamePane = new CanvasPane(CANVAS_WIDTH, CANVAS_HEIGHT, canvas);

        this.setBottom(commandPane);
        this.setCenter(gamePane);
        this.setRight(infosPane);
    }

    public void refresh(GameState board){
        canvas.draw(board);
        commandPane.setPlayer(board.getActualPlayer().name());
        commandPane.setActionLeft(board.getActionLeft());
    }

    public void setCommandHandler(EventHandler<? super KeyEvent> value){
        textField.setOnKeyPressed(value);
    }

    public void setCommandText(String value){
        textField.setText(value);
    }

    public String getCommandText(){
        return textField.getText();
    }

    public void clearCommandText(){
        textField.clear();
    }

}
