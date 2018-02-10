package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.board.Board;
import javafx.application.Application;
import ui.display.BoardCanvas;
import ui.display.BoardWindow;

import java.io.*;

import static ui.commands.UserToGameCall.*;

public class TermUI implements UserInterface {
    private CommandParser parser;
    private BufferedReader reader;
    private Shell shell;

    public TermUI(){
        //TODO : A mettre en forme, pas très propre
        new Thread(() -> {
            Application.launch(BoardWindow.class, null);
            System.exit(0);
        }).start();
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
        System.out.println("> ?list to display all available commands.");
    }

    private SharedCommand parse(String cmd) throws CLIException, CommandException{
        if(cmd == null) return new SharedCommand(EXIT);
        shell.processLine(cmd);
        if(parser.getResult().getCommand() == CMD_ERROR) throw new CommandException(parser.getResult().getString());
        return parser.getResult();
    }

    @Override
    public SharedCommand getNextCommand() {
        System.out.print("Enter command : ");
        parser.newCommand();
        try {
            String cmd = reader.readLine();
            return this.parse(cmd);
        } catch (IOException e) {
            return new SharedCommand(e);
        } catch (CommandException|CLIException e){
            if(e.getMessage() != null) System.out.println(e.getMessage());
            return new SharedCommand(e);
        }
    }

    @Override
    public void sendResponse(SharedCommand response, Board board) {
        switch (response.getResponse()){
            case VALID:{
                System.out.println("Valid !");
                break;
            }
            case INVALID:{
                System.out.println("Invalid !");
                break;
            }
            case APPLIED:{
                break;
            }
            case REFRESH:{
                break;
            }
            case GAME_ERROR:{
                //Do we need to handle game error ?
                break;
            }
        }
        BoardWindow.updateBoard(board);
    }

}
