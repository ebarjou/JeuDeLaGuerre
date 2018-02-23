package ruleEngine.rules.masterRules;

import game.board.Board;
import game.board.EDirection;
import game.board.IBoard;
import game.board.Unit;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.atomicRules.*;

public class AttackRules extends MasterRule {

    private static MasterRule instance;

    private AttackRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
        addRule(CheckPlayerTurn.class);
        addRule(CheckOnBoard.class);
        addRule(CheckCommunication.class);
        addRule(CheckIsAllyUnit.class); // CheckSourceIsAllyUnit
        addRule(CheckIsEnemyUnit.class); // CheckTargetIsEnemyUnit
        // TODO : Vérifier que la dernière action était un déplacement vers GameAction.source
        // TODO : Vérifier que le déplacement en question n'était pas une "action prioritaire"
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
        int fightVal = getAttackValue(board, action) - getDefenseValue(board, action);
        if (fightVal == 1) {
            // change state (retreat)
            Unit unit = new Unit(board.getUnitType(xT, yT), board.getUnitPlayer(xT, yT));
            unit.setPosition(xT, yT);
            state.addPriorityUnit(unit);
        }
        else if (fightVal > 1) {
            // change board (death)
            board.delUnit(xT, yT);
        }
    }

    private int getFightValueRec(IBoard board, GameAction action, int x, int y, EDirection dir, boolean isAttack, boolean charge) {
        x += dir.getX();
        y += dir.getY();
        if (!board.isValidCoordinate(x, y) || (board.isBuilding(x, y) && !board.getBuildingType(x, y).isAccessible())) {
            return 0;
        }

        int xS = action.getSourceCoordinates().getX();
        int yS = action.getSourceCoordinates().getY();
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int val = 0;
        if (board.isUnit(x, y) && (board.getUnitPlayer(x, y) == board.getUnitPlayer(xS, yS))) {
            EUnitData unit = board.getUnitType(x, y);

            if (unit.getFightRange() >= board.getDistance(x, y, xT, yT)) {
                if (isAttack && unit.isCanAttack()) {
                    if (unit.isCanCharge() && (charge || (board.getDistance(x, y, xT, yT) == 1))) {
                        val += 7;
                        charge = true;
                    } else {
                        val += unit.getAtkValue();
                        charge = false;
                    }
                }
                else if (!isAttack) { // isDefense
                    val += unit.getDefValue();
                    if (unit.isGetBonusDef() && board.isBuilding(x, y)) {
                        val += board.getBuildingType(x, y).getBonusDef();
                    }
                }
            }
        }
        return val + getFightValueRec(board, action, x, y, dir, isAttack, charge);
    }

    private int getAttackValue(IBoard board, GameAction action) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int val = 0;
        for (EDirection dir : EDirection.values()) {
            val += getFightValueRec(board, action, xT, yT, dir, true, false);
        }
        return val;
    }

    private int getDefenseValue(IBoard board, GameAction action) {
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int val = 0;
        for (EDirection dir : EDirection.values()) {
            val += getFightValueRec(board, action, xT, yT, dir, false, false);
        }
        return val;
    }
}
