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
import system.LoadFile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class MoveRulesTest {

    private GameState gameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private String expectedMessage;
    private MoveRules rule;
	private RuleChecker ruleChecker;

	@Before
    public void setUp() throws Exception{
        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        ruleResult = new RuleResult();
        rule = new MoveRules();
        ruleChecker = new RuleChecker();

        Player player = mock(Player.class);
        Game.init(player, player);
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/moveRulesTestFile.txt");

        gameState = Game.getInstance().getGameState();
		ruleChecker.checkAndApplyAction(gameState, new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));
	}

    @Test
    public void checkActionRealCorrectInfantry() {
        gameAction.setSourceCoordinates(9, 10);
        gameAction.setTargetCoordinates(10, 10);

        performAssertsCorrect(EUnitData.INFANTRY);
    }

    @Test
    public void checkActionRealCorrectCavalry() {
        gameAction.setSourceCoordinates(9, 9);
        gameAction.setTargetCoordinates(11, 11);

        performAssertsCorrect(EUnitData.CAVALRY);
    }

    @Test
    public void checkActionRealCorrectArtillery() {
        gameAction.setSourceCoordinates(9, 11);
        gameAction.setTargetCoordinates(10, 11);

        performAssertsCorrect(EUnitData.ARTILLERY);
    }

    @Test
    public void checkActionRealCorrectArtilleryHorse() {
        gameAction.setSourceCoordinates(9, 12);
        gameAction.setTargetCoordinates(11, 14);

        performAssertsCorrect(EUnitData.ARTILLERY_HORSE);
    }

    @Test
    public void checkActionRealCorrectRelay() {
        gameAction.setSourceCoordinates(0, 8);
        gameAction.setTargetCoordinates(0, 7);

        performAssertsCorrect(EUnitData.RELAY);
    }

    @Test
    public void checkActionRealCorrectRelayHorse() {
        gameAction.setSourceCoordinates(3, 7);
        gameAction.setTargetCoordinates(3, 5);

        performAssertsCorrect(EUnitData.RELAY_HORSE);
    }

    @Test
    public void checkActionRealWrongInfantry() {
        expectedMessage = "CheckIsAllyUnit : This unit is not owned by North Player.\n" +
                "CheckIsInCommunication, CheckIsRelay, , CheckUnitMP, CheckIsEmptyPath, CheckIsPriorityUnit, CheckCanMoveUnit,  : Those rules are not checked because CheckIsAllyUnit has failed.\n";
        gameAction.setSourceCoordinates(24, 19);
        gameAction.setTargetCoordinates(24, 17);

        performAssertsWrong();
    }

    @Test
    public void checkActionRealWrongCavalry() {
        expectedMessage = "CheckIsInCommunication : This unit is not in the player communication.\n" +
				"CheckIsRelay : This unit is not a relay.\n" +
				"CheckUnitMP : Not enough movement point, the unit has 2 MP, and you need 3 MP.\n" +
				"CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        gameAction.setSourceCoordinates(0, 19);
        gameAction.setTargetCoordinates(3, 16);

        performAssertsWrong();
    }

    @Test
    public void checkActionRealWrongArtillery() {
        expectedMessage = "CheckIsInCommunication : This unit is not in the player communication.\n" +
                "CheckIsRelay : This unit is not a relay.\n" +
                "CheckUnitMP : Not enough movement point, the unit has 1 MP, and you need 2 MP.\n" +
                "CheckIsEmptyPath : There is no path found using 1 movement points.\n";
        gameAction.setSourceCoordinates(1, 19);
        gameAction.setTargetCoordinates(1, 17);

        performAssertsWrong();
    }

    @Test
    public void checkActionRealWrongArtilleryHorse() {
        expectedMessage = "CheckIsInCommunication : This unit is not in the player communication.\n" +
				"CheckIsRelay : This unit is not a relay.\n" +
				"CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        gameAction.setSourceCoordinates(2, 19);
		gameAction.setTargetCoordinates(0, 17);

		performAssertsWrong();
	}

    @Test
    public void checkActionRealWrongRelay() {
        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(9, 10);
        gameState.addPriorityUnit(unit);

        expectedMessage = "CheckUnitMP : Not enough movement point, the unit has 1 MP, and you need 2 MP.\n" +
				"CheckIsEmptyPath : There is no path found using 1 movement points.\n" +
				"CheckIsPriorityUnit : There are other units that need to be moved first.\n";
        gameAction.setSourceCoordinates(0, 8);
        gameAction.setTargetCoordinates(0, 6);

        performAssertsWrong();
    }

    @Test
    public void checkActionRealWrongRelayHorse() {
        expectedMessage = "CheckUnitMP : Not enough movement point, the unit has 2 MP, and you need 3 MP.\n" +
                "CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        gameAction.setSourceCoordinates(3, 7);
        gameAction.setTargetCoordinates(3, 4);

        performAssertsWrong();
    }

    @Test
    public void checkActionRealCorrectDestroyedArsenal() {
        gameAction.setSourceCoordinates(23, 8);
        gameAction.setTargetCoordinates(23, 9);

        performAssertsCorrect(EUnitData.RELAY);
        gameAction.setSourceCoordinates(24, 8);
        gameAction.setTargetCoordinates(24, 9);

        performAssertsCorrect(EUnitData.INFANTRY);

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
    public void checkActionRealCorrectNotDestroyedArsenal() {
        gameAction.setSourceCoordinates(23, 8);
        gameAction.setTargetCoordinates(24, 9);

        performAssertsCorrect(EUnitData.RELAY);

        Building arsenal = null;
        for (Building b : gameState.getAllBuildings()) {
            if (b.getX() == 24 && b.getY() == 9) {
                arsenal = b;
                break;
            }
        }
        assertTrue("Arsenal broken after being occupied by an inoffensive unit.", arsenal != null);
    }

	private void performAssertsCorrect(EUnitData unitData) {
		assertTrue(rule.checkAction(gameState, gameAction, ruleResult));
		assertTrue(ruleResult.isValid());
		rule.applyResult(gameState, gameAction, ruleResult);
		try {
			ruleChecker.checkAndApplyAction(gameState, new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));
		} catch (IncorrectGameActionException e) {
			assertTrue(false);
		}
		Unit movedUnit = null;
        try {
            movedUnit = gameState.getLastUnitMoved();
        } catch (NullPointerException e) {
            for (Unit unit : gameState.getAllUnits()) {
                Coordinates coord = gameAction.getTargetCoordinates();
                if (unit.getX() == coord.getX() && unit.getY() == coord.getY()) {
                    movedUnit = unit;
                    break;
                }
            }
        }
        assertNotNull(movedUnit);
		assertTrue(movedUnit.getX() == gameAction.getTargetCoordinates().getX());
		assertTrue(movedUnit.getY() == gameAction.getTargetCoordinates().getY());
		assertTrue(movedUnit.getPlayer() == gameState.getActualPlayer());
		assertTrue(movedUnit.getUnitData() == unitData);
	}

	private void performAssertsWrong() {
		assertFalse(rule.checkAction(gameState, gameAction, ruleResult));
		assertFalse(ruleResult.isValid());
		if (!ruleResult.getLogMessage().equals(expectedMessage)) {
            System.out.print(ruleResult.getLogMessage());
        }
		assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
	}

}