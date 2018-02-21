package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.BoardManager;
import game.board.IBoardManager;
import game.board.PrimitiveBoard;
import game.gameMaster.GameState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;


public class CheckUnitMPTest {

    private GameAction gameAction;
    private GameState gameState;
    private PrimitiveBoard board;
    private CheckUnitMP rule;

    @Before
    public void setUp(){
        board = new PrimitiveBoard(25, 20);

        // MovementValue = 1
        board.setUnit(EUnitData.INFANTRY, EPlayer.PLAYER1,0, 0);
        // MovementValue = 2
        board.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER1,5, 5);

        gameAction = new GameAction(EPlayer.PLAYER1, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        rule = new CheckUnitMP();

        gameState = Mockito.mock(GameState.class);
    }

    @Test
    public void checkActionValidMP() {
        gameAction.setSourceCoordinates(5, 5);
        gameAction.setTargetCoordinates(3, 7);

        RuleResult result = new RuleResult();
        String expectedMessage = "";

        Assert.assertTrue(rule.checkAction(board, null, gameAction, result));
        Assert.assertTrue(result.getLogMessage().equals(expectedMessage));
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidMPMocking() {
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        RuleResult result = new RuleResult();
        String expectedMessage = "CheckUnitMP : Not enough movement point, the unit has "
                + "1 MP, and you need 2 MP\n";

        Assert.assertFalse(rule.checkAction(board, null, gameAction, result));
        Assert.assertTrue(result.getLogMessage().equals(expectedMessage));
        Assert.assertFalse(result.isValid());
    }
}
