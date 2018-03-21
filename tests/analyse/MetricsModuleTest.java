package analyse;

import game.EPlayer;
import game.Game;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import system.LoadFile;

import static org.mockito.Mockito.*;

public class MetricsModuleTest {

    private GameState gameState;

    @Before
    public void setUp() throws Exception {
        Player player = mock(Player.class);
        Game.init(player, player);
        System.out.print(System.getProperty("user.dir"));
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/debord.txt");
    }

    @Test
    public void getInfoMap() {
        //dumb test lol
        GameState s = Game.getInstance().getGameState();
        double[][] result = MetricsModule.getInfoMap(EMetricsMapType.ATTACK_MAP, s, EPlayer.PLAYER_NORTH);
        //TODO:Remove Printf useless in all project
        for (double[] y : result){
            for(double x : y)
                System.out.print("[" + (int)x + "]");

            System.out.println();
        }

    }
}