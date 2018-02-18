import game.EPlayer;
import game.Game;
import game.board.BoardManager;
import game.board.IBoardManager;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import player.GUIPlayer;
import ui.GUIThread;

public class Start {
    public static void main(String[] arg) {
        GUIThread guiThread = new GUIThread();

        GUIPlayer p1 = new GUIPlayer(Thread.currentThread(), guiThread);
        GUIPlayer p2 = new GUIPlayer(Thread.currentThread(), guiThread);

        Game.init(p1, p2);

        IBoardManager boardManager = Game.getInstance().getBoardManager();
        boardManager.initBoard(25, 20);
        boardManager.addUnit(EUnitData.RELAY_HORSE, EPlayer.PLAYER2, 10, 10);
        boardManager.addUnit(EUnitData.CAVALRY, EPlayer.PLAYER1, 0, 0);

        boardManager.addBuilding(EBuildingData.ARSENAL, EPlayer.PLAYER1, 0, 7);
        boardManager.addBuilding(EBuildingData.FORTRESS, EPlayer.PLAYER1, 10, 10);
        boardManager.addBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1, 1, 1);

        boardManager.setCommunication(EPlayer.PLAYER1, 0, 0, true);
        boardManager.setCommunication(EPlayer.PLAYER1, 1, 0, true);
        boardManager.setCommunication(EPlayer.PLAYER1, 0, 1, true);

        boardManager.setCommunication(EPlayer.PLAYER2, 0, 1, true);
        boardManager.setCommunication(EPlayer.PLAYER2, 1, 1, true);

        guiThread.start();

        Game.getInstance().start();
    }
}
