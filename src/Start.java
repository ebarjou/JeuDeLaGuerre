import game.EPlayer;
import game.Game;
import game.board.PrimitiveBoard;
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

        PrimitiveBoard boardManager = Game.getInstance().getBoardManager();

        boardManager.setUnit(EUnitData.RELAY_HORSE, EPlayer.PLAYER_SOUTH, 10, 10);
        boardManager.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH, 0, 0);

        boardManager.setBuilding(EBuildingData.ARSENAL, EPlayer.PLAYER_NORTH, 0, 7);
        boardManager.setBuilding(EBuildingData.FORTRESS, EPlayer.PLAYER_NORTH, 10, 10);
        boardManager.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH, 1, 1);

        boardManager.setInCommunication(EPlayer.PLAYER_NORTH, 0, 0, true);
        boardManager.setInCommunication(EPlayer.PLAYER_NORTH, 1, 0, true);
        boardManager.setInCommunication(EPlayer.PLAYER_NORTH, 0, 1, true);

        boardManager.setInCommunication(EPlayer.PLAYER_SOUTH, 0, 1, true);
        boardManager.setInCommunication(EPlayer.PLAYER_SOUTH, 1, 1, true);

        guiThread.start();

        Game.getInstance().start();
    }
}
