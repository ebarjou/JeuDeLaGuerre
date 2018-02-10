package ui.display;

import game.board.Board;
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
    private static final int WINDOW_WIDHT = 600;
    private static final int WINDOW_HEIGHT = 480;
    private static BoardCanvas canvas;
    private static Board board;

    public static void updateBoard(Board board){
        BoardWindow.board = board;
        if(canvas != null) canvas.draw(board);
    }

    public BoardWindow(){
        canvas = new BoardCanvas(WINDOW_WIDHT, WINDOW_HEIGHT);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JdlG");
        primaryStage.setResizable(false);
        Group root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDHT, WINDOW_HEIGHT, Color.LIGHTGREY);
        canvas.draw(board);
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
