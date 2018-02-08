package ui;

import asg.cliche.Command;

import static ui.commands.UserToGameCall.*;

public class CommandParser {
    private SharedCommand result;

    protected CommandParser(){
        result = new SharedCommand();
    }

    protected SharedCommand getResult(){
        return result;
    }

    protected int[] parseCoords(String c) throws CommandException {
        int[] coords = new int[2];
        c = c.toLowerCase();
        String c_splitted[] = c.split("(?<=\\D)(?=\\d)");

        if(c_splitted.length != 2) throw new CommandException("Invalide coordinate argument : Must be a letter followed by a number.");
        if(c_splitted[0].length() != 1) throw new CommandException("Invalide coordinate argument : There must be only one letter.");

        coords[0] = Integer.parseInt(c_splitted[1]);
        coords[1] = c_splitted[0].charAt(0) - 'a' + 1;
        return coords;
    }

    @Command
    public void move(String c1, String c2){
        try {
            int[] parsed_c1 = parseCoords(c1);
            int[] parsed_c2 = parseCoords(c2);
            /*
             * TODO: Avoir et vérifier la taille du plateau et envoyer une exception si besoin
             */
            result.setCommand(MOVE);
            result.setCommandCoords1(parsed_c1[0], parsed_c1[1]);
            result.setCommandCoords2(parsed_c2[0], parsed_c2[1]);
        } catch(CommandException e){
            result.setCommand(CMD_ERROR);
            result.setMessage(e.getMessage());
            return;
        }
    }

}
