package game;

import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;import game.gameState.GameState;
import org.mockito.Mockito;
import player.GUIPlayer;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.exceptions.IncorrectGameActionException;
import ui.GameResponse;
import ui.UIAction;
import ui.commands.GameToUserCall;
import ui.commands.UserToGameCall;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    @Before
    public void setUp() throws Exception {
        GUIPlayer p1 = new GUIPlayer(Thread.currentThread(), null);
        GUIPlayer p2 = new GUIPlayer(Thread.currentThread(), null);
        game.init(p1, p2);
        game = Game.getInstance();
    }

    @Test
    public void gameTest() {
        GameAction action = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.NONE);
        action.setTargetCoordinates(0, 0);
        action.setSourceCoordinates(1, 0);

        UIAction uiAction = Mockito.mock(UIAction.class);
        Mockito.when(uiAction.getCommand()).thenReturn(UserToGameCall.GAME_ACTION);
        Mockito.when(uiAction.getGameAction(EPlayer.PLAYER_SOUTH)).thenReturn(action);
        Mockito.when(uiAction.getGameAction(EPlayer.PLAYER_NORTH)).thenReturn(action);

        GameResponse response = game.processCommand(uiAction);

        assertTrue(response.getResponse() == GameToUserCall.GAME_ERROR);
    }
}