import game.EPlayer;
import game.Game;
import game.board.BoardManager;
import game.board.IBoardManager;
import game.board.entity.EBuilding;
import game.board.entity.EUnit;
import ui.TermUI;

public class Start {

    public static void main(String[] arg){
        IBoardManager boardManager = BoardManager.getInstance();
        boardManager.initBoard(25, 20);
        boardManager.addUnit(EUnit.INFANTRY, EPlayer.PLAYER1, 10, 10);
        boardManager.addUnit(EUnit.INFANTRY, EPlayer.PLAYER1, 0, 0);
        boardManager.addBuilding(EBuilding.ARSENAL, EPlayer.PLAYER1, 0, 7);
        boardManager.addBuilding(EBuilding.FORTERESS, EPlayer.PLAYER1, 10, 10);
        boardManager.setCommunication(EPlayer.PLAYER1, 0, 0, true);
        boardManager.setCommunication(EPlayer.PLAYER1, 1, 0, true);
        boardManager.setCommunication(EPlayer.PLAYER1, 0, 1, true);
        boardManager.setCommunication(EPlayer.PLAYER2, 0, 1, true);
        boardManager.setCommunication(EPlayer.PLAYER2, 1, 1, true);

        Game game = Game.getInstance();
        TermUI ui = new TermUI();
        ui.start();
    }
}
