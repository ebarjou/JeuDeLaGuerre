package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;
import ruleEngine.exceptions.IncorrectGameActionException;
import system.LoadFile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        ruleResult = new RuleResult();
        ruleMove = new MoveRules();
        ruleAttack = new AttackRules();
        ruleChecker = new RuleChecker();

        Player player = mock(Player.class);
        Game.init(player, player);
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/attackRulesTestFile.txt");

        gameState = Game.getInstance().getGameState();
        ruleChecker.checkAndApplyAction(gameState, gameAction);
    }

    @Test
    public void checkActionRealCorrectInfantryAttackInfantry() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (12, 8) has been attacked, but nothing happened : Attack:4 Defense:12.\n";
        gameAction.setSourceCoordinates(10, 8);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryBetweenCavalryAttackInfantry() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 10);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (12, 10) has been attacked, but nothing happened : Attack:8 Defense:12.\n";
        gameAction.setSourceCoordinates(10, 10);
        gameAction.setTargetCoordinates(12, 10);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryAttackArtilleryFort() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(9, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (7, 8) has been attacked, but nothing happened : Attack:13 Defense:24.\n";
        gameAction.setSourceCoordinates(9, 8);
        gameAction.setTargetCoordinates(7, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryAttackRelayFort() {
        performAssertsCorrectEndTurn();

        gameAction.setSourceCoordinates(12, 8);
        gameAction.setTargetCoordinates(12, 7);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (11, 7) has been attacked, but nothing happened : Attack:8 Defense:8.\n";
        gameAction.setSourceCoordinates(12, 7);
        gameAction.setTargetCoordinates(11, 7);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryAttackCavalryInCommunication() {
        gameAction.setSourceCoordinates(10, 7);
        gameAction.setTargetCoordinates(9, 7);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (9, 5) has been attacked, but nothing happened : Attack:4 Defense:18.\n";
        gameAction.setSourceCoordinates(9, 7);
        gameAction.setTargetCoordinates(9, 5);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryAttackCavalryNoCommunication() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        gameAction.setSourceCoordinates(10, 7);
        gameAction.setTargetCoordinates(10, 6);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (9, 5) died : Attack:4 Defense:0.\n";
        gameAction.setSourceCoordinates(10, 6);
        gameAction.setTargetCoordinates(9, 5);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryAttackCantAttackAlly() {
        Unit unit = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_SOUTH);
        unit.setPosition(12, 1);
        gameState.addPriorityUnit(unit);

        performAssertsCorrectEndTurn();

        gameAction.setSourceCoordinates(12, 1);
        gameAction.setTargetCoordinates(12, 0);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        gameAction.setSourceCoordinates(14, 0);
        gameAction.setTargetCoordinates(13, 0);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (11, 0) has been attacked, but nothing happened : Attack:4 Defense:5.\n";
        gameAction.setSourceCoordinates(13, 0);
        gameAction.setTargetCoordinates(11, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectInfantryAttackInfantryPass() {
        gameAction.setSourceCoordinates(16, 7);
        gameAction.setTargetCoordinates(16, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (15, 8) died : Attack:12 Defense:8.\n";
        gameAction.setSourceCoordinates(16, 8);
        gameAction.setTargetCoordinates(15, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryAttackInfantry() {
        gameAction.setSourceCoordinates(9, 10);
        gameAction.setTargetCoordinates(10, 10);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (12, 8) has been attacked, but nothing happened : Attack:4 Defense:12.\n";
        gameAction.setSourceCoordinates(10, 10);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectChargeAttackInfantry() {
        performAssertsCorrectEndTurn();

        gameAction.setSourceCoordinates(9, 5);
        gameAction.setTargetCoordinates(10, 6);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (10, 7) has been attacked, but nothing happened : Attack:7 Defense:13.\n";
        gameAction.setSourceCoordinates(10, 6);
        gameAction.setTargetCoordinates(10, 7);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectChargeDestroyInfantry() {
        gameAction.setSourceCoordinates(9, 10);
        gameAction.setTargetCoordinates(10, 10);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (12, 10) died : Attack:14 Defense:12.\n";
        gameAction.setSourceCoordinates(10, 10);
        gameAction.setTargetCoordinates(12, 10);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCharge3DestroyRelayHorse() {
        gameAction.setSourceCoordinates(6, 6);
        gameAction.setTargetCoordinates(5, 6);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (3, 8) died : Attack:21 Defense:13.\n";
        gameAction.setSourceCoordinates(5, 6);
        gameAction.setTargetCoordinates(3, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryDestroyRelayHorseFort() {
        performAssertsCorrectEndTurn();

        gameAction.setSourceCoordinates(9, 5);
        gameAction.setTargetCoordinates(10, 6);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (11, 5) died : Attack:4 Defense:2.\n";
        gameAction.setSourceCoordinates(10, 6);
        gameAction.setTargetCoordinates(11, 5);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryAttackArtilleryHorseFort() {
        gameAction.setSourceCoordinates(6, 6);
        gameAction.setTargetCoordinates(6, 7);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 8) has been attacked, but nothing happened : Attack:9 Defense:24.\n";
        gameAction.setSourceCoordinates(6, 7);
        gameAction.setTargetCoordinates(6, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryAttackArtilleryHorseFort2() {
        gameAction.setSourceCoordinates(6, 5);
        gameAction.setTargetCoordinates(6, 7);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 8) has been attacked, but nothing happened : Attack:13 Defense:24.\n";
        gameAction.setSourceCoordinates(6, 7);
        gameAction.setTargetCoordinates(6, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryDestroyArtilleryHorseFort3() {
        gameAction.setSourceCoordinates(4, 7);
        gameAction.setTargetCoordinates(6, 7);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 8) has been attacked, but nothing happened : Attack:13 Defense:24.\n";
        gameAction.setSourceCoordinates(6, 7);
        gameAction.setTargetCoordinates(6, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryRetreatInfantry() {
        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(3, 1);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (3, 2) will have to move in South Player's next turn : Attack:7 Defense:6.\n";
        gameAction.setSourceCoordinates(3, 1);
        gameAction.setTargetCoordinates(3, 2);

        performAssertsCorrectAttack();

        boolean hasToRetreat = false;
        for (Unit pUnit : gameState.getPriorityUnits()) {
            if (pUnit.getX() == gameAction.getTargetCoordinates().getX() &&
                    pUnit.getY() == gameAction.getTargetCoordinates().getY()) {
                hasToRetreat = true;
            }
        }
        assertTrue(hasToRetreat);
    }

    @Test
    public void checkActionRealCorrectCavalryDestroySurroundedInfantry() {
        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(3, 1);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (3, 0) died : Attack:7 Defense:6.\n";
        gameAction.setSourceCoordinates(3, 1);
        gameAction.setTargetCoordinates(3, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryCanChargeInfantry() {
        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(4, 2);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 0) died : Attack:14 Defense:6.\n";
        gameAction.setSourceCoordinates(4, 2);
        gameAction.setTargetCoordinates(6, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryCantChargeInfantryPass() {
        Building building = new Building(EBuildingProperty.PASS, EPlayer.PLAYER_NORTH);
        building.setPosition(6, 0);
        gameState.addBuilding(building);

        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(4, 2);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 0) has been attacked, but nothing happened : Attack:8 Defense:8.\n";
        gameAction.setSourceCoordinates(4, 2);
        gameAction.setTargetCoordinates(6, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryCantChargeInfantryFort() {
        Building building = new Building(EBuildingProperty.FORTRESS, EPlayer.PLAYER_NORTH);
        building.setPosition(6, 0);
        gameState.addBuilding(building);

        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(4, 2);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 0) has been attacked, but nothing happened : Attack:8 Defense:10.\n";
        gameAction.setSourceCoordinates(4, 2);
        gameAction.setTargetCoordinates(6, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryCantChargeFromFort1() {
        Building building = new Building(EBuildingProperty.FORTRESS, EPlayer.PLAYER_NORTH);
        building.setPosition(5, 1);
        gameState.addBuilding(building);

        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(4, 2);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 0) died : Attack:8 Defense:6.\n";
        gameAction.setSourceCoordinates(4, 2);
        gameAction.setTargetCoordinates(6, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryCantCharge2InfantryPass() {
        Building building = new Building(EBuildingProperty.FORTRESS, EPlayer.PLAYER_NORTH);
        building.setPosition(4, 2);
        gameState.addBuilding(building);

        gameAction.setSourceCoordinates(5, 3);
        gameAction.setTargetCoordinates(4, 2);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (6, 0) died : Attack:8 Defense:6.\n";
        gameAction.setSourceCoordinates(4, 2);
        gameAction.setTargetCoordinates(6, 0);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectCavalryAttackInfantryFort() {
        gameAction.setSourceCoordinates(15, 5);
        gameAction.setTargetCoordinates(16, 6);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        performAssertsCorrectEndTurn();
        performAssertsCorrectEndTurn();

        gameAction.setSourceCoordinates(16, 6);
        gameAction.setTargetCoordinates(15, 5);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "AttackRules : The unit at position (16, 4) has been attacked, but nothing happened : Attack:4 Defense:10.\n";
        gameAction.setSourceCoordinates(15, 5);
        gameAction.setTargetCoordinates(16, 4);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectArtilleryAttackInfantry() {
        gameAction.setSourceCoordinates(10, 11);
        gameAction.setTargetCoordinates(9, 11);

        performAssertsCorrectMove(EUnitProperty.ARTILLERY);

        expectedMessage = "AttackRules : The unit at position (12, 8) has been attacked, but nothing happened : Attack:5 Defense:12.\n";
        gameAction.setSourceCoordinates(9, 11);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealCorrectArtilleryHorseAttackArtilleryHorseFort() {
        // Also tests if ArtilleryHorse can charge (should not)
        gameAction.setSourceCoordinates(5, 7);
        gameAction.setTargetCoordinates(6, 7);

        performAssertsCorrectMove(EUnitProperty.ARTILLERY_HORSE);

        expectedMessage = "AttackRules : The unit at position (6, 8) has been attacked, but nothing happened : Attack:9 Defense:24.\n";
        gameAction.setSourceCoordinates(6, 7);
        gameAction.setTargetCoordinates(6, 8);

        performAssertsCorrectAttack();
    }

    @Test
    public void checkActionRealWrongSourceEmpty() {
        expectedMessage = "CheckIsAllyUnit : There is no unit at (10, 8).\n" +
                "CheckLastMove, CheckUnitRange, CheckIsCharge, CheckCanAttackUnit, CheckIsRelay : Those rules are not checked because CheckIsAllyUnit has failed.\n";
        gameAction.setSourceCoordinates(10, 8);
        gameAction.setTargetCoordinates(7, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongSourceEnemy() {
        expectedMessage = "CheckIsAllyUnit : This unit is not owned by North Player.\n" +
                "CheckLastMove, CheckUnitRange, CheckIsCharge, CheckCanAttackUnit, CheckIsRelay : Those rules are not checked because CheckIsAllyUnit has failed.\n" +
                "CheckIsInCommunication : This unit is not in the player communication.\n";
        gameAction.setSourceCoordinates(12, 8);
        gameAction.setTargetCoordinates(7, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongTargetEmpty() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckIsEnemyUnit : There is no unit at (11;8)\n" +
                "CheckAreAligned : Those rules are not checked because CheckIsEnemyUnit has failed.\n" +
                "CheckIsEmptyAttackPath : Those rules are not checked because CheckIsEnemyUnit has failed.\n";
        gameAction.setSourceCoordinates(10, 8);
        gameAction.setTargetCoordinates(11, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongTargetAlly() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckIsEnemyUnit : Targeted unit is not an enemy.\n" +
                "CheckAreAligned : Those rules are not checked because CheckIsEnemyUnit has failed.\n" +
                "CheckIsEmptyAttackPath : Those rules are not checked because CheckIsEnemyUnit has failed.\n";
        gameAction.setSourceCoordinates(10, 8);
        gameAction.setTargetCoordinates(10, 7);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongNoMove() {
        expectedMessage = "CheckLastMove : No unit has been moved yet.\n";
        gameAction.setSourceCoordinates(16, 7);
        gameAction.setTargetCoordinates(15, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongAllyMove() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckLastMove : Unit is not the last one moved.\n";
        gameAction.setSourceCoordinates(16, 7);
        gameAction.setTargetCoordinates(15, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongAllyDoubleAttack() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(11, 9);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "AttackRules : The unit at position (12, 8) has been attacked, but nothing happened : Attack:4 Defense:12.\n";
        gameAction.setSourceCoordinates(11, 9);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsCorrectAttack();

        ruleResult = new RuleResult();
        expectedMessage = "CheckLastMove : No unit has been moved yet.\n";
        gameAction.setSourceCoordinates(11, 9);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongInfantryRange() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(10, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "RuleCompositeOR : CheckUnitRange : Not enough range to attack, the unit has a range of 2, and you need a range of 3.\n" +
                "CheckIsCharge : The initiating unit is not able to charge.\n\n";
        gameAction.setSourceCoordinates(10, 8);
        gameAction.setTargetCoordinates(7, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongInfantryAligned() {
        gameAction.setSourceCoordinates(10, 9);
        gameAction.setTargetCoordinates(9, 9);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n" +
                "CheckIsEmptyAttackPath : Those rules are not checked because CheckAreAligned has failed.\n";
        gameAction.setSourceCoordinates(9, 9);
        gameAction.setTargetCoordinates(7, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongInfantryCommunication() {
        gameAction.setSourceCoordinates(16, 7);
        gameAction.setTargetCoordinates(17, 8);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckIsInCommunication : This unit is not in the player communication.\n";
        gameAction.setSourceCoordinates(17, 8);
        gameAction.setTargetCoordinates(15, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongInfantryPath() {
        gameAction.setSourceCoordinates(16, 7);
        gameAction.setTargetCoordinates(16, 6);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckIsEmptyAttackPath : This unit cannot attack here because there is an obstacle.\n";
        gameAction.setSourceCoordinates(16, 6);
        gameAction.setTargetCoordinates(16, 4);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongInfantryCantAttack() {
        Unit unit = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_SOUTH);
        unit.setPosition(12, 1);
        gameState.addPriorityUnit(unit);

        performAssertsCorrectEndTurn();

        gameAction.setSourceCoordinates(12, 1);
        gameAction.setTargetCoordinates(12, 0);

        performAssertsCorrectMove(EUnitProperty.INFANTRY);

        expectedMessage = "CheckCanAttackUnit : This unit can't attack this turn.\n";
        gameAction.setSourceCoordinates(12, 0);
        gameAction.setTargetCoordinates(11, 0);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongCavalryRange() {
        gameAction.setSourceCoordinates(9, 10);
        gameAction.setTargetCoordinates(9, 8);

        performAssertsCorrectMove(EUnitProperty.CAVALRY);

        expectedMessage = "RuleCompositeOR : CheckUnitRange : Not enough range to attack, the unit has a range of 2, and you need a range of 3.\n" +
                "CheckIsCharge : The initiating unit is not in a position to proceed a charge.\n\n";
        gameAction.setSourceCoordinates(9, 8);
        gameAction.setTargetCoordinates(12, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongArtilleryRange() {
        gameAction.setSourceCoordinates(10, 11);
        gameAction.setTargetCoordinates(11, 12);

        performAssertsCorrectMove(EUnitProperty.ARTILLERY);

        expectedMessage = "RuleCompositeOR : CheckUnitRange : Not enough range to attack, the unit has a range of 3, and you need a range of 4.\n" +
                "CheckIsCharge : The initiating unit is not able to charge.\n\n";
        gameAction.setSourceCoordinates(11, 12);
        gameAction.setTargetCoordinates(7, 8);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongArtilleryHorseRangeAndCom() {
        gameAction.setSourceCoordinates(5, 7);
        gameAction.setTargetCoordinates(5, 5);

        performAssertsCorrectMove(EUnitProperty.ARTILLERY_HORSE);

        expectedMessage = "RuleCompositeOR : CheckUnitRange : Not enough range to attack, the unit has a range of 3, and you need a range of 4.\n" +
                "CheckIsCharge : The initiating unit is not able to charge.\n\n" +
                "CheckIsInCommunication : This unit is not in the player communication.\n";
        gameAction.setSourceCoordinates(5, 5);
        gameAction.setTargetCoordinates(9, 5);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongRelayAttack() {
        gameAction.setSourceCoordinates(11, 7);
        gameAction.setTargetCoordinates(10, 6);

        performAssertsCorrectMove(EUnitProperty.RELAY);

        expectedMessage = "CheckIsRelay : Expected false, but returned true.\n";
        gameAction.setSourceCoordinates(10, 6);
        gameAction.setTargetCoordinates(9, 5);

        performAssertsWrongAttack();
    }

    @Test
    public void checkActionRealWrongRelayHorseAttack() {
        gameAction.setSourceCoordinates(11, 5);
        gameAction.setTargetCoordinates(10, 6);

        performAssertsCorrectMove(EUnitProperty.RELAY_HORSE);

        expectedMessage = "CheckIsRelay : Expected false, but returned true.\n";
        gameAction.setSourceCoordinates(10, 6);
        gameAction.setTargetCoordinates(9, 5);

        performAssertsWrongAttack();
    }

    private void performAssertsCorrectMove(EUnitProperty unitData) {
        gameAction.setActionType(EGameActionType.MOVE);
        assertTrue(ruleMove.checkAction(gameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
        ruleMove.applyResult(gameState, gameAction, ruleResult);

        gameAction.setActionType(EGameActionType.COMMUNICATION);
        try {
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
        assertTrue(ruleAttack.checkAction(gameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        ruleAttack.applyResult(gameState, gameAction, ruleResult);
        if (!ruleResult.getLogMessage().equals(expectedMessage))
            System.out.print(ruleResult.getLogMessage());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        gameAction.setActionType(EGameActionType.COMMUNICATION);
        try {
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

    private void performAssertsCorrectEndTurn() {
        gameAction.setActionType(EGameActionType.END_TURN);
        try {
            ruleChecker.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue(false);
        }
        assertTrue(gameAction.getPlayer() != gameState.getActualPlayer());
        gameAction.setPlayer(gameState.getActualPlayer());
    }
}