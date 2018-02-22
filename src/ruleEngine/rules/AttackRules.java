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
        //rules = new RuleList();
        /*rules.add(CheckPlayerTurn.class);
        rules.add(CheckPlayerMovesLeft.class);
        rules.add(CheckCommunication.class);
        rules.add(CheckOnBoard.class);
        rules.add(CheckIsUnit.class);
        //rules.add(CheckIsPriorityUnit.class);
        rules.add(CheckUnitMP.class);
        rules.add(CheckIsEmptyPath.class);*/

        // TODO
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
    public void applyResult(Board board, IGameState state, GameAction action, RuleResult result) {
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
        // TODO : Don't check everywhere, only in 8 directions
        // TODO : Apply the Charge
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                if (board.isUnit(x, y) && (board.getUnitPlayer(x, y) == board.getUnitPlayer(xS, yS))) {
                    EUnitData unit = board.getUnitType(x, y);
                    if (unit.getFightRange() >= board.getDistance(x, y, xT, yT) && unit.isCanAttack()) {
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
        // TODO : Don't check everywhere, only in 8 directions
        for (int y = 0 ; y < h ; y++) {
            for (int x = 0 ; x < w ; x++) {
                if (board.isUnit(x, y) && (board.getUnitPlayer(x, y) == board.getUnitPlayer(xS, yS))) {
                    EUnitData unit = board.getUnitType(x, y);
                    if (unit.getFightRange() >= board.getDistance(x, y, xT, yT) && unit.isCanAttack()) {
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
