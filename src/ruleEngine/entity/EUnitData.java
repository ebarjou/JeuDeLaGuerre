package ruleEngine.entity;

//TODO: Maybe use only this enum for the board instead of an enum with only names ..
public enum EUnitData {
    INFANTRY("I", 4, 6, 1, 2,           true,   false,  true,   false),
    CAVALRY("C", 4, 5, 2, 2,            false,  false,  true,   true),
    ARTILLERY("A", 5, 8, 1, 3,          true,   false,  true,   false),
    ARTILLERY_HORSE("AC", 5, 8, 2, 3,   true,   false,  true,   true),
    RELAY("R", 0, 1, 1, 2,              false,  true,   false,  false),
    RELAY_HORSE("RC", 0, 1, 2, 2,       false,  true,   false,  false);

    private String id;
    private int atkValue;
    private int defValue;
    private int movementValue;
    private int fightRange;
    private boolean getBonusDef;
    private boolean relayCommunication;
    private boolean canAttack;
    private boolean canCharge;
    //blockTransmission is true for all the units except relay...
    //private boolean blockTransmissions;

    EUnitData(String id, int atk, int def, int moveVal, int range,
              boolean bonusDef, boolean relayCom, boolean canAttack,
              boolean canCharge) {
        this.id = id;
        this.atkValue = atk;
        this.defValue = def;
        this.getBonusDef = bonusDef;
        this.movementValue = moveVal;
        this.fightRange = range;
        this.relayCommunication = relayCom;
        this.canAttack = canAttack;
        this.canCharge = canCharge;
    }

    public String getID() {
        return id;
    }

    public int getAtkValue() {
        return atkValue;
    }

    public int getDefValue() {
        return defValue;
    }

    public int getMovementValue() {
        return movementValue;
    }

    public int getFightRange() {
        return fightRange;
    }

    public boolean isGetBonusDef() {
        return getBonusDef;
    }

    public boolean isRelayCommunication() {
        return relayCommunication;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public boolean isCanCharge() {
        return canCharge;
    }
}
