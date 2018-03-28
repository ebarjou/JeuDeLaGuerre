package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitProperty;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class CheckCanMoveUnitTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckCanMoveUnit rule;

    @Before
    public void setUp() throws Exception {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckCanMoveUnit();
    }

    @Test
    public void checkActionMockingCorrect() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        List<Unit> l = new LinkedList<>();
        when(iGameState.getAllUnits()).thenReturn(l);
        Unit u = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_NORTH);
        u.setPosition(1, 1);
        l.add(u);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrong() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        List<Unit> l = new LinkedList<>();
        when(iGameState.getAllUnits()).thenReturn(l);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckCanMoveUnit : This unit has already moved.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}