package ruleEngine.rules;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.atomicRules.*;

public class AttackRules extends MasterRule {

    private static IRule instance;

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

    public static IRule getInstance() {
        if (instance == null)
            instance = new AttackRules();

        return instance;
    }

    @Override
    public void applyResult(Board board, GameState state, GameAction action, RuleResult result) {
        // attack <= def : do nothing
        // attack+1 = def : retreat
        // attack+2 >= def : death of defensive unit
        int fightVal = getAttackValue(board, action) - getDefenseValue(board, action);
        if (fightVal == 1) {
            // change state (retreat)
        }
        else if (fightVal > 1) {
            // change board (death)
        }
    }

    private int getAttackValue(IBoard board, GameAction action) {
        int w = board.getWidth();
        int h = board.getHeight();
        int xS = action.getSourceCoordinates().getX();
        int yS = action.getSourceCoordinates().getY();
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int val = 0;
        // TODO : Apply the Charge
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                if (board.isUnit(x, y) && (board.getUnitPlayer(x, y) == board.getUnitPlayer(xS, yS))) {
                    EUnitData unit = board.getUnitType(x, y);

                    // Check in 8 directions : i0 == i1 OR j0 == j1 OR i0+j0 == i1+j1 OR i0-j0 == i1-j1
                    if (unit.isCanAttack() && unit.getFightRange() >= board.getDistance(x, y, xT, yT)
                            && ( (x == xT) || (y == yT) || (x + y == xT + yT) || (x - y == xT - yT) )) {
                        val += unit.getAtkValue();
                    }
                }
            }
        }
        return val;
    }

    private int getDefenseValue(IBoard board, GameAction action) {
        int w = board.getWidth();
        int h = board.getHeight();
        int xS = action.getSourceCoordinates().getX();
        int yS = action.getSourceCoordinates().getY();
        int xT = action.getTargetCoordinates().getX();
        int yT = action.getTargetCoordinates().getY();

        int val = 0;
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                if (board.isUnit(x, y) && (board.getUnitPlayer(x, y) == board.getUnitPlayer(xS, yS))
                        && (board.isInCommunication(board.getUnitPlayer(x, y), x, y))) {
                    EUnitData unit = board.getUnitType(x, y);

                    // Check in 8 directions : i0 == i1 OR j0 == j1 OR i0+j0 == i1+j1 OR i0-j0 == i1-j1
                    if (unit.isCanAttack() && (unit.getFightRange() >= board.getDistance(x, y, xT, yT))
                            && ( (x == xT) || (y == yT) || (x + y == xT + yT) || (x - y == xT - yT) )) {
                        val += unit.getDefValue();

                        if (unit.isGetBonusDef()
                                && board.isBuilding(x, y)) {
                            val += board.getBuildingType(x, y).getBonusDef();
                        }
                    }
                }
            }
        }
        return val;
    }
}
