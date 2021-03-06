package ui.UIElements;

import game.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

class CommandPane extends HBox {
    private final int MARGIN = 10;
    private Label labelPlayerTurn;
    private Label labelActionLeft;


    public CommandPane(TextField commandTextField) {
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

        labelPlayerTurn = new Label(Game.getInstance().getGameState().getActualPlayer().toString());
        labelPlayerTurn.setTextFill(Game.getInstance().getGameState().getActualPlayer().getPreferedColor());
        labelPlayerTurn.setPrefHeight(MainLayout.COMMAND_HEIGHT);

        labelActionLeft = new Label("Action left : " + Game.getInstance().getGameState().getActionLeft());
        labelActionLeft.setPrefHeight(MainLayout.COMMAND_HEIGHT);

        this.setMinHeight(MainLayout.COMMAND_HEIGHT);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(MARGIN);
        this.getChildren().add(labelPlayerTurn);
        this.getChildren().add(commandTextField);
        this.getChildren().add(labelActionLeft);
    }

    public void setPlayer(String player, Color color) {
        labelPlayerTurn.setTextFill(color);
        labelPlayerTurn.setText(player);
    }

    public void setActionLeft(int n) {
        labelActionLeft.setText("Action left : " + n);
    }
}
