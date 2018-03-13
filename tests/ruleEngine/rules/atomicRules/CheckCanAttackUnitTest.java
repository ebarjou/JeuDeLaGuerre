package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class CheckCanAttackUnitTest {

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
        CheckCanAttackUnit rule = new CheckCanAttackUnit();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iGameState.isUnitCanAttack(any(Coordinates.class))).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
        when(iGameState.isUnitCanAttack(any(Coordinates.class))).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String expectedMessage = "CheckCanAttackUnit : This unit can't attack this turn.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());
    }

}