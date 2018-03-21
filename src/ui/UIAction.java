package ui;

import game.EPlayer;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ui.commands.UserToGameCall;

public class UIAction {
    private UserToGameCall command;
    private GameAction gameAction;
    private String error_message;
    private String text;

    UIAction(String error_message) {
        this.command = UserToGameCall.CMD_ERROR;
        this.gameAction = new GameAction(null, null);
        this.text = null;
    }

    public UIAction(UserToGameCall command, EGameActionType action) {
        this.command = command;
        this.gameAction = new GameAction(null, action);
        this.text = null;
    }

    public UIAction(UserToGameCall command, GameAction action) {
        this.command = command;
        this.gameAction = action;
        this.text = null;
    }

    public UserToGameCall getCommand() {
        return command;
    }

    void setCommand(UserToGameCall command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getErrorMessage() {
        return error_message;
    }

    void setErrorMessage(String message) {
        this.error_message = message;
    }

    public GameAction getGameAction(EPlayer player) {
        gameAction.setPlayer(player);
        return gameAction;
    }

    GameAction getGameAction() {
        return gameAction;
    }

    void setGameAction(EGameActionType action) {
        gameAction.setActionType(action);
    }
}
