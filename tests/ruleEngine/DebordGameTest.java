package ruleEngine;

import game.EPlayer;
import game.Game;
import game.gameState.GameState;
import org.junit.Before;

import org.junit.Test;
import player.Player;
import ruleEngine.exceptions.IncorrectGameActionException;
import system.LoadFile;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DebordGameTest {

    private GameState gameState;
    private RuleChecker rule;
    private int turn, actionNb;
    private EPlayer actualPlayer;

    @Before
    public void setUp() {
        rule = new RuleChecker();

        Player player = mock(Player.class);
        Game.init(player, player);
    }

    private void loadTurn(String file, int turn) throws Exception {
        LoadFile lf = new LoadFile();
        lf.loadFile(file);

        gameState = Game.getInstance().getGameState();
        actualPlayer = gameState.getActualPlayer();

        this.turn = turn;
        actionNb = 5 - gameState.getActionLeft();
        if (actionNb < 0)   actionNb = 0;

        GameAction communication = new GameAction(actualPlayer, EGameActionType.COMMUNICATION);
        RuleResult r = rule.checkAndApplyAction(gameState, communication);
        assertTrue("Can't check the actions of DebordGameTest because action COMMUNICATION failed beforehand.", r.isValid());
    }

    private void checkActionValid(EGameActionType gameActionType) {
        GameAction gameAction = new GameAction(actualPlayer, gameActionType);

        checkActionValid(gameAction);
    }

    private void checkActionValid(EGameActionType gameActionType, int srcX, char srcY, int targetX, char targetY) {
        if (srcX < 1 || srcY < 'A' || srcY > 'Z'
                || targetX < 1 || targetY < 'A' || targetY > 'Z') {
            assertTrue("Illegal coordinates for action.", false);
        }
        int valSrcX = srcX - 1;
        int valSrcY = srcY - 'A';
        int valTargetX = targetX - 1;
        int valTargetY = targetY - 'A';

        GameAction gameAction = new GameAction(actualPlayer, gameActionType);
        gameAction.setSourceCoordinates(valSrcX, valSrcY);
        gameAction.setTargetCoordinates(valTargetX, valTargetY);

        checkActionValid(gameAction);
    }

    private void checkActionValid(GameAction gameAction){
        ++actionNb;

        RuleResult ruleResult = new RuleResult();
        ruleResult.invalidate();
        try {
            ruleResult = rule.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action " + gameAction.getActionType() + " wasn't recognized by the RuleChecker.", false);
        }
        assertTrue(this.getClass().getSimpleName() + " failed at turn " + ((turn + 1) / 2) + ((turn % 2 != 0 ) ? "" : "bis" ) + ", action " + actionNb + ".\n" +
                "Log Message : " + ruleResult.getLogMessage(), ruleResult.isValid());

        if (gameAction.getActionType() == EGameActionType.END_TURN) {
            saveGame("debordGameTurns/Turn" + ((turn + 1) / 2) + ((turn % 2 != 0 ) ? "" : "bis" ) + ".txt");
            actionNb = 0;
            ++turn;
            actualPlayer = gameState.getActualPlayer();
        }
    }

    private void saveGame(String filename) {
        LoadFile lf = new LoadFile();
        try {
            lf.save(filename, gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTurns1To8() throws Exception {
        loadTurn("presets/debord.txt", 1);

        // Turn 1-North
        checkActionValid(EGameActionType.MOVE, 4, 'H', 4, 'J'); // C
        checkActionValid(EGameActionType.MOVE, 3, 'H', 3, 'J'); // C
        checkActionValid(EGameActionType.MOVE, 4, 'G', 5, 'I'); // C
        checkActionValid(EGameActionType.MOVE, 3, 'G', 4, 'I'); // C
        checkActionValid(EGameActionType.MOVE, 6, 'I', 6, 'J'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 1-South
        checkActionValid(EGameActionType.MOVE, 19, 'K', 17, 'J'); // C
        checkActionValid(EGameActionType.MOVE, 15, 'L', 14, 'K'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'M', 14, 'L'); // I
        checkActionValid(EGameActionType.MOVE, 16, 'M', 15, 'M'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'N', 15, 'L'); // Tc
        checkActionValid(EGameActionType.END_TURN);

        // Turn 2-North
        checkActionValid(EGameActionType.MOVE, 3, 'J', 3, 'L'); // C
        checkActionValid(EGameActionType.MOVE, 10, 'F', 11, 'F'); // I
        checkActionValid(EGameActionType.MOVE, 9, 'G', 10, 'F'); // I
        checkActionValid(EGameActionType.MOVE, 8, 'G', 9, 'F'); // A
        checkActionValid(EGameActionType.MOVE, 7, 'G', 8, 'G'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 2-South
        checkActionValid(EGameActionType.MOVE, 17, 'J', 15, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'K', 13, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'L', 14, 'K'); // I
        checkActionValid(EGameActionType.MOVE, 16, 'K', 15, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 18, 'K', 17, 'J'); // A
        checkActionValid(EGameActionType.END_TURN);

        // Turn 3-North
        checkActionValid(EGameActionType.MOVE, 3, 'L', 3, 'N'); // C
        checkActionValid(EGameActionType.MOVE, 6, 'H', 8, 'F'); // Ac
        checkActionValid(EGameActionType.MOVE, 10, 'F', 11, 'E'); // I
        checkActionValid(EGameActionType.MOVE, 9, 'F', 10, 'F'); // I
        checkActionValid(EGameActionType.MOVE, 8, 'G', 9, 'F'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 3-South
        checkActionValid(EGameActionType.MOVE, 13, 'J', 13, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'J', 14, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'J', 16, 'I'); // A
        checkActionValid(EGameActionType.MOVE, 19, 'L', 17, 'J'); // C
        checkActionValid(EGameActionType.MOVE, 18, 'M', 17, 'O'); // C
        checkActionValid(EGameActionType.END_TURN);

        // Turn 4-North
        checkActionValid(EGameActionType.MOVE, 3, 'N', 3, 'P'); // C
        checkActionValid(EGameActionType.MOVE, 5, 'I', 7, 'G'); // C
        checkActionValid(EGameActionType.MOVE, 4, 'J', 6, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 4, 'I', 5, 'G'); // C
        checkActionValid(EGameActionType.MOVE, 9, 'H', 9, 'G'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 4-South
        checkActionValid(EGameActionType.MOVE, 15, 'H', 16, 'F'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'I', 14, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 16, 'I', 16, 'H'); // A
        checkActionValid(EGameActionType.MOVE, 17, 'J', 15, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'K', 14, 'J'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 5-North
        checkActionValid(EGameActionType.MOVE, 3, 'P', 3, 'R'); // C
        checkActionValid(EGameActionType.MOVE, 11, 'F', 12, 'E'); // I
        checkActionValid(EGameActionType.MOVE, 10, 'F', 11, 'F'); // A
        checkActionValid(EGameActionType.MOVE, 8, 'F', 10, 'F'); // Ac
        checkActionValid(EGameActionType.MOVE, 7, 'G', 9, 'E'); // C
        checkActionValid(EGameActionType.END_TURN);

        // Turn 5-South
        checkActionValid(EGameActionType.MOVE, 16, 'H', 15, 'G'); // A
        checkActionValid(EGameActionType.MOVE, 16, 'F', 17, 'D'); // C
        checkActionValid(EGameActionType.MOVE, 15, 'H', 16, 'F'); // C
        checkActionValid(EGameActionType.MOVE, 15, 'L', 15, 'J'); // Tc
        checkActionValid(EGameActionType.MOVE, 17, 'K', 16, 'J'); // I
        checkActionValid(EGameActionType.END_TURN);


        //In the book, a cavalry destroys an arsenal, so we check if there is an arsenal before, and after the north turn.
        assertTrue(gameState.isBuilding(2, 19));

        // Turn 6-North
        checkActionValid(EGameActionType.MOVE, 3, 'R', 3, 'T'); // C
        checkActionValid(EGameActionType.MOVE, 10, 'F', 12, 'F'); // Ac
        checkActionValid(EGameActionType.MOVE, 9, 'F', 10, 'F'); // I
        checkActionValid(EGameActionType.MOVE, 5, 'G', 7, 'F'); // C
        checkActionValid(EGameActionType.MOVE, 6, 'H', 8, 'G'); // C
        checkActionValid(EGameActionType.END_TURN);

        assertTrue(!gameState.isBuilding(2, 19));

        // Turn 6-South
        checkActionValid(EGameActionType.MOVE, 16, 'F', 16, 'D'); // C
        checkActionValid(EGameActionType.MOVE, 16, 'J', 15, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'J', 16, 'H'); // Tc
        checkActionValid(EGameActionType.MOVE, 15, 'G', 16, 'F'); // A
        checkActionValid(EGameActionType.MOVE, 18, 'L', 16, 'J'); // C
        checkActionValid(EGameActionType.END_TURN);

        // Turn 7-North
        checkActionValid(EGameActionType.MOVE, 3, 'T', 3, 'R'); // C
        checkActionValid(EGameActionType.MOVE, 11, 'E', 12, 'D'); // I
        checkActionValid(EGameActionType.MOVE, 10, 'F', 11, 'E'); // I
        checkActionValid(EGameActionType.MOVE, 8, 'G', 10, 'F'); // C
        checkActionValid(EGameActionType.MOVE, 7, 'F', 9, 'F'); // C
        checkActionValid(EGameActionType.END_TURN);

        // Turn 7-South
        checkActionValid(EGameActionType.MOVE, 16, 'J', 15, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'H', 15, 'G'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'I', 14, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'J', 15, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'M', 15, 'L'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 8-North
        checkActionValid(EGameActionType.MOVE, 12, 'D', 13, 'D'); // I
        checkActionValid(EGameActionType.MOVE, 11, 'E', 11, 'D'); // I
        checkActionValid(EGameActionType.MOVE, 10, 'F', 12, 'D'); // C
        checkActionValid(EGameActionType.MOVE, 9, 'F', 11, 'E'); // C
        checkActionValid(EGameActionType.MOVE, 9, 'E', 10, 'F'); // C
        checkActionValid(EGameActionType.END_TURN);

        // Turn 8-South
        checkActionValid(EGameActionType.MOVE, 17, 'L', 17, 'K'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'K', 14, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'L', 15, 'K'); // I
        checkActionValid(EGameActionType.MOVE, 16, 'F', 17, 'E'); // A
        checkActionValid(EGameActionType.MOVE, 15, 'H', 16, 'F'); // C
        checkActionValid(EGameActionType.END_TURN);
    }

    @Test
    public void testTurns42To48() throws Exception {
        loadTurn("presets/debordTurn41bis.txt", 83);

        // Turn 42-North
        checkActionValid(EGameActionType.MOVE, 16, 'G', 15, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 19, 'G', 18, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 18, 'F', 16, 'G'); // C
        checkActionValid(EGameActionType.MOVE, 20, 'F', 18, 'G'); // C
        checkActionValid(EGameActionType.MOVE, 19, 'F', 17, 'F'); // C
        checkActionValid(EGameActionType.END_TURN);

        // Turn 42-South
        checkActionValid(EGameActionType.MOVE, 13, 'J', 11, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 12, 'J', 12, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 13, 'I', 12, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'H', 13, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'I', 14, 'I'); // I
        // Check the result of the attack
        assertTrue(gameState.isUnit(11, 6));
        checkActionValid(EGameActionType.ATTACK, 14, 'I', 12, 'G'); // I to I
        assertTrue(!gameState.isUnit(11, 6));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 43-North
        checkActionValid(EGameActionType.MOVE, 14, 'F', 13, 'F'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'H', 16, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'G', 16, 'E'); // Ac
        checkActionValid(EGameActionType.MOVE, 16, 'G', 14, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 17, 'F', 15, 'H'); // C
        // Check the result of the attack
        assertTrue(gameState.isUnit(12, 7));
        checkActionValid(EGameActionType.ATTACK, 15, 'H', 13, 'H'); // C to I
        assertTrue(!gameState.isUnit(12, 7));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 43-South
        checkActionValid(EGameActionType.MOVE, 12, 'H', 13, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 11, 'H', 12, 'J'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'I', 13, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'K', 16, 'L'); // Ac
        checkActionValid(EGameActionType.MOVE, 18, 'K', 17, 'K'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 44-North
        checkActionValid(EGameActionType.MOVE, 16, 'E', 14, 'F'); // Ac
        checkActionValid(EGameActionType.MOVE, 18, 'G', 16, 'G'); // C
        checkActionValid(EGameActionType.MOVE, 13, 'F', 12, 'G'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'G', 13, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'F', 14, 'G'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 44-South
        checkActionValid(EGameActionType.MOVE, 12, 'J', 13, 'K'); // C
        checkActionValid(EGameActionType.MOVE, 12, 'I', 12, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 19, 'K', 19, 'I'); // C
        checkActionValid(EGameActionType.MOVE, 18, 'L', 19, 'K'); // A
        checkActionValid(EGameActionType.MOVE, 20, 'L', 20, 'J'); // C
        // Check the result of the attack
        assertTrue(gameState.isUnit(17, 7));
        checkActionValid(EGameActionType.ATTACK, 20, 'J', 18, 'H'); // I to I
        assertTrue(!gameState.isUnit(17, 7));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 45-North
        checkActionValid(EGameActionType.MOVE, 13, 'H', 12, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 13, 'G', 13, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'F', 13, 'G'); // Ac
        checkActionValid(EGameActionType.MOVE, 16, 'I', 15, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'H', 14, 'I'); // C
        // Check the result of the attack
        assertTrue(gameState.isUnit(12, 9));
        checkActionValid(EGameActionType.ATTACK, 14, 'I', 13, 'J'); // C to I
        assertTrue(!gameState.isUnit(12, 9));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 45-South
        checkActionValid(EGameActionType.MOVE, 15, 'L', 17, 'M'); // Tc
        checkActionValid(EGameActionType.MOVE, 15, 'K', 15, 'L'); // I
        checkActionValid(EGameActionType.MOVE, 13, 'K', 15, 'K'); // C
        checkActionValid(EGameActionType.MOVE, 12, 'J', 13, 'K'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'N', 18, 'O'); // T
        checkActionValid(EGameActionType.END_TURN);

        // Turn 46-North
        checkActionValid(EGameActionType.MOVE, 12, 'G', 12, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'H', 15, 'I'); // C
        checkActionValid(EGameActionType.MOVE, 16, 'G', 14, 'H'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'G', 15, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'H', 16, 'I'); // A
        // Check the result of the attack
        assertTrue(gameState.isUnit(15, 8));
        checkActionValid(EGameActionType.ATTACK, 16, 'I', 13, 'I'); // A to I
        assertTrue(!gameState.isUnit(12, 8));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 46-South
        checkActionValid(EGameActionType.MOVE, 15, 'L', 15, 'M'); // I
        checkActionValid(EGameActionType.MOVE, 16, 'L', 15, 'L'); // Ac
        checkActionValid(EGameActionType.MOVE, 15, 'K', 16, 'L'); // C
        checkActionValid(EGameActionType.MOVE, 13, 'K', 14, 'L'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'J', 15, 'K'); // I
        checkActionValid(EGameActionType.END_TURN);

        // Turn 47-North
        checkActionValid(EGameActionType.MOVE, 12, 'H', 13, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'J', 14, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 16, 'H', 17, 'I'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'H', 16, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 14, 'I', 15, 'J'); // C
        // Check the result of the attack
        assertTrue(gameState.isUnit(15, 9));
        checkActionValid(EGameActionType.ATTACK, 15, 'J', 16, 'J'); // C to I
        assertTrue(!gameState.isUnit(15, 9));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 47-South
        checkActionValid(EGameActionType.MOVE, 15, 'L', 17, 'L'); // Ac
        checkActionValid(EGameActionType.MOVE, 16, 'L', 17, 'J'); // C
        checkActionValid(EGameActionType.MOVE, 14, 'L', 15, 'L'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'M', 16, 'L'); // I
        checkActionValid(EGameActionType.MOVE, 20, 'J', 18, 'I'); // C
        // Check the result of the attack
        assertTrue(gameState.isUnit(16, 8));
        checkActionValid(EGameActionType.ATTACK, 18, 'I', 17, 'I'); // C to I
        assertTrue(!gameState.isUnit(16, 8));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 48-North
        checkActionValid(EGameActionType.MOVE, 16, 'I', 17, 'H'); // A
        checkActionValid(EGameActionType.MOVE, 14, 'H', 16, 'I'); // C
        checkActionValid(EGameActionType.MOVE, 13, 'G', 14, 'I'); // Ac
        checkActionValid(EGameActionType.MOVE, 13, 'H', 14, 'H'); // I
        checkActionValid(EGameActionType.MOVE, 15, 'J', 17, 'I'); // C
        // Check the result of the attack
        assertTrue(gameState.isUnit(17, 8));
        checkActionValid(EGameActionType.ATTACK, 17, 'I', 18, 'I'); // C to C
        assertTrue(!gameState.isUnit(17, 8));
        checkActionValid(EGameActionType.END_TURN);

        // Turn 48-South
        checkActionValid(EGameActionType.MOVE, 15, 'K', 16, 'K'); // I
        checkActionValid(EGameActionType.MOVE, 17, 'M', 19, 'N'); // Tc
        checkActionValid(EGameActionType.MOVE, 19, 'K', 19, 'L'); // A
        checkActionValid(EGameActionType.MOVE, 18, 'J', 19, 'J'); // I
        checkActionValid(EGameActionType.MOVE, 19, 'I', 18, 'K'); // C
        checkActionValid(EGameActionType.END_TURN);

    }
}