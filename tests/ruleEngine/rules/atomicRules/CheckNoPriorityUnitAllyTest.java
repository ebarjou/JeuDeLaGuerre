package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.board.Unit;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

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
        //when(isPlayerHasPriorityUnits(iGameState.getPriorityUnits(), EPlayer.PLAYER_NORTH)).thenReturn(false);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        //when(isPlayerHasPriorityUnits(iGameState.getPriorityUnits(), EPlayer.PLAYER_NORTH)).thenReturn(true);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckNoPriorityUnitAlly : There is at least one priority unit.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    private boolean isPlayerHasPriorityUnits(List<Unit> priorityUnits, EPlayer player) {
        for(Unit unit : priorityUnits)
            if(unit.getPlayer() == player)
                return true;
        return false;
    }
}