package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.board.entity.EBuilding;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.items.EUnitData;

import java.util.LinkedList;
import java.util.List;

public class CheckIsEmptyPath implements IRule {

    private static CheckIsEmptyPath instance;

    private CheckIsEmptyPath(){
    }

    public static CheckIsEmptyPath getInstance(){
        if (instance == null)
            instance = new CheckIsEmptyPath();

        return instance;
    }

    @Override
    public boolean checkAction(Board board, GameAction action, RuleResult result) {
        //EUnit u = board.getUnit(c1.getX(), c1.getY()).getId();
        //int PM = ...getPM(u);
        GameAction.Coordinates c1 = action.getSourceCoordinates();
        GameAction.Coordinates c2 = action.getTargetCoordinates();

        int PM = EUnitData.getDataFromEUnit(board.getUnit(c1.getX(), c1.getY()).getId()).getMovementValue();

        Vertex[][] map = new Vertex[2 * PM + 1][2 * PM + 1];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = new Vertex(i, j);
            }
        }

        List<Vertex> queue = new LinkedList<>();

        Vertex actual = new Vertex (c1.getX(), c1.getY());
        actual.isMarked = true;
        actual.dist = 0;
        queue.add(actual);
        while(!queue.isEmpty()){
            actual = queue.remove(0);
            for(int i = actual.x - 1; i <= actual.x + 1; i++){
                for(int j = actual.y - 1; j <= actual.y + 1; j++){
                    if(board.edge(i, j) || map[i][j].isMarked) {
                        continue;
                    }
                    if(board.getUnit(i, j) != null) {
                        continue;
                    }
                    if(board.getBuilding(i, j) != null && board.getBuilding(i, j).getId() == EBuilding.MOUNTAIN) {
                        continue;
                    }
                    System.out.println(board.getDistance(i, j,c1.getX(), c1.getY()));
                    if(i == c2.getX() && j == c2.getY() && actual.dist <= PM){
                        return true;
                    }
                    Vertex v = map[i][j];
                    v.isMarked = true;
                    v.dist = actual.dist + 1;
                    if(v.dist > PM)
                        break;
                    queue.add(v);
                }
            }
        }
        result.addMessage(this, "There is no empty path found.");
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

        void mark(){
            isMarked = true;
        }
    }
}
