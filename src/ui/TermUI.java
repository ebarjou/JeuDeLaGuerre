package ui;

import asg.cliche.CLIException;
import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

import java.io.*;

import static ui.commands.UserToGameCall.*;

public class TermUI implements UserInterface {
    private CommandParser parser;
    private BufferedReader reader;
    private Shell shell;

    public TermUI(){
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
    }

    private SharedCommand parse(String cmd) throws CLIException{
        if(cmd == null) return new SharedCommand(EXIT);
        shell.processLine(cmd);
        return parser.getResult();
    }

    @Override
    public SharedCommand getNextCommand() {
        System.out.print("Enter command : ");
        try {
            String cmd = reader.readLine();
            return parse(cmd);
        } catch (IOException|CLIException e) {
            return new SharedCommand(e);
        }
    }

    @Override
    public void sendResponse(SharedCommand response, CurrentGameState state) {

    }

}
