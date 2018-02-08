package ui.commands;

public enum UserToGameCall {
    CMD_ERROR(false, false, true),
    MOVE(true, false, false),
    ATTACK(true, false, false),
    CHARGE(true, false, false),
    END_TURN(),
    PAT(),
    SURRENDER(),
    UNITS(true, true, false),
    SAVE(false, false, true),
    LOAD(false, false, true),
    REVERT(),
    SET(),
    EXIT();

    UserToGameCall(){
        this.haveCoords1 = false;
        this.haveCoords2 = false;
        this.haveUnit = false;
        this.haveString = false;
    }

    UserToGameCall(boolean haveCoords, boolean haveUnit, boolean haveString){
        this.haveCoords1 = haveCoords;
        this.haveCoords2 = haveCoords&!haveUnit;
        this.haveUnit = haveUnit;
        this.haveString = haveString;
    }

    private boolean haveCoords1, haveCoords2, haveUnit, haveString;

    public boolean haveCoords1() {
        return haveCoords1;
    }

    public boolean haveCoords2() {
        return haveCoords2;
    }

    public boolean haveUnit(){
        return haveUnit;
    }

    public boolean haveString(){
        return haveString;
    }
}
