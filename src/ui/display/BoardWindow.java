package ui.display;

import game.board.BoardManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Singleton difficile ou impossible à cause de JavaFX, allocation à améliorer !
 */

public class BoardWindow extends Application {
    private static final int WINDOW_WIDHT = 650;
    private static final int WINDOW_HEIGHT = 520;
    private BoardCanvas canvas;

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

        canvas.draw(BoardManager.getInstance().getBoard());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                canvas.draw(BoardManager.getInstance().getBoard());
            }
        }, 0, 500);
    }
}
