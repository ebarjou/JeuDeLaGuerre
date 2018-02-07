package ui;

import java.io.*;

public class TermCommand implements IUserCommand {
    private BufferedReader reader;

    public TermCommand(){
        this.reader = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
    }

    @Override
    public String ask() {
        try{
            System.out.println("Enter command : ");
            String cmd = reader.readLine();

            if(cmd == null) return "exit";
            return cmd;
        } catch(IOException e){
            System.out.println(e.getMessage());
            return ask();
        }
    }

    @Override
    public void respond(String response) {
        System.out.println(" > GAME : "+response+"\n");
    }
}
