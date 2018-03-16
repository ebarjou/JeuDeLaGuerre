package ui;

import asg.cliche.Command;
import ruleEngine.EGameActionType;

import static ui.commands.UserToGameCall.*;

public class CommandParser {
    private UIAction result;

    CommandParser() {
        result = new UIAction(CMD_ERROR, null);
    }

    /**
     * @return the UIAction generated from the last Cliche parsing.
     */
    UIAction getResult() {
        return result;
    }

    /**
     * Clear the previous result of Cliche parse.
     */
    void clearResult() {
        result.setCommand(CMD_ERROR);
    }

    /**
     * @param c String containing letters then numbers
     * @return  int[], [0] being the convertion from b26 to b10 of the letters, and [1] the number from the string.
     * @throws Exception if the string was not correct.
     */
    private int[] parseCoords(String c) throws Exception {
        int[] coords = new int[2];
        c = c.toLowerCase();
        if (!c.matches("([a-z]+)([0-9]+)"))
            throw new Exception("Invalid coordinate argument : Must be letters followed by a number.");
        String c_splitted[] = c.split("(?<=\\D)(?=\\d)");
        coords[0] = Integer.parseInt(c_splitted[1]) - 1;
        coords[1] = IntLetterConverter.getIntFromLetters(c_splitted[0]);
        return coords;
    }

    @Command
    public void move(String c1, String c2) {
        try {
            int[] parsed_c1 = parseCoords(c1);
            int[] parsed_c2 = parseCoords(c2);

            result.setCommand(GAME_ACTION);
            result.setGameAction(EGameActionType.MOVE);
            result.getGameAction().setSourceCoordinates(parsed_c1[0], parsed_c1[1]);
            result.getGameAction().setTargetCoordinates(parsed_c2[0], parsed_c2[1]);
        } catch (Exception e) {
            result.setCommand(CMD_ERROR);
            result.setErrorMessage(e.getMessage());
        }
    }

    @Command
    public void attack(String c1, String c2) {
        try {
            int[] parsed_c1 = parseCoords(c1);
            int[] parsed_c2 = parseCoords(c2);

            result.setCommand(GAME_ACTION);
            result.setGameAction(EGameActionType.ATTACK);
            result.getGameAction().setSourceCoordinates(parsed_c1[0], parsed_c1[1]);
            result.getGameAction().setTargetCoordinates(parsed_c2[0], parsed_c2[1]);
        } catch (Exception e) {
            result.setCommand(CMD_ERROR);
            result.setErrorMessage(e.getMessage());
        }
    }

    @Command
    public void revert() {
        result.setCommand(REVERT);
    }

    @Command
    public void end() {
        result.setCommand(GAME_ACTION);
        result.setGameAction(EGameActionType.END_TURN);
    }

    @Command
    public void load(String path) {
        result.setCommand(LOAD);
        result.setText(path);
        result.setGameAction(EGameActionType.COMMUNICATION);
    }

    @Command
    public void save(String path){
        result.setCommand(SAVE);
        result.setText(path);
    }

    @Command
    public void exit() {
        result.setCommand(EXIT);
    }
}
