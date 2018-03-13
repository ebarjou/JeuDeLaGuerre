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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsPriorityUnitTest {
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
        CheckIsPriorityUnit rule = new CheckIsPriorityUnit();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_SOUTH);
        List<Unit> l = new LinkedList<>();
        Unit u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        when(iGameState.getPriorityUnits()).thenReturn(l);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        //Covered by rule dependencies
        //l.add(u);
        //assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        //assertTrue(ruleResult.isValid());

        u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_SOUTH);
        u.setPosition(1, 1);
        l.add(u);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        u.setPosition(0, 0);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsPriorityUnit : There are other units that need to be moved first.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}