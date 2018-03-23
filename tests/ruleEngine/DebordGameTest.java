package ruleEngine;

import game.EPlayer;
import game.Game;
import game.gameState.GameState;
import org.junit.Before;

import org.junit.Test;
import player.GUIPlayer;
import player.Player;
import ruleEngine.exceptions.IncorrectGameActionException;
import system.BadFileFormatException;
import system.LoadFile;
import ui.GUIThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DebordGameTest {

    private GameState gameState;
    private List<GameAction> actions;
    private GameAction gameAction;
    private RuleChecker rule;
    private int turn, actionNb;

    @Before
    public void setUp() throws Exception {
        turn = 1;
        actionNb = 0;
        actions = new ArrayList<>();
        gameAction = null;
        rule = new RuleChecker();

        Player player = mock(Player.class);
        Game.init(player, player);
        LoadFile lf = new LoadFile();
        lf.loadFile("presets/debord.txt");

        gameState = Game.getInstance().getGameState();
        gameState.setActualPlayer(EPlayer.PLAYER_NORTH);

        GameAction communication = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.COMMUNICATION);
        RuleResult r = rule.checkAndApplyAction(gameState, communication);
        assertTrue("Can't check the actions of DebordGameTest because action COMMUNICATION failed beforehand.", r.isValid());
    }

    private void checkActionValid(int turn, int actionNb) {
        RuleResult ruleResult = new RuleResult();
        ruleResult.invalidate();

        try {
            ruleResult = rule.checkAndApplyAction(gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action MOVE wasn't recognized by the RuleChecker.", false);
        }
        assertTrue(this.getClass().getSimpleName() + " failed at turn " + turn + ", action " + actionNb + ".\n" +
                "Log Message : " + ruleResult.getLogMessage(), ruleResult.isValid());
    }

    @Test
    public void checkActionValidActions1() {
        generateListOfActions1();
        int turn = 1;
        int actionNb = 0;
        for (GameAction ga : actions) {
            gameAction = ga;
            checkActionValid(turn, ++actionNb);
            if (gameAction.getActionType() == EGameActionType.END_TURN) {
                actionNb = 0;
                ++turn;
            }
        }
        saveEnd();
    }

    private void checkActionValid(GameAction gameAction){
        this.gameAction = gameAction;
        checkActionValid(turn, ++actionNb);
        if (this.gameAction.getActionType() == EGameActionType.END_TURN) {
            saveEnd("debordGameTurns/Turn" + ((turn % 2 != 0 ) ? (turn + 1) / 2 : (turn + 1) / 2 + "'") + ".txt");
            actionNb = 0;
            ++turn;
        }
    }

    private void addAction(EPlayer player, EGameActionType gameActionType) {
        GameAction gameAction = new GameAction(player, gameActionType);
        //actions.add(gameAction);
        checkActionValid(gameAction);
    }

    private void addAction(EPlayer player, EGameActionType gameActionType, int srcX, char srcY, int targetX, char targetY) {
        if (srcX < 1 || srcY < 'A' || srcY > 'Z'
            || targetX < 1 || targetY < 'A' || targetY > 'Z') {
            assertTrue("Illegal coordinates for action.", false);
        }
        int valSrcX = srcX - 1;
        int valSrcY = srcY - 'A';
        int valTargetX = targetX - 1;
        int valTargetY = targetY - 'A';
        GameAction gameAction = new GameAction(player, gameActionType);
        gameAction.setSourceCoordinates(valSrcX, valSrcY);
        gameAction.setTargetCoordinates(valTargetX, valTargetY);
        //actions.add(gameAction);
        checkActionValid(gameAction);

    }

    private void saveEnd() {
        LoadFile lf = new LoadFile();
        try {
            lf.save("presets/debordGameTestEnd.txt", gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEnd(String filename) {
        LoadFile lf = new LoadFile();
        try {
            lf.save(filename, gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void generateListOfActions1() {
        EPlayer player;

        // Turn 1-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 4, 'H', 4, 'J'); // C
        addAction(player, EGameActionType.MOVE, 3, 'H', 3, 'J'); // C
        addAction(player, EGameActionType.MOVE, 4, 'G', 5, 'I'); // C
        addAction(player, EGameActionType.MOVE, 3, 'G', 4, 'I'); // C
        addAction(player, EGameActionType.MOVE, 6, 'I', 6, 'J'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 1-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 19, 'K', 17, 'J'); // C
        addAction(player, EGameActionType.MOVE, 15, 'L', 14, 'K'); // I
        addAction(player, EGameActionType.MOVE, 15, 'M', 14, 'L'); // I
        addAction(player, EGameActionType.MOVE, 16, 'M', 15, 'M'); // I
        addAction(player, EGameActionType.MOVE, 17, 'N', 15, 'L'); // Tc
        addAction(player, EGameActionType.END_TURN);

        // Turn 2-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 3, 'J', 3, 'L'); // C
        addAction(player, EGameActionType.MOVE, 10, 'F', 11, 'F'); // I
        addAction(player, EGameActionType.MOVE, 9, 'G', 10, 'F'); // I
        addAction(player, EGameActionType.MOVE, 8, 'G', 9, 'F'); // A
        addAction(player, EGameActionType.MOVE, 7, 'G', 8, 'G'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 2-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 17, 'J', 15, 'H'); // C
        addAction(player, EGameActionType.MOVE, 14, 'K', 13, 'J'); // I
        addAction(player, EGameActionType.MOVE, 14, 'L', 14, 'K'); // I
        addAction(player, EGameActionType.MOVE, 16, 'K', 15, 'J'); // I
        addAction(player, EGameActionType.MOVE, 18, 'K', 17, 'J'); // A
        addAction(player, EGameActionType.END_TURN);

        // Turn 3-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 3, 'L', 3, 'N'); // C
        addAction(player, EGameActionType.MOVE, 6, 'H', 8, 'F'); // Ac
        addAction(player, EGameActionType.MOVE, 10, 'F', 11, 'E'); // I
        addAction(player, EGameActionType.MOVE, 9, 'F', 10, 'F'); // I
        addAction(player, EGameActionType.MOVE, 8, 'G', 9, 'F'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 3-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 13, 'J', 13, 'I'); // I
        addAction(player, EGameActionType.MOVE, 15, 'J', 14, 'I'); // I
        addAction(player, EGameActionType.MOVE, 17, 'J', 16, 'I'); // A
        addAction(player, EGameActionType.MOVE, 19, 'L', 17, 'J'); // C
        addAction(player, EGameActionType.MOVE, 18, 'M', 17, 'O'); // C
        addAction(player, EGameActionType.END_TURN);

        // Turn 4-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 3, 'N', 3, 'P'); // C
        addAction(player, EGameActionType.MOVE, 5, 'I', 7, 'G'); // C
        addAction(player, EGameActionType.MOVE, 4, 'J', 6, 'H'); // C
        addAction(player, EGameActionType.MOVE, 4, 'I', 5, 'G'); // C
        addAction(player, EGameActionType.MOVE, 9, 'H', 9, 'G'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 4-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 15, 'H', 16, 'F'); // C
        addAction(player, EGameActionType.MOVE, 14, 'I', 14, 'H'); // I
        addAction(player, EGameActionType.MOVE, 16, 'I', 16, 'H'); // A
        addAction(player, EGameActionType.MOVE, 17, 'J', 15, 'H'); // C
        addAction(player, EGameActionType.MOVE, 14, 'K', 14, 'J'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 5-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 3, 'P', 3, 'R'); // C
        addAction(player, EGameActionType.MOVE, 11, 'F', 12, 'E'); // I
        addAction(player, EGameActionType.MOVE, 10, 'F', 11, 'F'); // A
        addAction(player, EGameActionType.MOVE, 8, 'F', 10, 'F'); // Ac
        addAction(player, EGameActionType.MOVE, 7, 'G', 9, 'E'); // C
        addAction(player, EGameActionType.END_TURN);

        // Turn 5-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 16, 'H', 15, 'G'); // A
        addAction(player, EGameActionType.MOVE, 16, 'F', 17, 'D'); // C
        addAction(player, EGameActionType.MOVE, 15, 'H', 16, 'F'); // C
        addAction(player, EGameActionType.MOVE, 15, 'L', 15, 'J'); // Tc
        addAction(player, EGameActionType.MOVE, 17, 'K', 16, 'J'); // I
        addAction(player, EGameActionType.END_TURN);


        //In the book, a horseman detroys an arsenal, so we check if there is an arsenal before, and after the north turn.
        assertTrue(gameState.isBuilding(2, 19));

        // Turn 6-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 3, 'R', 3, 'T'); // C
        addAction(player, EGameActionType.MOVE, 10, 'F', 12, 'F'); // Ac
        addAction(player, EGameActionType.MOVE, 9, 'F', 10, 'F'); // I
        addAction(player, EGameActionType.MOVE, 5, 'G', 7, 'F'); // C
        addAction(player, EGameActionType.MOVE, 6, 'H', 8, 'G'); // C
        addAction(player, EGameActionType.END_TURN);

        assertTrue(!gameState.isBuilding(2, 19));

        // Turn 6-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 16, 'F', 16, 'D'); // C
        addAction(player, EGameActionType.MOVE, 16, 'J', 15, 'I'); // I
        addAction(player, EGameActionType.MOVE, 15, 'J', 16, 'H'); // Tc
        addAction(player, EGameActionType.MOVE, 15, 'G', 16, 'F'); // A
        addAction(player, EGameActionType.MOVE, 18, 'L', 16, 'J'); // C
        addAction(player, EGameActionType.END_TURN);

        // Turn 7-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 3, 'T', 3, 'R'); // C
        addAction(player, EGameActionType.MOVE, 11, 'E', 12, 'D'); // I
        addAction(player, EGameActionType.MOVE, 10, 'F', 11, 'E'); // I
        addAction(player, EGameActionType.MOVE, 8, 'G', 10, 'F'); // C
        addAction(player, EGameActionType.MOVE, 7, 'F', 9, 'F'); // C
        addAction(player, EGameActionType.END_TURN);

        // Turn 7-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 16, 'J', 15, 'H'); // C
        addAction(player, EGameActionType.MOVE, 14, 'H', 15, 'G'); // I
        addAction(player, EGameActionType.MOVE, 15, 'I', 14, 'H'); // I
        addAction(player, EGameActionType.MOVE, 14, 'J', 15, 'I'); // I
        addAction(player, EGameActionType.MOVE, 15, 'M', 15, 'L'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 8-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 12, 'D', 13, 'D'); // I
        addAction(player, EGameActionType.MOVE, 11, 'E', 11, 'D'); // I
        addAction(player, EGameActionType.MOVE, 10, 'F', 12, 'D'); // C
        addAction(player, EGameActionType.MOVE, 9, 'F', 11, 'E'); // C
        addAction(player, EGameActionType.MOVE, 9, 'E', 10, 'F'); // C
        addAction(player, EGameActionType.END_TURN);

        // Turn 8-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 17, 'L', 17, 'K'); // I
        addAction(player, EGameActionType.MOVE, 15, 'K', 14, 'J'); // I
        addAction(player, EGameActionType.MOVE, 15, 'L', 15, 'K'); // I
        addAction(player, EGameActionType.MOVE, 16, 'F', 17, 'E'); // A
        addAction(player, EGameActionType.MOVE, 15, 'H', 16, 'F'); // C
        addAction(player, EGameActionType.END_TURN);

        // Turn 9-North
        player = EPlayer.PLAYER_NORTH;
        addAction(player, EGameActionType.MOVE, 12, 'F', 13, 'F'); // Ac
        addAction(player, EGameActionType.MOVE, 10, 'F', 12, 'F'); // C
        addAction(player, EGameActionType.MOVE, 9, 'G', 10, 'F'); // I
        addAction(player, EGameActionType.MOVE, 8, 'H', 9, 'G'); // I
        addAction(player, EGameActionType.MOVE, 7, 'H', 8, 'H'); // I
        addAction(player, EGameActionType.END_TURN);

        // Turn 9-South
        player = EPlayer.PLAYER_SOUTH;
        addAction(player, EGameActionType.MOVE, 16, 'D', 18, 'D'); // C
        //addAction(player, EGameActionType.MOVE, 16, 'J', 15, 'I'); // I
        addAction(player, EGameActionType.END_TURN);
    }
}