import game.Game;
import game.board.BoardManager;
import game.board.IBoardManager;
import ui.TermUI;
import ui.UserInterface;

public class Start {

    public static void main(String[] arg){
        IBoardManager bm = BoardManager.getInstance();

        UserInterface ui = new TermUI();
        (new Game(ui)).startNewGame();
    }
}
