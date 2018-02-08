package ui;

import ui.commands.CommandException;
import ui.commands.GameToUserCommand;
import ui.commands.UserToGameCommand;

import static ui.commands.GameToUserCommand.*;
import static ui.commands.UserToGameCommand.*;

public class SharedCommand {
    private UserToGameCommand command;
    private int[] commandCoords1, commandCoords2;

    private GameToUserCommand response;

    public SharedCommand(){
        this.command = CMD_ERROR;
        this.response = GAME_ERROR;
        commandCoords1 = new int[2];
        commandCoords2 = new int[2];
    }

    /*
     * ### For Game ###
     */

    public UserToGameCommand getCommand() {
        return command;
    }

    public int[] commandGetCoords1() throws CommandException {
        if(command == null || !command.haveCoords1()) throw new CommandException();
        return commandCoords1;
    }

    public int[] commandGetCoords2() throws CommandException{
        if(command == null || !command.haveCoords2()) throw new CommandException();
        return commandCoords2;
    }

    public void setResponse(GameToUserCommand response){
        this.response = response;
    }

    /*
     * ### For User ###
     */

    public GameToUserCommand getResponse() {
        return response;
    }

    public void setCommand(UserToGameCommand command){
        this.command = command;
    }

    public void setCommandCoords1(int x, int y){
        commandCoords1[0] = x;
        commandCoords1[1] = y;
    }

    public void setCommandCoords2(int x, int y){
        commandCoords2[0] = x;
        commandCoords2[1] = y;
    }
}
