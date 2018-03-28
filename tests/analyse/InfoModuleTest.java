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

import static org.mockito.Mockito.mock;

public class InfoModuleTest {

    private GameState s;

    @Before
    public void setUp() throws Exception {
        Player player = mock(Player.class);
        Game.init(player, player);
        System.out.print(System.getProperty("user.dir"));
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/debord.txt");
        RuleChecker rc = new RuleChecker();
        rc.checkAndApplyAction(Game.getInstance().getGameState(), new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));
        s = Game.getInstance().getGameState();
    }

    @Test
    public void attackMap(){
        System.out.println("Attack Map");
        printMap(InfoModule.getInfoMap(EMetricsMapType.ATTACK_MAP_STATIC, s, EPlayer.PLAYER_NORTH));
    }

    @Test
    public void rangeMap1m(){
        System.out.println("Range 1M Map");
        printMap(InfoModule.getInfoMap(EMetricsMapType.ATTACK_MAP_1M, s, EPlayer.PLAYER_NORTH));
    }

    @Test
    public void rangeMapFast(){
        System.out.println("Range Fast Map");
        printMap(InfoModule.getInfoMap(EMetricsMapType.ATTACK_MAP_FAST, s, EPlayer.PLAYER_NORTH));
    }


    private void printMap(double[][] map){
        for (double[] y : map){
            for(double x : y)
                System.out.print("[" + (int)x + "]");

            System.out.println();
        }
    }
}