package ui;

import java.io.*;

public class TermCommand implements IUserCommand {
    private CommandParser parser;
    private BufferedReader reader;


    public TermCommand(){
        this.parser = new CommandParser();
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
    }

    @Override
    public String ask() {
        System.out.println("Enter command : ");
        try{
            String cmd = reader.readLine();
            if(cmd == null) return "exit";
            return ask();
        } catch(IOException e){
            System.out.println(e.getMessage());
            return ask();
        }
    }

    @Override
    public void respond(String response) {

    }

    private void parseResp(String response){

    }

}
