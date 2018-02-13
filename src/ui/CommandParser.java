package ui;

import asg.cliche.Command;
import ruleEngine.EGameActionType;

import static ui.commands.UserToGameCall.*;

public class CommandParser {
    private UIAction result;

    protected CommandParser(){
        result = new UIAction(CMD_ERROR, null);
    }

    protected UIAction getResult(){
        return result;
    }

    protected void newCommand(){
        this.result = new UIAction(CMD_ERROR, null);
    }

    int getIntFromString(String s){
        s = s.toLowerCase();
        int res = 0;
        int base = 26;
        int stage = 0;
        for(int i = s.length() - 1; i >= 0; --i){
            char c = s.charAt(i);
            res += (Math.pow(base, stage) * ((c - 'a') + 1));
            stage++;
        }
        return res;
    }

    protected int[] parseCoords(String c) throws CommandException {
        int[] coords = new int[2];
        c = c.toLowerCase();
        if(!c.matches("([a-z]+)([0-9]+)")) throw new CommandException("Invalid coordinate argument : Must be letters followed by a number.");
        String c_splitted[] = c.split("(?<=\\D)(?=\\d)");
        coords[0] = Integer.parseInt(c_splitted[1])-1;
        coords[1] = getIntFromString(c_splitted[0])-1;
        return coords;
    }

    @Command
    public void move(String c1, String c2){
        try {
            int[] parsed_c1 = parseCoords(c1);
            int[] parsed_c2 = parseCoords(c2);

            result.setCommand(GAME_ACTION);
            result.setGameAction(EGameActionType.MOVE);
            result.getGameAction().setSourceCoordinates(parsed_c1[0], parsed_c1[1]);
            result.getGameAction().setTargetCoordinates(parsed_c2[0], parsed_c2[1]);
        } catch(CommandException e){
            result.setCommand(CMD_ERROR);
            result.setErrorMessage(e.getMessage());
            return;
        }
    }

    @Command
    public void revert(){
        result.setCommand(REVERT);
    }

    @Command
    public void exit(){
        result.setCommand(EXIT);
    }
}
