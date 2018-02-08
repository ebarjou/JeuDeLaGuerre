package ui.commands;

public enum UserToGameCommand {
    CMD_ERROR(),
    MOVE(true, true),
    ATTACK(true, true),
    CHARGE(true, true),
    PAT(),
    EXIT()
    ;

    UserToGameCommand(){
        this.haveCoords1 = false;
        this.haveCoords2 = false;
    }

    UserToGameCommand(boolean haveCoords1, boolean haveCoords2){
        this.haveCoords1 = haveCoords1;
        this.haveCoords2 = haveCoords2;
    }

    private boolean haveCoords1, haveCoords2;

    public boolean haveCoords1() {
        return haveCoords1;
    }

    public boolean haveCoords2() {
        return haveCoords2;
    }
}
