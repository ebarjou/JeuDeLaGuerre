package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

import java.util.LinkedList;
import java.util.List;

public class CheckIsEmptyPath implements IRule {

    private int length;

    private Vertex[][] initMap(int MP, GameAction.Coordinates src) {
        length = 2 * MP + 1;
        Vertex[][] map = new Vertex[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                map[i][j] = new Vertex(src.getX() - MP + i, src.getY() - MP + j, i, j);
            }
        }
        return map;
    }

    @Override
    public boolean checkAction(IBoard board, GameState state, GameAction action, RuleResult result) {
        GameAction.Coordinates src = action.getSourceCoordinates();
        GameAction.Coordinates target = action.getTargetCoordinates();
        try {
            int MP = board.getUnitType(src.getX(), src.getY()).getMovementValue();

            assert MP != 0;

            Vertex[][] map = initMap(MP, src);
            List<Vertex> queue = new LinkedList<>();

            map[length / 2][length / 2].isMarked = true;
            map[length / 2][length / 2].dist = 0;
            Vertex actual = map[length / 2][length / 2];
            queue.add(actual);

            while (!queue.isEmpty()) {
                actual = queue.remove(0);
                for (int i = actual.i - 1; i <= actual.i + 1; i++) {
                    for (int j = actual.j - 1; j <= actual.j + 1; j++) {
                        //Don't add the neighbours with invalid coords or those we have already added
                        if (i < 0 || j < 0 || i >= length || j >= length)
                            continue;

                        int x = map[i][j].x;
                        int y = map[i][j].y;

                        if (!board.isValidCoordinate(x, y) || map[i][j].isMarked)
                            continue;
                        //A cell containing a unit isn't valid to find the path
                        if (board.isUnit(x, y))
                            continue;
                        //If there is building and it's a mountain, we can't add it
                        if (board.isBuilding(x, y) && !board.getBuildingType(x, y).isAccessible())
                            continue;

                        //Just create the valid neighbour, with dist + 1
                        Vertex neigh = map[i][j];
                        neigh.isMarked = true;
                        neigh.dist = actual.dist + 1;

                        if (x == target.getX() && y == target.getY() && neigh.dist <= MP)
                            return true;

                        queue.add(neigh);
                    }
                }
            }
            result.addMessage(this, "There is no path found using " + MP + " movement points");
            result.invalidate();
            return false;
        } catch (NullPointerException e){
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
