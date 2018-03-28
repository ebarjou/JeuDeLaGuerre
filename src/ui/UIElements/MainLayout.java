package ui.UIElements;

import analyse.EMetricsMapType;
import game.EPlayer;
import game.gameState.GameState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class MainLayout extends BorderPane {
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 640;
    public static final int COMMAND_HEIGHT = 50;
    public static final int INFOS_WIDTH = 200;
    public static final int COORDINATES_BAR_WIDTH = 20;

    private static final int COMMAND_PANEL_HEIGHT = 28;
    private static final int COMMAND_PANEL_MARGIN = 10;

    private final ChoiceBox metricsDisplay;
    private ObservableList metricsList =
            FXCollections.observableArrayList(
                    EMetricsMapType.COMMUNICATION_MAP,
                    new Separator(),
                    EMetricsMapType.RANGE_MAP_FAST,
                    EMetricsMapType.RANGE_MAP_1M,
                    new Separator(),
                    EMetricsMapType.ATTACK_MAP_STATIC,
                    EMetricsMapType.ATTACK_MAP_FAST,
                    EMetricsMapType.ATTACK_MAP_1M,
                    new Separator(),
                    EMetricsMapType.DEFENSE_MAP_STATIC,
                    EMetricsMapType.DEFENSE_MAP_FAST,
                    new Separator(),
                    EMetricsMapType.OUTCOME_MAP_FAST,
                    EMetricsMapType.OUTCOME_MAP_FAST_STATIC
            );
    private BoardCanvas canvas;
    private CommandPane commandPane;
    private InfosPane infosPane;
    private CanvasPane gamePane;
    private TextField textField;

    public MainLayout() {
        this.setMinHeight(CANVAS_HEIGHT + COMMAND_HEIGHT + COORDINATES_BAR_WIDTH);
        this.setMinWidth(CANVAS_WIDTH + INFOS_WIDTH + COORDINATES_BAR_WIDTH);

        textField = new TextField();
        textField.setPrefWidth(Double.MAX_VALUE);
        canvas = new BoardCanvas(CANVAS_WIDTH + COORDINATES_BAR_WIDTH, CANVAS_HEIGHT + COORDINATES_BAR_WIDTH, COORDINATES_BAR_WIDTH, textField);
        textField.setPrefWidth(canvas.getWidth());

        metricsDisplay = new ChoiceBox(metricsList);
        metricsDisplay.valueProperty().addListener(new ChoiceBoxEvent());

        CheckBox metricsCB_North = new CheckBox(EPlayer.PLAYER_NORTH.toString());
        metricsCB_North.setSelected(true);
        metricsCB_North.selectedProperty().addListener(new MetricsChannelEvent(EPlayer.PLAYER_NORTH.ordinal()));

        CheckBox metricsCB_South = new CheckBox(EPlayer.PLAYER_SOUTH.toString());
        metricsCB_South.setSelected(true);
        metricsCB_South.selectedProperty().addListener(new MetricsChannelEvent(EPlayer.PLAYER_SOUTH.ordinal()));

        commandPane = new CommandPane(textField);
        infosPane = new InfosPane(metricsDisplay, metricsCB_North, metricsCB_South);
        gamePane = new CanvasPane(CANVAS_WIDTH, CANVAS_HEIGHT, canvas);

        this.setBottom(commandPane);
        this.setCenter(gamePane);
        this.setRight(infosPane);
    }

    public void refresh(GameState board) {
        canvas.draw(board);
        commandPane.setPlayer(board.getActualPlayer().toString(), board.getActualPlayer() == EPlayer.PLAYER_SOUTH ? Color.ORANGERED : Color.BLUE);
        commandPane.setActionLeft(board.getActionLeft());
    }

    public void setCommandHandler(EventHandler<? super KeyEvent> value) {
        textField.setOnKeyPressed(value);
    }

    public String getCommandText() {
        return textField.getText();
    }

    public void clearCommandText() {
        textField.clear();
    }

    private class ChoiceBoxEvent implements ChangeListener<EMetricsMapType> {
        @Override
        public void changed(ObservableValue ov, EMetricsMapType oldValue, EMetricsMapType newValue) {
            canvas.setMetricsMapType(newValue);
            canvas.draw(null);
        }
    }

    private class MetricsChannelEvent implements ChangeListener<Boolean> {
        private int id;

        public MetricsChannelEvent(int id) {
            this.id = id;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            canvas.setDisplayMetrics(id, newValue);
            canvas.draw(null);
        }
    }
}
