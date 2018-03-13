package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.board.Unit;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import java.util.List;

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
        //when(isUnitCanAttack(iGameState.getCantAttackUnits(), any(Coordinates.class))).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
        //when(isUnitCanAttack(iGameState.getCantAttackUnits(), any(Coordinates.class))).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String expectedMessage = "CheckCanAttackUnit : This unit can't attack this turn.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());
    }

    private boolean isUnitCanAttack(List<Unit> cantAttackUnits, Coordinates coords){
        for(Unit unit : cantAttackUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;
        return true;
    }

}