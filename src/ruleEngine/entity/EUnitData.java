package ruleEngine.entity;

/**
 * Enum used to represent every type of unit in the game, with their appropriates data.
 */
public enum EUnitData {
	/*
	Name			Label    Def   Range       canRelay         canCharge
	 					 Atk	MP      FortDef         canAtk
	 */
	INFANTRY(		"I"	, 4	, 6, 1, 2,  true,   false,  true,   false),
	CAVALRY(		"C"	, 4	, 5, 2, 2,  false,  false,  true,   true),
	ARTILLERY(		"A"	, 5	, 8, 1, 3,	true,   false,  true,   false),
	ARTILLERY_HORSE("AC", 5	, 8, 2, 3,	true,   false,  true,   false),
	RELAY(			"R"	, 0	, 1, 1, 2,	false,  true,   false,  false),
	RELAY_HORSE(	"RC", 0	, 1, 2, 2,	false,  true,   false,  false);

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

	/**
	 * @return A label identifying the unit.
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return The individual attack power of the unit.
	 */
	public int getAtkValue() {
		return atkValue;
	}

	/**
	 * @return The individual defence power of the unit.
	 */
	public int getDefValue() {
		return defValue;
	}

	/**
	 * @return The range within the unit can move to.
	 */
	public int getMovementValue() {
		return movementValue;
	}

	/**
	 * @return The range within the unit can attack another unit.
	 */
	public int getFightRange() {
		return fightRange;
	}

	/**
	 * @return true if the unit benefits of a bonus of defence power when placed on some building, false otherwise.
	 * @see EBuildingData
	 */
	public boolean isGetBonusDef() {
		return getBonusDef;
	}

	/**
	 * @return true if the unit generate communication axis on its own, false otherwise.
	 * @see ruleEngine.rules.masterRules.CommRules
	 */
	public boolean isRelayCommunication() {
		return relayCommunication;
	}

	/**
	 * @return true if the unit is allowed to initiate attack, false otherwise.
	 */
	public boolean isCanAttack() {
		return canAttack;
	}

	/**
	 * @return true if the unit is allowed to initiate charges, a special attack.
	 * @see ruleEngine.rules.masterRules.AttackRules
	 */
	public boolean isCanCharge() {
		return canCharge;
	}
}
