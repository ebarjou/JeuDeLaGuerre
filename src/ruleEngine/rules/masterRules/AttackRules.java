package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.board.EDirection;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;
import ruleEngine.rules.atomicRules.*;
import ruleEngine.rules.newRules.*;

import java.util.List;


/**
 * Class testing if an attack action is allowed on the board or not. If successful, the outcome of the attack is computed
 * and can be displayed from the RuleResult.<br>
 * <br>
 * If the attackers power is lesser or equals to the defenders power, nothing happens.<br>
 * If the attackers power is larger of the defenders power by 1 point, the attacked unit must retreat first on the next turn
 * of the attacked player. This retreat must be performed on a adjacent case of the unit's current position. If there's no empty
 * case around the unit, the unit is removed from the game.<br>
 * If the attackers power is larger of the defenders power by 2 or more points, the attacked unit is removed from the game.
 */
public class AttackRules extends RuleCompositeAND {

    private static final int chargeVal = 7;

    public AttackRules() {
        super.add(new CheckPlayerTurn());
        RuleComposite onBoardDep = new RuleCompositeLazyAND();
        onBoardDep.add(new CheckOnBoard());
        // Creating rules depending of CheckOnBoard

        RuleComposite isAllyUnitDep = new RuleCompositeLazyAND();
        isAllyUnitDep.add(new CheckIsAllyUnit());

        //-Creating rules depending of CheckIsAllyUnit
        RuleComposite andRules = new RuleCompositeAND();
        andRules.add(new CheckLastMove());

        //--Creating rules for range attack
        RuleComposite rangeRules = new RuleCompositeOR();
        rangeRules.add(new CheckUnitRange());
        rangeRules.add(new CheckIsCharge());

        //-Adding range rules in the dependencies of CheckIsAllyUnit
        andRules.add(rangeRules);
        andRules.add(new CheckCanAttackUnit());

        //--Creating not isRelay rule and adding it in the dependencies of CheckIsAllyUnit
        RuleComposite notRelay = new RuleCompositeNOT();
        notRelay.add(new CheckIsRelay());
        andRules.add(notRelay);

        //-Adding all rules depending of CheckIsAllyUnit in CheckIsAlly dependencies
        isAllyUnitDep.add(andRules);

        //-Adding rules depending of CheckOnBoard
        andRules = new RuleCompositeAND();
        andRules.add(isAllyUnitDep);
        andRules.add(new CheckIsInCommunication());

        //--Creating and adding rules dependencies of isEnnemy -> AreAligned -> IsEmptyAttackPath
        RuleComposite areAlignedDep = new RuleCompositeLazyAND();
        areAlignedDep.add(new CheckIsEnemyUnit());
        areAlignedDep.add(new CheckAreAligned());
        areAlignedDep.add(new CheckIsEmptyAttackPath());

        andRules.add(areAlignedDep);
        onBoardDep.add(andRules);

        super.add(onBoardDep);
    }


    public void applyResult(GameState state, GameAction action, RuleResult result) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int atkVal = getAttackValue(state, action);
        int defVal = getDefenseValue(state, action);
        int fightVal = atkVal - defVal;
        String fightValMessage = " Attack:" + atkVal + " Defense:" + defVal;

        boolean isSurrounded = true;
        for (EDirection dir : EDirection.values()) {
            int xDir = xT + dir.getX();
            int yDir = yT + dir.getY();
            if (state.isValidCoordinate(xDir, yDir) && !state.isUnit(xDir, yDir) &&
                    (!state.isBuilding(xDir, yDir) || state.getBuildingType(xDir, yDir).isAccessible())) {
                isSurrounded = false;
                break;
            }
        }

