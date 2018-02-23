import game.EPlayer;
import game.Game;
import game.board.Board;
import game.board.Building;
import game.board.Unit;
import game.gameMaster.GameState;
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

        Board board = Game.getInstance().getBoardManager();
        GameState gameState = Game.getInstance().getGameStateManager();

        board.setUnit(EUnitData.RELAY_HORSE, EPlayer.PLAYER_SOUTH, 10, 10);
        Unit u = new Unit(EUnitData.RELAY_HORSE, EPlayer.PLAYER_SOUTH);
        u.setPosition(10, 10);
        gameState.addUnit(u);

        board.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH, 0, 0);
        u = new Unit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH);
        u.setPosition(0, 0);
        gameState.addUnit(u);

        Building b = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_NORTH);
        b.setPosition(0, 7);
        board.setBuilding(EBuildingData.ARSENAL, EPlayer.PLAYER_NORTH, 0, 7);
        gameState.addBuilding(b);

        b = new Building(EBuildingData.FORTRESS, EPlayer.PLAYER_NORTH);
        b.setPosition(10, 10);
        board.setBuilding(EBuildingData.FORTRESS, EPlayer.PLAYER_NORTH, 10, 10);
        gameState.addBuilding(b);

        b = new Building(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH);
        b.setPosition(1, 1);
        board.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH, 1, 1);
        gameState.addBuilding(b);

        guiThread.start();

        Game.getInstance().start();
    }
}
