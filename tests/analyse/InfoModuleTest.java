package analyse;

import game.EPlayer;
import game.Game;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import system.LoadFile;

import static org.mockito.Mockito.*;

public class InfoModuleTest {

    private GameState gameState;

    @Before
    public void setUp() throws Exception {
        Player player = mock(Player.class);
        Game.init(player, player);
        System.out.print(System.getProperty("user.dir"));
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/debord.txt");
        RuleChecker rc = new RuleChecker();
        rc.checkAndApplyAction(Game.getInstance().getGameState(), new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));
    }

    @Test
    public void getInfoMap() {
        //dumb test lol
        GameState s = Game.getInstance().getGameState();
        System.out.println("Attack Map");
        printMap(InfoModule.getInfoMap(EMetricsMapType.ATTACK_MAP, s, EPlayer.PLAYER_NORTH));
        System.out.println("Range Map");
        printMap(InfoModule.getInfoMap(EMetricsMapType.RANGE_MAP, s, EPlayer.PLAYER_NORTH));
        //TODO:Remove Printf useless in all project

    }

    private void printMap(double[][] map){
        for (double[] y : map){
            for(double x : y)
                System.out.print("[" + (int)x + "]");

            System.out.println();
        }
    }
}