package ui;

import ruleEngine.GameAction;
import ui.commands.GameToUserCall;
import ui.commands.UserToGameCall;

import static ui.commands.GameToUserCall.*;
import static ui.commands.UserToGameCall.*;

public class SharedCommand {
    private GameToUserCall response;
    private UserToGameCall command;
    private int[] commandCoords1, commandCoords2;
    private String message;
    private int unit;



    protected SharedCommand(){
        this.command = CMD_ERROR;
        this.response = GAME_ERROR;
        commandCoords1 = new int[2];
        commandCoords2 = new int[2];
    }

    protected SharedCommand(UserToGameCall cmd){
        this.command = cmd;
        this.response = GAME_ERROR;
        commandCoords1 = new int[2];
        commandCoords2 = new int[2];
    }

    protected SharedCommand(Exception e){
        this.command = CMD_ERROR;
        this.response = GAME_ERROR;
        commandCoords1 = new int[2];
        commandCoords2 = new int[2];
        message = e.getMessage();
    }

    public SharedCommand(GameToUserCall cmd){
        this.command = CMD_ERROR;
        this.response = cmd;
        commandCoords1 = new int[2];
        commandCoords2 = new int[2];
    }

    /*
     * ### For Game ###
     */

    public UserToGameCall getCommand() {
        return command;
    }

    public int[] getCoords1() throws CommandException {
        if(command == null || !command.haveCoords1()) throw new CommandException("This command does not have a Coords1 attached.");
        return commandCoords1;
    }

    public int[] getCoords2() throws CommandException{
        if(command == null || !command.haveCoords2()) throw new CommandException("This command does not have a Coords2 attached.");
        return commandCoords2;
    }

    public String getString() throws CommandException{
        if(command == null || !command.haveString()) throw new CommandException("This command does not have a String attached.");
        return message;
    }

    public int getUnit() throws CommandException{
        if(command == null || !command.haveUnit()) throw new CommandException("This command does not have a Unit attached.");
        return unit;
    }

    public void setResponse(GameToUserCall response){
        this.response = response;
    }

    /*
     * ### For User ###
     */

    protected GameToUserCall getResponse() {
        return response;
    }

    protected void setCommand(UserToGameCall command){
        this.command = command;
    }

    protected void setCommandCoords1(int x, int y){
        commandCoords1[0] = x;
        commandCoords1[1] = y;
    }

    protected void setCommandCoords2(int x, int y){
        commandCoords2[0] = x;
        commandCoords2[1] = y;
    }

    protected void setMessage(String message){
        this.message = message;
    }

    protected void setUnit(int unit){
        this.unit = unit;
    }
}
