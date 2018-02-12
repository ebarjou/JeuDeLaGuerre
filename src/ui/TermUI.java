package ui;

import asg.cliche.CLIException;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import game.Game;
import javafx.application.Application;
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
        this.parser = new CommandParser();
        shell = ShellFactory.createConsoleShell("Enter command", "", parser);
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
        System.out.println("> ?list to display all available commands.");
    }

    private UIAction parse(String cmd) throws CLIException, CommandException{
        if(cmd == null) return new UIAction(EXIT, null);
        shell.processLine(cmd);
        if(parser.getResult().getCommand() == CMD_ERROR) throw new CommandException(parser.getResult().getErrorMessage());
        return parser.getResult();
    }

    public void start() {
        UIAction UIAction;
        while(true){
            System.out.print("Enter command : ");
            parser.newCommand();
            try {
                String cmd = reader.readLine();
                UIAction = processCommand(cmd);
            } catch (IOException e) {
                UIAction = new UIAction(CMD_ERROR, null);
            }
            //Pas très propre, Game et Board à abstraire pour l'UI ?
            processResponse( Game.getInstance().processCommand(UIAction));
        }
    }

    protected UIAction processCommand(String cmd){
        try {
        return this.parse(cmd);
        } catch (CommandException|CLIException e){
            if(e.getMessage() != null) System.out.println(e.getMessage());
            return new UIAction(CMD_ERROR, null);
        }
    }

    protected void processResponse(GameResponse response) {
        switch (response.getResponse()){
            case VALID:{
                System.out.println("Valid !");
                break;
            }
            case INVALID:{
                if(response.getMessage().isEmpty()) System.out.println("Valid !");
                else System.out.println(response.getMessage());
                break;
            }
            case APPLIED:{
                break;
            }
            case REFRESH:{
                break;
            }
            case GAME_ERROR:{
                break;
            }
        }
    }
}
