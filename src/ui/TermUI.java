package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import game.board.Board;
import game.board.BoardManager;
import javafx.application.Application;
import ui.display.BoardCanvas;
import ui.display.BoardWindow;

import java.io.*;

import static ui.commands.UserToGameCall.*;


public class TermUI {
    private static TermUI instance;
    private CommandParser parser;
    private BufferedReader reader;
    private Shell shell;

    public static TermUI getInstance(){
        if(instance == null) instance = new TermUI();
        return instance;
    }

    public TermUI(){
        new Thread(() -> {
            Application.launch(BoardWindow.class, null);
            System.exit(0);
        }).start();
        BoardWindow.update();
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

    public void start() {
        SharedCommand sharedCommand;
        while(true){
            System.out.print("Enter command : ");
            parser.newCommand();
            try {
                String cmd = reader.readLine();
                sharedCommand = processCommand(cmd);
            } catch (IOException e) {
                sharedCommand = new SharedCommand(e);
            }
            //Pas très propre, Game et Board à abstraire pour l'UI ?
            processResponse( Game.getInstance().processCommand(sharedCommand));
        }
    }

    protected SharedCommand processCommand(String cmd){
        try {
        return this.parse(cmd);
        } catch (CommandException|CLIException e){
            if(e.getMessage() != null) System.out.println(e.getMessage());
            return new SharedCommand(e);
        }
    }

    protected void processResponse(SharedCommand response) {
        switch (response.getResponse()){
            case VALID:{
                System.out.println("Valid !");
                break;
            }
            case INVALID:{
                if(response.getString().isEmpty()) System.out.println("Valid !");
                else System.out.println(response.getString());
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
        BoardWindow.update();
    }

}
