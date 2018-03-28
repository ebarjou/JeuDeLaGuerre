package player;

import analyse.EMetricsMoveType;
import analyse.InfoModule;
import analyse.MoveWrapper;
import game.EPlayer;
import game.Game;
import game.gameState.GameState;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ui.GameResponse;
import ui.UIAction;
import ui.commands.UserToGameCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BotPlayer implements Player {
    private UIAction action;
    private GameResponse response;
    private Object wait_command;
    private Object wait_response;

    public BotPlayer() {
        action = null;
        response = null;
        wait_command = new Object();
        wait_response = new Object();
    }

    private UIAction createCommand() {
        List<GameAction> actionList = new ArrayList<>();
        GameState state = Game.getInstance().getGameState();
        Collection<MoveWrapper> moves = InfoModule.getAvailableMoves(EMetricsMoveType.ALL_AVAILABLE_MOVES, state, state.getActualPlayer());
        for (MoveWrapper moveWrapper : moves) {
            GameAction ga = new GameAction(state.getActualPlayer(), EGameActionType.MOVE);
            ga.setSourceCoordinates(moveWrapper.getSourceCoordinates().getX(), moveWrapper.getSourceCoordinates().getY());
            ga.setTargetCoordinates(moveWrapper.getTargetCoordinates().getX(), moveWrapper.getTargetCoordinates().getY());
            actionList.add(ga);
        }

        if (actionList.size() == 0)
            return new UIAction(UserToGameCall.GAME_ACTION, new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.END_TURN));
        else {
            GameAction action = actionList.get((int) (Math.random() * actionList.size()));
            return new UIAction(UserToGameCall.GAME_ACTION, action);
        }
    }

    @Override
    public UIAction getCommand() {
        createCommand();
        UIAction output;
        synchronized (wait_command) {
            while (action == null) {
                try {
                    wait_command.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        output = action;
        action = null;
        return output;
    }

    @Override
    public void setCommand(UIAction action) {
        if (action.getCommand() == UserToGameCall.CMD_ERROR)
            this.action = createCommand();
        else
            this.action = action;

        synchronized (wait_command) {
            wait_command.notifyAll();
        }
    }

    @Override
    public GameResponse getResponse() {
        GameResponse output;
        synchronized (wait_response) {
            while (response == null) {
                try {
                    wait_response.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        output = response;
        response = null;
        return output;
    }

    @Override
    public void setResponse(GameResponse response) {
        this.response = response;
        synchronized (wait_response) {
            wait_response.notifyAll();
        }
    }
}
