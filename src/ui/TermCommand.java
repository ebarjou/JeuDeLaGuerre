package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

import java.io.*;

import static ui.commands.UserToGameCommand.*;

public class TermCommand implements UserCommand {
    private CommandParser parser;
    private BufferedReader reader;
    private Shell shell;

    public TermCommand(){
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
    }

    private SharedCommand parse(String cmd) throws CLIException{
        if(cmd == null) return new SharedCommand(EXIT);
        shell.processLine(cmd);
        if(parser.getResult().getCommand() == CMD_ERROR) throw new CLIException();
        return parser.getResult();
    }

    @Override
    public SharedCommand getNextCommand() {
        System.out.print("Enter command : ");
        try {
            String cmd = reader.readLine();
            return parse(cmd);
        } catch (IOException e) {
            System.out.println("IO : ");
            return new SharedCommand();
        } catch (Throwable e) {
            System.out.println("CLI : ");
            return new SharedCommand();
        }
    }

    @Override
    public void sendResponse(SharedCommand response) {

    }

    public void setState(){

    }

}
