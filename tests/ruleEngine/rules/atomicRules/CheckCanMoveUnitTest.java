package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.board.Unit;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import java.util.LinkedList;
import java.util.List;

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
        List<Unit> l = new LinkedList<>();
        when(iGameState.getAllUnits()).thenReturn(l);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckCanMoveUnit : This unit has already moved.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        ruleResult = new RuleResult();
        Unit u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        u.setPosition(1, 1);
        l.add(u);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }
}