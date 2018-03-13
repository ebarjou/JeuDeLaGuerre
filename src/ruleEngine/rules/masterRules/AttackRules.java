package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.board.Board;
import game.board.EDirection;
import game.board.Unit;
import game.gameState.GameState;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.atomicRules.*;

import java.util.List;

public class AttackRules extends MasterRule {

    private static final int chargeVal = 7;

    public AttackRules() {
        addRule(new CheckPlayerTurn());
        addRule(new CheckOnBoard());
        addRule(new CheckAbilityToMove());
        addRule(new CheckIsAllyUnit()); // CheckSourceIsAllyUnit
        addRule(new CheckIsEnemyUnit()); // CheckTargetIsEnemyUnit
        addRule(new CheckAreAligned());
        addRule(new CheckUnitRange());
        addDependence(new CheckUnitRange(), new CheckAreAligned(), true);
        addRule(new CheckLastMove());
        addRule(new CheckCanAttackUnit());
        addRule(new CheckIsEmptyAttackPath());
        addDependence(new CheckIsEmptyAttackPath(), new CheckUnitRange(), true);
    }

    @Override
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
                    ( !state.isBuilding(xDir, yDir) || state.getBuildingType(xDir, yDir).isAccessible() ) ) {
                isSurrounded = false;
                break;
            }
        }

        if ( (fightVal > 1) || ((fightVal == 1) && isSurrounded) ) {
            // change board (death)
            state.removeUnit(xT, yT);
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") died :" + fightValMessage + ".");
        }
        else if (fightVal == 1) {
            // change state (retreat)
            Unit unit = new Unit(state.getUnitType(xT, yT), state.getUnitPlayer(xT, yT));
            unit.setPosition(xT, yT);
            state.addPriorityUnit(unit);
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") will have to move in " + state.getUnitPlayer(xT, yT) +
                    "'s next turn :" + fightValMessage + ".");
        }
        else {
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") has been attacked, but nothing happened :" + fightValMessage + ".");
        }
        Coordinates src = action.getTargetCoordinates();
        setUnitHasAttacked(state, src);
    }

    private int caseDefVal(IGameState state, int x, int y) {
        EUnitData unit = state.getUnitType(x, y);
        if (unit.isGetBonusDef() && state.isBuilding(x, y)) {
            return unit.getDefValue() + state.getBuildingType(x, y).getBonusDef();
        }
        return unit.getDefValue();
    }

    private int getFightValueRec(IGameState state, GameAction action, EPlayer player, int x, int y, EDirection dir, boolean isAttack, boolean charge) {
        x += dir.getX();
        y += dir.getY();
        if (!state.isValidCoordinate(x, y) || (state.isBuilding(x, y) && !state.getBuildingType(x, y).isAccessible())) {
            return 0;
        }

        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        if (state.isUnit(x, y) && (state.getUnitPlayer(x, y) == player) &&
                (state.isInCommunication(player, x, y))) {
            EUnitData unit = state.getUnitType(x, y);

            int dist = state.getDistance(x, y, xT, yT);

            if (isAttack && unit.isCanAttack() && (isUnitCanAttack(state, new Coordinates(x, y)))) {
                if (charge && unit.isCanCharge()) {
                    return chargeVal + getFightValueRec(state, action, player, x, y, dir, true, true);
                } else if (unit.getFightRange() >= dist) {
                    return unit.getAtkValue() + getFightValueRec(state, action, player, x, y, dir, true, false);
                }
            }
            else if (!isAttack && (unit.getFightRange() >= dist)) { // isDefense
                return caseDefVal(state, x, y) + getFightValueRec(state, action, player, x, y, dir, false, false);
            }
        }
        return getFightValueRec(state, action, player, x, y, dir, isAttack, false);
    }

    private int getAttackValue(GameState state, GameAction action) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();
        EPlayer player = state.getUnitPlayer(src.getX(), src.getY());

        boolean charge = state.isUnit(src.getX(), src.getY())
            && ( state.getUnitType(src.getX(), src.getY()).isCanCharge() );
        int chargeX = 0;
        int chargeY = 0;
        if (charge) {
            chargeX = src.getX() - dst.getX();
            chargeY = src.getY() - dst.getY();
            if (chargeX != 0)   chargeX = chargeX / Math.abs(chargeX); // 1 or -1
            if (chargeY != 0)   chargeY = chargeY / Math.abs(chargeY); // 1 or -1
        }

        int val = 0;
        for (EDirection dir : EDirection.values()) {
            if ( charge && (chargeX == dir.getX()) && (chargeY == dir.getY()) )
                val += getFightValueRec(state, action, player, dst.getX(), dst.getY(), dir, true, true);
            else {
                val += getFightValueRec(state, action, player, dst.getX(), dst.getY(), dir, true, false);
            }
        }
        return val;
    }

    private int getDefenseValue(GameState state, GameAction action) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();
        EPlayer player = state.getUnitPlayer(xT, yT);

        int val = 0;
        if (state.isInCommunication(player, xT, yT)) {
            val += caseDefVal(state, xT, yT);
        }
        for (EDirection dir : EDirection.values()) {
            val += getFightValueRec(state, action, player, xT, yT, dir, false, false);
        }

        return val;
    }

    private void setUnitHasAttacked(IGameState state, Coordinates src){
        List<Unit> allUnits = state.getAllUnits();
        for(Unit unit : allUnits) {
            if (unit.getX() == src.getX() && unit.getY() == src.getY()) {
                state.setLastUnitMoved(null);
                return;
            }
        }
    }

    private boolean isUnitCanAttack(IGameState state, Coordinates coords){
        List<Unit> cantAttackUnits = state.getCantAttackUnits();
        for(Unit unit : cantAttackUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;
        return true;
    }
}
