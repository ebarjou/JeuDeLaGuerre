package ui.UIElements;

import analyse.EMetricsMapType;
import com.sun.xml.internal.bind.v2.runtime.property.StructureLoaderBuilder;
import game.EPlayer;
import game.gameState.GameState;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.xpath.operations.Bool;

import java.util.*;


public class MainLayout extends BorderPane{
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 640;
    public static final int COMMAND_HEIGHT = 50;
    public static final int INFOS_WIDTH = 200;
    public static final int COORDINATES_BAR_WIDTH = 20;

    private static final int COMMAND_PANEL_HEIGHT = 28;
    private static final int COMMAND_PANEL_MARGIN = 10;
	private boolean initialize = true;
    private static final String COMBOBOX_SEPARATOR = "----------------";

    private final ComboBox metricsDisplay;
    private ObservableList<String> metricsList =
            FXCollections.observableArrayList(
                    "Communications",
                    "Offensive map",
                    "Defense map",
                    "Danger map (1M)",
                    "Danger map (Fast)"
            );
    private Map<String, EMetricsMapType> metricsObjects;
    private BoardCanvas canvas;
    private CommandPane commandPane;
    private InfosPane infosPane;
    private CanvasPane gamePane;
    private TextField textField;

    public MainLayout(){
        metricsObjects = new HashMap<>();
//        for (EMetricsMapType t : EMetricsMapType.values())
//            metricsObjects.put(t.getMapName(), t);

        List<String> labels = new LinkedList<>();
        for (int i = 0; i <= EMetricsMapType.getMaxIndex(); ++i){
            EMetricsMapType t = EMetricsMapType.getType(i);
            if (t == null){
                labels.add(COMBOBOX_SEPARATOR);
            }else{
                labels.add(t.getMapName());
                metricsObjects.put(t.getMapName(), t);
            }
        }

        metricsList = FXCollections.observableArrayList(labels);
        this.setMinHeight(CANVAS_HEIGHT + COMMAND_HEIGHT + COORDINATES_BAR_WIDTH);
        this.setMinWidth(CANVAS_WIDTH + INFOS_WIDTH + COORDINATES_BAR_WIDTH);

        textField = new TextField();
        textField.setPrefWidth(Double.MAX_VALUE);
        canvas = new BoardCanvas(CANVAS_WIDTH + COORDINATES_BAR_WIDTH, CANVAS_HEIGHT + COORDINATES_BAR_WIDTH, COORDINATES_BAR_WIDTH, textField);
        textField.setPrefWidth(canvas.getWidth());

        metricsDisplay = new ComboBox(metricsList);
        metricsDisplay.valueProperty().addListener(new MetrixBoxEvent());

        CheckBox[] metricsChannels = new CheckBox[2];
		metricsChannels[0] = new CheckBox(EPlayer.PLAYER_NORTH.toString());
		metricsChannels[1] = new CheckBox(EPlayer.PLAYER_SOUTH.toString());
		metricsChannels[0].setSelected(true);
		metricsChannels[1].setSelected(true);
		metricsChannels[0].selectedProperty().addListener(new MetrixChannelEvent());
		metricsChannels[1].selectedProperty().addListener(new MetrixChannelEvent());

        commandPane = new CommandPane(textField);
        infosPane = new InfosPane(metricsDisplay, metricsChannels);
        gamePane = new CanvasPane(CANVAS_WIDTH, CANVAS_HEIGHT, canvas, metricsChannels);

        this.setBottom(commandPane);
        this.setCenter(gamePane);
        this.setRight(infosPane);
    }

    public void refresh(GameState board){
        canvas.draw(board);
        commandPane.setPlayer(board.getActualPlayer().toString(), board.getActualPlayer()== EPlayer.PLAYER_SOUTH?Color.ORANGERED:Color.BLUE);
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
            if (t1.equals(COMBOBOX_SEPARATOR)){
				Platform.runLater(() -> metricsDisplay.getSelectionModel().select(t));
            }else if (initialize || !t.equals(COMBOBOX_SEPARATOR)){
                canvas.setMetricsMapType(metricsObjects.get(t1));
                canvas.refresh(!initialize);
                initialize = false;
            }
        }
    }

    private class MetrixChannelEvent implements ChangeListener<Boolean>{

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			canvas.refresh(false);
		}
	}
}
