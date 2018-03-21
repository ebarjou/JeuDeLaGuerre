package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import player.GUIPlayer;
import player.Player;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;
import system.BadFileFormatException;
import system.LoadFile;
import ui.GUIThread;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AttackRulesTest {

    private GameState gameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private RuleChecker rule;
    private String expectedMessage;

    @Before
    public void setUp() {
        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        ruleResult = new RuleResult();
        rule = new RuleChecker();

        GUIThread guiThread = new GUIThread();
        Player p1 = new GUIPlayer(Thread.currentThread(), guiThread);
        Player p2 = new GUIPlayer(Thread.currentThread(), guiThread);
        Game.init(p1, p2);
        LoadFile lf = new LoadFile();
        try {
            lf.loadFile("presets/attackRulesTestFile.txt");
        } catch (IOException|BadFileFormatException e) {
            assertTrue("Test class " + this.getClass().getSimpleName() +
                    " could not load the test file : Test interrupted.", false);
        }

        gameState = Game.getInstance().getGameState();

        try {
            GameAction communication = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
            RuleResult r = rule.checkAction(gameState, communication);
            assertTrue("Can't check actions ATTACK because action COMMUNICATION failed beforehand.", r.isValid());
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check actions ATTACK because action COMMUNICATION failed beforehand.", false);
        }

    }

    private void checkActionValidMove(EUnitData unitData) {
        try {
            ruleResult = rule.checkAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action MOVE wasn't recognized by the RuleChecker.", false);
        }
        assertTrue(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        Unit movedUnit = gameState.getLastUnitMoved();
        assertTrue(movedUnit.getX() == gameAction.getTargetCoordinates().getX());
        assertTrue(movedUnit.getY() == gameAction.getTargetCoordinates().getY());
        assertTrue(movedUnit.getPlayer() == gameState.getActualPlayer());
        assertTrue(movedUnit.getUnitData() == unitData);
    }

    private void checkActionValidAttack() {
        try {
            ruleResult = rule.checkAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action ATTACK wasn't recognized by the RuleChecker.", false);
        }
        assertTrue(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        try {
            gameState.getLastUnitMoved();
            assertTrue(false);
        } catch (NullPointerException ignored) {
        }
    }

    private void checkActionInvalidMove() {
        try {
            ruleResult = rule.checkAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action MOVE wasn't recognized by the RuleChecker.", false);
        }
        assertFalse(ruleResult.isValid());
        System.out.println(expectedMessage + "\n" + ruleResult.getLogMessage() +"\n\n");
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionValidAttackInfantry() {
        expectedMessage = "";
        gameAction.setSourceCoordinates(9, 10);
        gameAction.setTargetCoordinates(10, 10);

        checkActionValidMove(EUnitData.INFANTRY);
    }

    @Test
    public void checkActionValidAttackCavalry() {
        expectedMessage = "";
        gameAction.setSourceCoordinates(9, 9);
        gameAction.setTargetCoordinates(11, 11);

        checkActionValidMove(EUnitData.CAVALRY);
    }

    @Test
    public void checkActionValidAttackArtillery() {
        expectedMessage = "";
        gameAction.setSourceCoordinates(9, 11);
        gameAction.setTargetCoordinates(10, 11);

        checkActionValidMove(EUnitData.ARTILLERY);
    }

    @Test
    public void checkActionValidAttackArtilleryHorse() {
        expectedMessage = "";
        gameAction.setSourceCoordinates(9, 12);
        gameAction.setTargetCoordinates(11, 14);

        checkActionValidMove(EUnitData.ARTILLERY_HORSE);
    }

    @Test
    public void checkActionInvalidAttackInfantry() {
        expectedMessage = "CheckIsInCommunication : This unit is not in communication and cannot be used.\n" +
                "CheckIsAllyUnit : This unit is not owned by PLAYER_NORTH.\n" +
                "MoveRules : CheckUnitMP is not checked because it is dependant of the following rule(s) : \n" +
                "\t- CheckIsAllyUnit : expected Valid but got Invalid instead.\n" +
                "\n" +
                "CheckIsEmptyPath : There is no path found using 1 movement points.\n";
        gameAction.setSourceCoordinates(24, 19);
        gameAction.setTargetCoordinates(24, 17);

        checkActionInvalidMove();
    }

    @Test
    public void checkActionInvalidAttackCavalry() {
        expectedMessage = "CheckIsInCommunication : This unit is not in communication and cannot be used.\n" +
                "CheckUnitMP : Not enough movement point, the unit has 2 MP, and you need 3 MP\n" +
                "CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        gameAction.setSourceCoordinates(0, 19);
        gameAction.setTargetCoordinates(3, 16);

        checkActionInvalidMove();
    }

    @Test
    public void checkActionInvalidAttackArtillery() {
        expectedMessage = "CheckIsInCommunication : This unit is not in communication and cannot be used.\n" +
                "CheckUnitMP : Not enough movement point, the unit has 1 MP, and you need 2 MP\n" +
                "CheckIsEmptyPath : There is no path found using 1 movement points.\n";
        gameAction.setSourceCoordinates(1, 19);
        gameAction.setTargetCoordinates(1, 17);

        checkActionInvalidMove();
    }

    @Test
    public void checkActionInvalidAttackArtilleryHorse() {
        expectedMessage = "CheckIsInCommunication : This unit is not in communication and cannot be used.\n" +
                "CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        gameAction.setSourceCoordinates(2, 19);
        gameAction.setTargetCoordinates(0, 17);

        checkActionInvalidMove();
    }

    @Test
    public void checkActionInvalidMoveRelay() {
        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(9, 10);
        gameState.addPriorityUnit(unit);

        expectedMessage = "CheckUnitMP : Not enough movement point, the unit has 1 MP, and you need 2 MP\n" +
                "CheckIsPriorityUnit : There are other units that need to be moved first.\n" +
                "CheckIsEmptyPath : There is no path found using 1 movement points.\n";
        gameAction.setSourceCoordinates(0, 8);
        gameAction.setTargetCoordinates(0, 6);

        checkActionInvalidMove();
    }

    @Test
    public void checkActionInvalidMoveRelayHorse() {
        expectedMessage = "CheckUnitMP : Not enough movement point, the unit has 2 MP, and you need 3 MP\n" +
                "CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        gameAction.setSourceCoordinates(3, 7);
        gameAction.setTargetCoordinates(3, 4);

        checkActionInvalidMove();
    }

    @Test
    public void checkActionMoveDestroyArsenal() {
        expectedMessage = "";
        gameAction.setSourceCoordinates(23, 8);
        gameAction.setTargetCoordinates(23, 9);

        checkActionValidMove(EUnitData.RELAY);

        expectedMessage = "";
        gameAction.setSourceCoordinates(24, 8);
        gameAction.setTargetCoordinates(24, 9);

        checkActionValidMove(EUnitData.INFANTRY);

        Building arsenal = null;
        for (Building b : gameState.getAllBuildings()) {
            if (b.getX() == 24 && b.getY() == 9) {
                arsenal = b;
                break;
            }
        }
        assertTrue("Arsenal not broken after being occupied.", arsenal == null);
    }

    @Test
    public void checkActionMoveNotDestroyArsenal() {
        expectedMessage = "";
        gameAction.setSourceCoordinates(23, 8);
        gameAction.setTargetCoordinates(24, 9);

        checkActionValidMove(EUnitData.RELAY);

        Building arsenal = null;
        for (Building b : gameState.getAllBuildings()) {
            if (b.getX() == 24 && b.getY() == 9) {
                arsenal = b;
                break;
            }
        }
        assertTrue("Arsenal broken after being occupied by an inoffensive unit.", arsenal != null);
    }
}