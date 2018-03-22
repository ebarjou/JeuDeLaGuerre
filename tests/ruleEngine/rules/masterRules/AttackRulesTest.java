package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;

import org.junit.Before;
import org.junit.Test;

import player.Player;
import ruleEngine.*;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;
import system.BadFileFormatException;
import system.LoadFile;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AttackRulesTest {

    private GameState gameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private String expectedMessage;
    private MoveRules ruleMove;
    private AttackRules ruleAttack;
    private RuleChecker ruleChecker;

    @Before
    public void setUp() throws Exception {
        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        ruleResult = new RuleResult();
        ruleMove = new MoveRules();
        ruleChecker = new RuleChecker();

        Player player = mock(Player.class);
        Game.init(player, player);
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/attackRulesTestFile.txt");

        gameState = Game.getInstance().getGameState();
        ruleChecker.checkAndApplyAction(gameState, new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));
    }

    @Test
    public void checkActionRealCorrectInfantryAttackInfantry() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitData.INFANTRY);

        gameAction.setSourceCoordinates(10, 8);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsCorrectAttack();
    }

    private void performAssertsCorrectMove(EUnitData unitData) {
        gameAction.setActionType(EGameActionType.MOVE);
        assertTrue(ruleMove.checkAction(gameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
        ruleMove.applyResult(gameState, gameAction, ruleResult);
        try {
            gameAction.setActionType(EGameActionType.COMMUNICATION);
            ruleChecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue(false);
        }
        Unit movedUnit = gameState.getLastUnitMoved();
        assertTrue(movedUnit.getX() == gameAction.getTargetCoordinates().getX());
        assertTrue(movedUnit.getY() == gameAction.getTargetCoordinates().getY());
        assertTrue(movedUnit.getPlayer() == gameState.getActualPlayer());
        assertTrue(movedUnit.getUnitData() == unitData);
    }

    private void performAssertsCorrectAttack() {
        gameAction.setActionType(EGameActionType.ATTACK);
        assertFalse(ruleAttack.checkAction(gameState, gameAction, ruleResult));
        System.out.println(ruleResult.getLogMessage());
        assertTrue(ruleResult.isValid());
        ruleAttack.applyResult(gameState, gameAction, ruleResult);
        try {
            gameAction.setActionType(EGameActionType.COMMUNICATION);
            ruleChecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue(false);
        }
    }

    private void performAssertsWrongAttack() {
        gameAction.setActionType(EGameActionType.ATTACK);
        assertFalse(ruleAttack.checkAction(gameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        if (!ruleResult.getLogMessage().equals(expectedMessage))
            System.out.print(ruleResult.getLogMessage());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}