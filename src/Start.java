import game.EPlayer;
import game.Game;
import game.board.BoardManager;
import game.board.IBoardManager;
import game.board.entity.EBuilding;
import game.board.entity.EUnit;
import javafx.application.Application;
import ui.TermUI;
import ui.UserInterface;
import ui.display.BoardWindow;

public class Start {

    public static void main(String[] arg){
        IBoardManager boardManager = BoardManager.getInstance();
        boardManager.initBoard(25, 20);
        boardManager.addUnit(EUnit.INFANTRY, EPlayer.PLAYER1, 10, 10);
        boardManager.addBuilding(EBuilding.ARSENAL, EPlayer.PLAYER1, 14, 7);
        boardManager.addBuilding(EBuilding.FORTERESS, EPlayer.PLAYER1, 10, 10);
        UserInterface ui = new TermUI();
        (new Game(ui)).startNewGame();
    }
}
