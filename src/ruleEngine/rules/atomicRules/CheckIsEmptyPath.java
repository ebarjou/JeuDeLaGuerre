package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import game.gameMaster.GameState;

import java.util.LinkedList;
import java.util.List;

public class CheckIsEmptyPath implements IRule {

    private Vertex[][] initMap(int MP, GameAction.Coordinates coords){
        int length = 2 * MP + 1;
        Vertex[][] map = new Vertex[length][length];
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                map[i][j] = new Vertex(coords.getX() - MP + i, coords.getY() - MP + j);
            }
        }
        return map;
    }

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        GameAction.Coordinates src = action.getSourceCoordinates();
        GameAction.Coordinates target = action.getTargetCoordinates();

        int MP = board.getUnit(src.getX(), src.getY()).getUnit().getMovementValue();

        Vertex[][] map = initMap(MP, src);

        List<Vertex> queue = new LinkedList<>();

        Vertex actual = new Vertex (src.getX(), src.getY());
        actual.isMarked = true;
        actual.dist = 0;
        queue.add(actual);
        while(!queue.isEmpty()){
            actual = queue.remove(0);
            for(int i = actual.x - MP; i <= actual.x + MP; i++){
                for(int j = actual.y - MP; j <= actual.y + MP; j++){
                    //Don't add the neighbours with invalid coords or those we have already added
                    if(board.edge(i, j) || map[i][j].isMarked || (i == actual.x && j == actual.y)) {
                        continue;
                    }
                    //A cell containing a unit isn't valid to find the path
                    if(board.getUnit(i, j) != null) {
                        continue;
                    }
                    //If there is building and it's a mountain, we can't add it
                    if(board.getBuilding(i, j) != null && board.getBuilding(i, j).getBuilding().isAccessible()) {
                        continue;
                    }
                    //If the cell we check has the same coords than the target cell
                    //  and it has the right distance, we win, return true.
                    if(i == target.getX() && j == target.getY() && actual.dist <= MP){
                        return true;
                    }
                    //Just create the valid neighbour, with dist + 1
                    Vertex v = map[i][j];
                    v.isMarked = true;
                    v.dist = actual.dist + 1;

                    //If the neighbours is at a distance > MP, we loose

                    if(v.dist > MP)
                        break;

                    queue.add(v);
                }
            }
        }
        result.addMessage(this, "There is no empty path found.");
        result.invalidate();
        return false;
    }

    private class Vertex{
        private int x, y;
        private boolean isMarked = false;
        int dist;
        Vertex(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
