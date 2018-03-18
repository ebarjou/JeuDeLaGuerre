package ruleEngine.entity;


/**
 * Enum that represents every type of building on the game (minus plain terrain), with their appropriate data.
 */
public enum EBuildingData {
	/*
	name	label		BonusDef
				isCrossable
	 */
    MOUNTAIN("M", 	false, 	0),
    PASS	("CO", 	true, 	2),
    FORTRESS("F", 	true, 	4),
    ARSENAL	("AR", 	true, 	0);

    private String id;
    private boolean accessible; // the name was crossable before.. ?
    private int bonusDef;

    EBuildingData(String id, boolean cross, int def) {
        this.id = id;
        this.accessible = cross;
        this.bonusDef = def;
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
}
