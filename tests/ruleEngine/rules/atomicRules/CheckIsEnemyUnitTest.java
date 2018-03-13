package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsEnemyUnitTest {
    private IBoard iBoard;
    private IGameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        iBoard = mock(IBoard.class);
        iGameState = mock(IGameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
    }

    @Test
    public void checkActionMocking() {
        CheckIsEnemyUnit rule = new CheckIsEnemyUnit();
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iBoard.isUnit(1, 1)).thenReturn(true);
        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iBoard.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_SOUTH);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iBoard.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_NORTH);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsEnemyUnit : Targeted unit is not an enemy.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

    }
}