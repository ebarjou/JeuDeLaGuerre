package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import ui.commands.UserToGameCommand;

import java.io.*;

public class TermCommand implements UserCommand {
    private CommandParser parser;
    private BufferedReader reader;
    private Shell shell;

    public TermCommand(){
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
    }

    @Override
    public SharedCommand getNextCommand() {
        System.out.print("Enter command : ");
        try {
            String cmd = reader.readLine();
            shell.processLine(cmd);
            return parser.getCommand();
        } catch (IOException e) {
            System.out.println("IO : " + e.toString());
            return new SharedCommand();
        } catch (CLIException e) {
            System.out.println("CLI : " + e.toString());
            return new SharedCommand();
        }
    }

    @Override
    public void sendResponse(SharedCommand response) {

    }

    public void setState(){

    }

}
