package ui;

import asg.cliche.Command;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.math.BigInteger;
import java.util.regex.Pattern;

import static ui.commands.UserToGameCall.*;

public class CommandParser {
    private SharedCommand result;

    protected CommandParser(){
        result = new SharedCommand();
    }

    protected SharedCommand getResult(){
        return result;
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
        coords[0] = Integer.parseInt(c_splitted[1]);
        coords[1] = getIntFromString(c_splitted[0]);
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
