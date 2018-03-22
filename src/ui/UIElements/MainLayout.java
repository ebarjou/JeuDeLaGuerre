package ui.UIElements;

import analyse.EMetricsMapType;
import game.EPlayer;
import game.gameState.GameState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class MainLayout extends BorderPane{
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 640;
    public static final int COMMAND_HEIGHT = 50;
    public static final int INFOS_WIDTH = 200;
    public static final int COORDINATES_BAR_WIDTH = 20;

    private static final int COMMAND_PANEL_HEIGHT = 28;
    private static final int COMMAND_PANEL_MARGIN = 10;

    private final ComboBox metricsDisplay;
    private final ObservableList<String> metricsList =
            FXCollections.observableArrayList(
                    "Communications",
                    "Offensive map",
                    "Defense map",
                    "Danger map (1M)",
                    "Danger map (Fast)"
            );

    private BoardCanvas canvas;
    private CommandPane commandPane;
    private InfosPane infosPane;
    private CanvasPane gamePane;
    private TextField textField;

    public MainLayout(){
        this.setMinHeight(CANVAS_HEIGHT + COMMAND_HEIGHT + COORDINATES_BAR_WIDTH);
        this.setMinWidth(CANVAS_WIDTH + INFOS_WIDTH + COORDINATES_BAR_WIDTH);

        textField = new TextField();
        textField.setPrefWidth(Double.MAX_VALUE);
        canvas = new BoardCanvas(CANVAS_WIDTH + COORDINATES_BAR_WIDTH, CANVAS_HEIGHT + COORDINATES_BAR_WIDTH, COORDINATES_BAR_WIDTH, textField);
        textField.setPrefWidth(canvas.getWidth());

        metricsDisplay = new ComboBox(metricsList);
        metricsDisplay.valueProperty().addListener(new MetrixBoxEvent());

        commandPane = new CommandPane(textField);
        infosPane = new InfosPane(metricsDisplay);
        gamePane = new CanvasPane(CANVAS_WIDTH, CANVAS_HEIGHT, canvas);

        this.setBottom(commandPane);
        this.setCenter(gamePane);
        this.setRight(infosPane);
    }

    public void refresh(GameState board){
        canvas.draw(board);
        commandPane.setPlayer(board.getActualPlayer().name(), board.getActualPlayer()== EPlayer.PLAYER_SOUTH?Color.ORANGERED:Color.BLUE);
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

    private class MetrixBoxEvent implements ChangeListener<String>{
        @Override
        public void changed(ObservableValue<? extends String> ov, String t, String t1) {
            if(t1.equals(metricsList.get(0))){
                canvas.setMetricsMapType(null);
            } else if(t1.equals(metricsList.get(1))){
                canvas.setMetricsMapType(EMetricsMapType.ATTACK_MAP);
            }else if (t1.equals(metricsList.get(3))){
                canvas.setMetricsMapType(EMetricsMapType.RANGE_MAP_1M);
            }else if (t1.equals(metricsList.get(4))){
                canvas.setMetricsMapType(EMetricsMapType.RANGE_MAP_FAST);
            }

            canvas.refresh();
        }
    }

}
