package ruleEngine.rules.atomicRules;

import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

import java.util.LinkedList;
import java.util.List;

/**
 * Check if a move performed from a unit to its destination is clear from obstacles or next units.<br>
 * Valid if the path is clear, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.MoveRules
 */
public class CheckIsEmptyPath implements IRule {

    private int length;

    private Vertex[][] initMap(int MP, Coordinates src) {
        length = 2 * MP + 1;
        Vertex[][] map = new Vertex[length][length];
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                map[i][j] = new Vertex(src.getX() - MP + i, src.getY() - MP + j, i, j);
            }
        }
        return map;
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        try {
            int MP = state.getUnitType(src.getX(), src.getY()).getMovementValue();

            assert MP != 0;

            Vertex[][] map = initMap(MP, src);
            List<Vertex> queue = new LinkedList<>();

            map[length / 2][length / 2].isMarked = true;
            map[length / 2][length / 2].dist = 0;
            Vertex actual = map[length / 2][length / 2];
            queue.add(actual);  //throw

            while (!queue.isEmpty()) {
                actual = queue.remove(0);
                for (int i = actual.i - 1; i <= actual.i + 1; i++) {
                    for (int j = actual.j - 1; j <= actual.j + 1; j++) {
                        //Don't add the neighbours with invalid coords or those we have already added
                        if (i < 0 || j < 0 || i >= length || j >= length)
                            continue;

                        int x = map[i][j].x;
                        int y = map[i][j].y;

                        if (!state.isValidCoordinate(x, y) || map[i][j].isMarked)
                            continue;
                        //A cell containing a unit isn't valid to find the path
                        if (state.isUnit(x, y))
                            continue;
                        //If there is building and it's a mountain, we can't add it
                        if (state.isBuilding(x, y) && !state.getBuildingType(x, y).isAccessible())
                            continue;

                        //Just create the valid neighbour, with dist + 1
                        Vertex neigh = map[i][j];
                        neigh.isMarked = true;
                        neigh.dist = actual.dist + 1;

                        if (x == target.getX() && y == target.getY() && neigh.dist <= MP)
                            return true;

                        queue.add(neigh);   //throw
                    }
                }
            }
            result.addMessage(this, "There is no path found using " + MP + " movement points.");
            result.invalidate();
            return false;
        } catch (IllegalBoardCallException e) {
            //TODO : Isn't this part impossible to reach ?
            result.addMessage(this, "There is no path found");
            result.invalidate();
            return false;
        }
    }

    private class Vertex {
        int dist;
        private int x, y; // index in array board
        private int i, j; // index in array map
        private boolean isMarked = false;

        Vertex(int x, int y, int i, int j) {
            this.x = x;
            this.y = y;
            this.i = i;
            this.j = j;
        }
    }
}
