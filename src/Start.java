import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
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

        GameState gameState = Game.getInstance().getGameStateManager();

        Unit u = new Unit(EUnitData.RELAY_HORSE, EPlayer.PLAYER_SOUTH);
        u.setPosition(10, 10);
        gameState.addUnit(u);

        u = new Unit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH);
        u.setPosition(0, 0);
        gameState.addUnit(u);

        u = new Unit(EUnitData.RELAY_HORSE, EPlayer.PLAYER_SOUTH);
        u.setPosition(5, 5);
        gameState.addUnit(u);
        gameState.addPriorityUnit(u.clone());

        Building b = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_NORTH);
        b.setPosition(0, 7);
        gameState.addBuilding(b);

        b = new Building(EBuildingData.FORTRESS, EPlayer.PLAYER_NORTH);
        b.setPosition(10, 10);
        gameState.addBuilding(b);

        b = new Building(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH);
        b.setPosition(1, 1);
        gameState.addBuilding(b);

        guiThread.start();

        Game.getInstance().start();
    }
}
