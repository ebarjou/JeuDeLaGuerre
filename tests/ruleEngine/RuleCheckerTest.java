package ruleEngine;

import game.EPlayer;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;

import static org.junit.Assert.assertTrue;

public class RuleCheckerTest {

    private RuleChecker rulechecker;
    private GameState gameState;

    @Before
    public void setUp(){
        rulechecker = new RuleChecker();
        gameState = new GameState(25, 20);

        Building building = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_NORTH);
        building.setPosition(10, 10);
        gameState.addBuilding(building);
        building = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_SOUTH);
        building.setPosition(0, 10);
        gameState.addBuilding(building);

        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);
        unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_SOUTH);
        unit.setPosition(0, 2);
        gameState.addUnit(unit);
    }

    @Test
    public void checkAndApplyActionMoveTest() {

        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check action MOVE because action COMMUNICATION failed beforehand.", false);
        }

        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(1, 1);

        RuleResult result = new RuleResult();
        result.invalidate();
        String expectedMessage = "";

        try {
            result = rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action MOVE unrecognized by RuleChecker.checkAndApplyAction().", false);
        }
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkAndApplyActionAttackTest() {

        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check action MOVE because action COMMUNICATION failed beforehand.", false);
        }

        gameAction.setActionType(EGameActionType.MOVE);
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(1, 1);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check action ATTACK because action MOVE failed beforehand.", false);
        }

        gameAction.setActionType(EGameActionType.ATTACK);
        gameAction.setSourceCoordinates(1, 1);
        gameAction.setTargetCoordinates(0, 2);

        RuleResult result = new RuleResult();
        result.invalidate();
        String expectedMessage = "AttackRules : The unit at position ("
                + gameAction.getTargetCoordinates().getX() + ", "
                + gameAction.getTargetCoordinates().getY()
                + ") has been attacked, but nothing happened : "
                + "Attack:4 Defense:6.\n";

        try {
            result = rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action ATTACK unrecognized by RuleChecker.checkAndApplyAction().", false);
        }
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkAndApplyActionEndTurnTest() {

        GameAction gameAction = Mockito.mock(GameAction.class);
        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        Mockito.when(gameAction.getActionType()).thenReturn(EGameActionType.END_TURN);

        RuleResult result = new RuleResult();
        result.invalidate();
        String expectedMessage = "";

        try {
            result = rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action END_TURN unrecognized by RuleChecker.checkAndApplyAction().", false);
        }
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkAndApplyActionVictoryAfterMoveTest() {

        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check Victory because action COMMUNICATION failed beforehand.", false);
        }

        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(1, 10);
        gameState.addUnit(unit);

        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(1, 10);
        gameAction.setTargetCoordinates(0, 10);

        RuleResult result = new RuleResult();
        String expectedMessage = "MoveRules : The Arsenal at position (0, 10) has been destroyed.\n" +
                "VictoryRules : North Player winner !\n\n";
        try {
            result = rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check Victory because action MOVE failed beforehand.", false);
        }
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkAndApplyActionVictoryAfterAttackTest() {

        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check Victory because action COMMUNICATION failed beforehand.", false);
        }

        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(1, 1);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check Victory because action MOVE failed beforehand.", false);
        }

        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(2, 0);
        gameState.addUnit(unit);

        gameAction.setActionType(EGameActionType.ATTACK);
        gameAction.setSourceCoordinates(1, 1);
        gameAction.setTargetCoordinates(0, 2);

        RuleResult result = new RuleResult();
        String expectedMessage = "AttackRules : The unit at position (0, 2) died : Attack:8 Defense:6.\n" +
                "VictoryRules : North Player winner !\n\n";
        try {
            result = rulechecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check Victory because action ATTACK failed beforehand.", false);
        }
        assertTrue(result.isValid());
        System.out.println(result.getLogMessage());
        assertTrue(result.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkAndApplyActionInvalidTest() {
        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.NONE);
        try {
            rulechecker.checkAndApplyAction(gameState, gameAction);
            assertTrue("Action NONE should not be recognized by RuleChecker.checkAndApplyAction().", false);
        } catch (IncorrectGameActionException ignored) {
        }
        gameAction.setActionType(EGameActionType.COMMUNICATION);
        try {
            rulechecker.checkAction(gameState, gameAction);
            assertTrue("Action COMMUNICATION should not be recognized by RuleChecker.checkAction().", false);
        } catch (IncorrectGameActionException ignored) {
        }
    }

    @Test
    public void checkActionInvalidTests() {
        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        try {
            rulechecker.checkAction(gameState, gameAction);
            assertTrue("Action COMMUNICATION should not be recognized by RuleChecker.checkAction().", false);
        } catch (IncorrectGameActionException ignored) {
        }
        gameAction.setActionType(EGameActionType.NONE);
        try {
            rulechecker.checkAction(gameState, gameAction);
            assertTrue("Action NONE should not be recognized by RuleChecker.checkAction().", false);
        } catch (IncorrectGameActionException ignored) {
        }
    }
}
