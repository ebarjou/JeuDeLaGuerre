package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import org.mockito.Mockito;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import game.gameState.GameState;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class CheckPlayerTurnTest {

    private GameAction gameAction;
    private GameState gameState;
    private RuleResult result;
    private CheckPlayerTurn rule;
    private String expectedMessage;

    @Before
    public void setUp(){
        gameAction = Mockito.mock(GameAction.class);
        gameState = Mockito.mock(GameState.class);
        rule = new CheckPlayerTurn();
        result = new RuleResult();
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        expectedMessage = "CheckPlayerTurn : This is not player PLAYER_NORTH's turn.\n";
    }

    @Test
    public void checkActionMockingCorrect() {
        when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionMockingWrong() {
        when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_SOUTH);
        assertFalse(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertFalse(result.isValid());
    }
}
