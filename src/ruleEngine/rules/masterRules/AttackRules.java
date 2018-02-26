package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.board.Board;
import game.board.EDirection;
import game.board.IBoard;
import game.board.Unit;
import game.gameMaster.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.atomicRules.*;

public class AttackRules extends MasterRule {

    private static MasterRule instance;
    private static final int chargeVal = 7;

    private AttackRules() {
        addRule(CheckPlayerTurn.class);
        addRule(CheckOnBoard.class);
        addRule(CheckCommunication.class);
        addRule(CheckIsAllyUnit.class); // CheckSourceIsAllyUnit
        addRule(CheckIsEnemyUnit.class); // CheckTargetIsEnemyUnit
        addRule(CheckUnitRange.class);
        addRule(CheckLastMove.class);
        addRule(CheckCanAttackUnit.class);
        // TODO : add CheckIsEmptyAttackPath
    }

    public static MasterRule getInstance() {
        if (instance == null)
            instance = new AttackRules();

        return instance;
    }

    @Override
    public void applyResult(Board board, GameState state, GameAction action, RuleResult result) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int atkVal = getAttackValue(board, action);
        int defVal = getDefenseValue(board, action);
        int fightVal = atkVal - defVal;
        String fightValMessage = " Attack:" + atkVal + " Defense:" + defVal;

        boolean isSurrounded = true;
        for (EDirection dir : EDirection.values()) {
            int xDir = xT + dir.getX();
            int yDir = yT + dir.getY();
            if (board.isValidCoordinate(xDir, yDir) && !board.isUnit(xDir, yDir) &&
                    ( !board.isBuilding(xDir, yDir) || board.getBuildingType(xDir, yDir).isAccessible() ) ) {
                isSurrounded = false;
                break;
            }
        }

        if ( (fightVal > 1) || ((fightVal == 1) && isSurrounded) ) {
            // change board (death)
            state.getMutableBoard().delUnit(xT, yT);
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") died :" + fightValMessage + ".");
        }
        else if (fightVal == 1) {
            // change state (retreat)
            Unit unit = new Unit(board.getUnitType(xT, yT), board.getUnitPlayer(xT, yT));
            unit.setPosition(xT, yT);
            state.addPriorityUnit(unit);
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") will have to move in " + board.getUnitPlayer(xT, yT) +
                    "'s next turn :" + fightValMessage + ".");
        }
        else {
            result.addMessage(this, "The unit at position (" + xT + ", " + yT +
                    ") has been attacked, but nothing happened :" + fightValMessage + ".");
        }
        state.getLastUnitMoved().setCanAttack(false);
    }

    private int caseDefVal(Board board, int x, int y) {
        EUnitData unit = board.getUnitType(x, y);
        if (unit.isGetBonusDef() && board.isBuilding(x, y)) {
            return unit.getDefValue() + board.getBuildingType(x, y).getBonusDef();
        }
        return unit.getDefValue();
    }

    private int getFightValueRec(Board board, GameAction action, EPlayer player, int x, int y, EDirection dir, boolean isAttack, boolean charge) {
        x += dir.getX();
        y += dir.getY();
        if (!board.isValidCoordinate(x, y) || (board.isBuilding(x, y) && !board.getBuildingType(x, y).isAccessible())) {
            return 0;
        }

        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int val = 0;
        if (board.isUnit(x, y) && (board.getUnitPlayer(x, y) == player) &&
                (board.isInCommunication(player, x, y))) {
            EUnitData unit = board.getUnitType(x, y);

            int dist = board.getDistance(x, y, xT, yT);

            if (isAttack && unit.isCanAttack()) {
                if (unit.isCanCharge() && (charge || (dist == 1))) {
                    val += chargeVal;
                    charge = true;
                } else if (unit.getFightRange() >= dist) {
                    val += unit.getAtkValue();
                    charge = false;
                }
            }
            else if (!isAttack && (unit.getFightRange() >= dist)) { // isDefense
                val += caseDefVal(board, x, y);
            }
        }
        return val + getFightValueRec(board, action, player, x, y, dir, isAttack, charge);
    }

    private int getAttackValue(Board board, GameAction action) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();
        EPlayer player = board.getUnitPlayer(src.getX(), src.getY());

        int val = 0;
        for (EDirection dir : EDirection.values()) {
            val += getFightValueRec(board, action, player, dst.getX(), dst.getY(), dir, true, false);
        }
        return val;
    }

    private int getDefenseValue(Board board, GameAction action) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();
        EPlayer player = board.getUnitPlayer(xT, yT);

        int val = 0;
        if (board.isInCommunication(player, xT, yT)) {
            val += caseDefVal(board, xT, yT);
        }
        for (EDirection dir : EDirection.values()) {
            val += getFightValueRec(board, action, player, xT, yT, dir, false, false);
        }

        return val;
    }
}
