package ui;

import game.EPlayer;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ui.commands.GameToUserCall;
import ui.commands.UserToGameCall;

import static ui.commands.GameToUserCall.*;
import static ui.commands.UserToGameCall.*;

public class UIAction {
    private UserToGameCall command;
    private GameAction gameAction;
    private String error_message;

    protected UIAction(String error_message){
        this.command = command;
        this.gameAction = new GameAction(null, null);
    }

    protected UIAction(UserToGameCall command, EGameActionType action){
        this.command = command;
        this.gameAction = new GameAction(null, action);
    }

    protected void setGameAction(EGameActionType action){
        gameAction.setActionType(action);
    }

    public GameAction getGameAction(EPlayer player){
        gameAction.setPlayer(player);
        return gameAction;
    }

    protected GameAction getGameAction(){
        return gameAction;
    }

    protected void setCommand(UserToGameCall command){
        this.command = command;
    }

    public UserToGameCall getCommand(){
        return command;
    }

    public String getErrorMessage() {
        return error_message;
    }

    public void setErrorMessage(String message) {
        this.error_message = message;
    }
}
