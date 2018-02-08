package ui;

import asg.cliche.CLIException;
import asg.cliche.Command;

import static ui.commands.UserToGameCommand.*;

public class CommandParser {
    private SharedCommand command;

    public CommandParser(){
        command = new SharedCommand();
    }

    public SharedCommand getCommand(){
        return command;
    }

    public int[] parseCoords(String c) throws CLIException{
        int[] coords = new int[2];
        c = c.toLowerCase();
        String c_splitted[] = c.split("(?<=\\D)(?=\\d)");

        if(c_splitted.length != 2) throw new CLIException("Invalide coordinate arg.");
        if(c_splitted[0].length() != 1) throw new CLIException("Invalide coordinate arg.");

        coords[0] = Integer.parseInt(c_splitted[1]);
        coords[1] = c_splitted[0].charAt(0) - 'a' + 1;
        return coords;
    }

    @Command
    public void move(String c1, String c2) throws CLIException{

        int[] parsed_c1 = parseCoords(c1);
        int[] parsed_c2 = parseCoords(c2);
        /*
         * TODO: Avoir et vérifier la taille du plateau et envoyer une exception si besoin
         */
        command.setCommand(MOVE);
        command.setCommandCoords1(parsed_c1[0], parsed_c1[1]);
        command.setCommandCoords2(parsed_c2[0], parsed_c2[1]);
    }

}
