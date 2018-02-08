package ui;

class CommandException extends Exception {
    private boolean unhandled;

    protected CommandException(){
        super("Unhandled exception while parsing command.");
        unhandled = true;
    }

    protected CommandException(String message){
        super(message);
        unhandled = false;
    }

    public boolean isUnhandled() {
        return unhandled;
    }
}