        if ((fightVal > 1) || ((fightVal == 1) && isSurrounded)) {
            // change board (death)
            state.setLastUnitMoved(null);
            state.removeUnit(xT, yT);
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") died :" + fightValMessage + ".");
        } else if (fightVal == 1) {
            // change state (retreat)
            state.setLastUnitMoved(null);
            Unit unit = new Unit(state.getUnitType(xT, yT), state.getUnitPlayer(xT, yT));
            unit.setPosition(xT, yT);
            state.addPriorityUnit(unit);
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") will have to move in " + state.getUnitPlayer(xT, yT) +
                    "'s next turn :" + fightValMessage + ".");
        } else {
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") has been attacked, but nothing happened :" + fightValMessage + ".");
        }
        Coordinates src = action.getTargetCoordinates();
        setUnitHasAttacked(state);
    }

    private int caseDefVal(GameState state, int x, int y) {
        EUnitProperty unit = state.getUnitType(x, y);
        if (unit.isGetBonusDef() && state.isBuilding(x, y)) {
            return unit.getDefValue() + state.getBuildingType(x, y).getBonusDef();
        }
        return unit.getDefValue();
    }

    private int getFightValueRec(GameState state, GameAction action, EPlayer player, int x, int y, EDirection dir, boolean isAttack, boolean charge) {
        x += dir.getX();
        y += dir.getY();
        if (!state.isValidCoordinate(x, y) || (state.isBuilding(x, y) && !state.getBuildingType(x, y).isAccessible())) {
            return 0;
        }

        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        if (state.isUnit(x, y) && (state.getUnitPlayer(x, y) == player) &&
                (state.isInCommunication(player, x, y))) {
            EUnitProperty unit = state.getUnitType(x, y);

            int dist = state.getDistance(x, y, xT, yT);

            if (isAttack && unit.isCanAttack() && (isUnitCanAttack(state, new Coordinates(x, y)))) {
                if (charge && unit.isCanCharge()
                        && ((!state.isBuilding(x, y))
                        || (state.getBuildingType(x, y) != EBuildingProperty.FORTRESS))) {
                    return chargeVal + getFightValueRec(state, action, player, x, y, dir, true, true);
                } else if (unit.getFightRange() >= dist) {
                    return unit.getAtkValue() + getFightValueRec(state, action, player, x, y, dir, true, false);
                }
            } else if (!isAttack && (unit.getFightRange() >= dist)) { // isDefense
                return caseDefVal(state, x, y) + getFightValueRec(state, action, player, x, y, dir, false, false);
            }
        }
        return getFightValueRec(state, action, player, x, y, dir, isAttack, false);
    }

    private int getAttackValue(GameState state, GameAction action) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();
        EPlayer player = state.getUnitPlayer(src.getX(), src.getY());

        boolean isUnitSrc = state.isUnit(src.getX(), src.getY());
        boolean isBuildingSrc = state.isBuilding(src.getX(), src.getY());
        boolean isBuildingDst = state.isBuilding(dst.getX(), dst.getY());

        EUnitProperty unitSrc = null;
        EBuildingProperty buildingSrc = null;
        EBuildingProperty buildingDst = null;

        if (isUnitSrc)
            unitSrc = state.getUnitType(src.getX(), src.getY());
        if (isBuildingSrc)
            buildingSrc = state.getBuildingType(src.getX(), src.getY());
        if (isBuildingDst)
            buildingDst = state.getBuildingType(dst.getX(), dst.getY());

        boolean charge = isUnitSrc && unitSrc.isCanCharge() && (!isBuildingSrc || (buildingSrc != EBuildingProperty.FORTRESS))
                && (!isBuildingDst || (buildingDst != EBuildingProperty.FORTRESS && buildingDst != EBuildingProperty.PASS));

        int chargeX = 0;
        int chargeY = 0;
        if (charge) {
            chargeX = src.getX() - dst.getX();
            chargeY = src.getY() - dst.getY();
            if (chargeX != 0) chargeX = chargeX / Math.abs(chargeX); // 1 or -1
            if (chargeY != 0) chargeY = chargeY / Math.abs(chargeY); // 1 or -1
        }

        int result = 0;
        for (EDirection dir : EDirection.values())
            if (charge && (chargeX == dir.getX()) && (chargeY == dir.getY()))
                result += getFightValueRec(state, action, player, dst.getX(), dst.getY(), dir, true, true);
            else
                result += getFightValueRec(state, action, player, dst.getX(), dst.getY(), dir, true, false);

        return result;
    }

    private int getDefenseValue(GameState state, GameAction action) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();
        EPlayer player = state.getUnitPlayer(xT, yT);

        int result = 0;
        if (state.isInCommunication(player, xT, yT))
            result += caseDefVal(state, xT, yT);

        for (EDirection dir : EDirection.values())
            result += getFightValueRec(state, action, player, xT, yT, dir, false, false);

        return result;
    }

    private void setUnitHasAttacked(GameState state) {
        state.setLastUnitMoved(null);
    }

    private boolean isUnitCanAttack(GameState state, Coordinates coords) {
        List<Unit> cantAttackUnits = state.getCantAttackUnits();
        for (Unit unit : cantAttackUnits)
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;
        return true;
    }


}
