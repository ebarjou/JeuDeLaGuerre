import game.EPlayer;
import game.Game;
import game.board.BoardManager;
import game.board.IBoardManager;
import javafx.application.Application;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ui.TermGUI;
import ui.TermUI;
import ui.display.BoardWindow;

public class Start {

    public static void main(String[] arg){
        IBoardManager boardManager = BoardManager.getInstance();
        boardManager.initBoard(25, 20);
        boardManager.addUnit(EUnitData.INFANTRY, EPlayer.PLAYER1, 10, 10);
        boardManager.addUnit(EUnitData.CAVALRY, EPlayer.PLAYER1, 0, 0);

        boardManager.addBuilding(EBuildingData.ARSENAL, EPlayer.PLAYER1, 0, 7);
        boardManager.addBuilding(EBuildingData.FORTRESS, EPlayer.PLAYER1, 10, 10);
        boardManager.addBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1, 1, 1);

        boardManager.setCommunication(EPlayer.PLAYER1, 0, 0, true);
        boardManager.setCommunication(EPlayer.PLAYER1, 1, 0, true);
        boardManager.setCommunication(EPlayer.PLAYER1, 0, 1, true);

        boardManager.setCommunication(EPlayer.PLAYER2, 0, 1, true);
        boardManager.setCommunication(EPlayer.PLAYER2, 1, 1, true);

        Game game = Game.getInstance();
        Application.launch(TermGUI.class);
    }
}
