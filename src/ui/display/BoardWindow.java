package ui.display;

import game.board.Board;
import game.board.BoardManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

/*
 * Singleton difficile ou impossible à cause de JavaFX, allocation à améliorer !
 */

public class BoardWindow extends Application {
    private static final int WINDOW_WIDHT = 650;
    private static final int WINDOW_HEIGHT = 520;
    private static BoardCanvas canvas;

    public static void update(){
        if(canvas != null) canvas.draw(BoardManager.getInstance().getBoard());
    }

    public BoardWindow(){
        canvas = new BoardCanvas(WINDOW_WIDHT, WINDOW_HEIGHT);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JdlG");
        Group root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDHT, WINDOW_HEIGHT, Color.LIGHTGREY);
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        update();
    }
}
