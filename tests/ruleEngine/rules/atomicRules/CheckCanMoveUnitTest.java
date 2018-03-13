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

public class CheckCanMoveUnitTest {

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
        CheckCanMoveUnit rule = new CheckCanMoveUnit();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        //when(iGameState.isUnitCanMove(any())).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
        //when(iGameState.isUnitCanMove(any())).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String expectedMessage = "CheckCanMoveUnit : This unit has already moved.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());
    }
}