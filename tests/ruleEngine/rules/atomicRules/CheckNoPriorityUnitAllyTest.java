package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.board.Unit;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import java.net.NoRouteToHostException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckNoPriorityUnitAllyTest {
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
        CheckNoPriorityUnitAlly rule = new CheckNoPriorityUnitAlly();
        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        List<Unit> l = new LinkedList<>();
        when(iGameState.getPriorityUnits()).thenReturn(l);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        ruleResult = new RuleResult();
        Unit u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_SOUTH);
        l.add(u);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        String expectedMessage = "CheckNoPriorityUnitAlly : There is at least one priority unit.\n";
        u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        l.add(u);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    private boolean isPlayerHasPriorityUnits(List<Unit> priorityUnits, EPlayer player) {
        for(Unit unit : priorityUnits)
            if(unit.getPlayer() == player)
                return true;
        return false;
    }
}