package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;

import org.junit.Test;
import ruleEngine.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;

import static org.junit.Assert.*;

public class MoveRulesTest {

    private GameState gameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private RuleChecker rule;
    private String expectedMessage;
//    private LoadFile lf;

    //TODO: Find how to load files ?

    @Before
    public void setUp() {
        gameState = new GameState(25, 20);
        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        ruleResult = new RuleResult();
        rule = new RuleChecker();
        expectedMessage = "";
//        gameState = Game.getInstance().getGameState();
//        lf = new LoadFile();
//        try {
//            lf.loadFile("debord.txt");
//        } catch (IOException e) {
//            assertTrue("Test class " + this.getClass().getSimpleName() +
//                    " could not load the test file : Test interrupted.", false);
//        }


        Building building = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_NORTH);
        building.setPosition(0, 0);
        gameState.addBuilding(building);
        // Ensure the VictoryRules don't activate
        building = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_SOUTH);
        building.setPosition(24, 19);
        gameState.addBuilding(building);
        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_SOUTH);
        unit.setPosition(24, 19);
        gameState.addUnit(unit);
        try {
            GameAction communication = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
            RuleResult r = rule.checkAction(gameState, communication);
            assertTrue("Can't check actions MOVE because action COMMUNICATION failed beforehand.", r.isValid());
        } catch (IncorrectGameActionException e) {
            assertTrue("Can't check actions MOVE because action COMMUNICATION failed beforehand.", false);
        }

    }

    private void checkActionValidMove(Unit unit) {
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
        assertTrue(movedUnit.getPlayer() == unit.getPlayer());
        assertTrue(movedUnit.getUnitData() == unit.getUnitData());
    }

    @Test
    public void checkActionValidMoveInfantry() {
        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 1);

        checkActionValidMove(unit);
    }

    @Test
    public void checkActionValidMoveCavalry() {
        Unit unit = new Unit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 2);

        checkActionValidMove(unit);
    }

    @Test
    public void checkActionValidMoveArtillery() {
        Unit unit = new Unit(EUnitData.ARTILLERY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 1);

        checkActionValidMove(unit);
    }

    @Test
    public void checkActionValidMoveArtilleryHorse() {
        Unit unit = new Unit(EUnitData.ARTILLERY_HORSE, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 2);

        checkActionValidMove(unit);
    }

    @Test
    public void checkActionValidMoveRelay() {
        Unit unit = new Unit(EUnitData.RELAY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 1);

        checkActionValidMove(unit);
    }

    @Test
    public void checkActionValidMoveRelayHorse() {
        Unit unit = new Unit(EUnitData.RELAY_HORSE, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 2);

        checkActionValidMove(unit);
    }

    // TODO : All InvalidMove tests

    @Test
    public void checkActionMoveDestroyArsenal() {
        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);
        Building building = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_SOUTH);
        building.setPosition(0, 1);
        gameState.addBuilding(building);
        assertTrue("Arsenal broken right after being initiated.", !building.isBroken());

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 1);

        checkActionValidMove(unit);

        Building arsenal = null;
        for (Building b : gameState.getAllBuildings()) {
            if (b.getX() == building.getX() && b.getY() == building.getY()) {
                arsenal = b;
                break;
            }
        }
        assertTrue("Arsenal not broken after being occupied.", arsenal == null);
    }

    @Test
    public void checkActionMoveNotDestroyArsenal() {
        Unit unit = new Unit(EUnitData.RELAY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);
        Building building = new Building(EBuildingData.ARSENAL, EPlayer.PLAYER_SOUTH);
        building.setPosition(0, 1);
        gameState.addBuilding(building);
        assertTrue("Arsenal broken right after being initiated.", !building.isBroken());

        expectedMessage = "";
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(0, 1);

        checkActionValidMove(unit);

        Building arsenal = null;
        for (Building b : gameState.getAllBuildings()) {
            if (b.getX() == building.getX() && b.getY() == building.getY()) {
                arsenal = b;
                break;
            }
        }
        assertTrue("Arsenal broken after being occupied by an inoffensive unit.", arsenal != null);
    }
}