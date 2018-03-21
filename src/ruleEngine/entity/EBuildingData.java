package ruleEngine.entity;


/**
 * Enum that represents every type of building on the game (minus plain terrain), with their appropriate data.
 */
public enum EBuildingData {
	/*
	name	label		BonusDef
				isCrossable		name
	 */
    MOUNTAIN("M", 	false, 	0,	"Mountain"),
    PASS	("CO", 	true, 	2,	"Pass"),
    FORTRESS("F", 	true, 	4,	"Fortress"),
    ARSENAL	("AR", 	true, 	0,	"Arsenal");

	private String id;
	private boolean accessible; // the name was crossable before.. ?
	private int bonusDef;
	private final String displayName;

    EBuildingData(String id, boolean cross, int def, String displayName) {
        this.id = id;
        this.accessible = cross;
        this.bonusDef = def;
        this.displayName = displayName;
    }

	/**
	 * @return A label identifying the building.
	 */
    public String getID() {
        return id;
    }

	/**
	 * @return true if a unit can move on this case, false otherwise.
	 */
    public boolean isAccessible() {
        return accessible;
    }

	/**
	 * @return The bonus defence power to be added on the unit occupying this case if allowed to benefits from it.
	 * @see EUnitData
	 */
    public int getBonusDef() {
        return bonusDef;
    }

	/**
	 * @return The unit name to display for the end user.
	 */
	public String getDisplayName() {
		return displayName;
	}
}
