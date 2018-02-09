package ruleEngine.items;

import game.board.entity.EUnit;

public enum EUnitData {
    INFANTRY_DATA   (EUnit.INFANTRY, 4, 6, 1, 2, true, false, true, false),
    CAVALRY_DATA    (EUnit.CAVALRY, 4, 5, 2, 2, false, false, true, true),
    ARTILLERY_DATA  (EUnit.ARTILLERY, 5, 8, 1, 3, true, false, true, false),
    ARTILLERY_HORSE_DATA(EUnit.ARTILLERY_HORSE, 5, 8, 2, 3, true, false, true,
            true),
    RELAY_DATA      (EUnit.RELAY, 0, 1, 1, 2, false, true, false, false),
    RELAY_HORSE_DATA(EUnit.RELAY_HORSE, 0, 1, 2, 2, false, true, false, false);

    private EUnit unitID;
    private int atkValue;
    private int defValue;
    private int movementValue;
    private int fightRange;
    private boolean getBonusDef;
    private boolean relayCommunication;
    private boolean canAttack;
    private boolean canCharge;
    // A ajouter si jugé utile (Seuls les relay l'ont à false)
    //private boolean blockTransmissions;

    EUnitData(EUnit uID, int atk, int def, int moveVal, int range,
               boolean bonusDef, boolean relayCom, boolean canAttack,
               boolean canCharge) {
        this.unitID = uID;
        this.atkValue = atk;
        this.defValue = def;
        this.getBonusDef = bonusDef;
        this.movementValue = moveVal;
        this.fightRange = range;
        this.relayCommunication = relayCom;
        this.canAttack = canAttack;
        this.canCharge = canCharge;
    }

    public static EUnitData getDataFromEUnit(EUnit unitType) {
        switch (unitType) {
            case INFANTRY:      return INFANTRY_DATA;
            case CAVALRY :      return CAVALRY_DATA;
            case ARTILLERY:     return ARTILLERY_DATA;
            case ARTILLERY_HORSE:return ARTILLERY_HORSE_DATA;
            case RELAY:         return RELAY_DATA;
            case RELAY_HORSE:   return RELAY_HORSE_DATA;
            default:    return null;
        }
    }

    public EUnit getUnitID() { return unitID; }

    public int getAtkValue() { return atkValue; }

    public int getDefValue() { return defValue; }

    public int getMovementValue() { return movementValue; }

    public int getFightRange() { return fightRange; }

    public boolean isGetBonusDef() { return getBonusDef; }

    public boolean isRelayCommunication() { return relayCommunication; }

    public boolean isCanAttack() { return canAttack; }

    public boolean isCanCharge() { return canCharge; }
}
