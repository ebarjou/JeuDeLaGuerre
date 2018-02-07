package game.board;

import java.util.Stack;

public class BoardMaster implements IBoardMaster{
    private Board actualBoard;
    private static BoardMaster boardMaster;
    private Stack<Board> history;

    private BoardMaster(){
        history = new Stack<>();
    }

    public static IBoardMaster getInstance(){
        if(boardMaster == null){
            boardMaster = new BoardMaster();
        }
        return boardMaster;
    }

    /**
     * If one of the parameters is negative, assert.
     * @param width Width of the game.board in cell
     * @param height Height of the game.board in cell
     */
    public void init(int width, int height){
        actualBoard = new Board(width, height);
    }

    public Board getBoard(){
        return actualBoard;
    }

    @Override
    public boolean revert(){
        if(history.empty())
            return false;
        actualBoard = history.pop();
        return true;
    }

    @Override
    public void clearHistory() {
        while(!history.empty())
            history.pop();
    }

    @Override
    public boolean moveUnit(int x, int y, int x2, int y2) {
        history.push(actualBoard.clone());
        if(actualBoard.move(x, y, x2, y2, EInfoType.UNIT)){
            return true;
        }
        history.pop();
        return false;
    }

    @Override
    public boolean moveBuilding(int x, int y, int x2, int y2) {
        Board tmp = actualBoard.clone();
        if(actualBoard.move(x, y, x2, y2, EInfoType.BUILDING)){
            history.push(tmp);
            return true;
        }
        return false;
    }

    @Override
    public Object getValueInfo(EInfoType key, int x, int y) {
        return actualBoard.getInfo(key, x, y).getValue();
    }

    @Override
    public void addInfo(EInfoType key, Info info, int x, int y) {
        actualBoard.addInfo(key, info, x, y);
    }

    @Override
    public void clearCommunication() {
        actualBoard.clearCommunication();
    }



/* Tests for clone (need to be moved)
        IBoardMaster bm = BoardMaster.getInstance();
        bm.init(10, 10);
        IMutableBoard imb = new Board(10, 10);


        int i = 10;
        {
            int x = 0;
            int y = 0;
            while (i != 0) {
                EntityID e = new EntityID(1, x % 2 == 0, "a" + x);
                imb.setUnit(e, x, y);
                x++;
                y++;
                i--;
            }
        }

        IMutableBoard cl = imb.clone();
        //IMutableBoard cl = imb;
        if(cl == imb) {
            for (int y = 0; y < imb.getHeight(); y++) {
                for (int x = 0; x < imb.getWidth(); x++) {
                    EntityID u = cl.getUnit(x, y);
                    EntityID b = imb.getUnit(x, y);
                    if (u != null && (u == b))
                        System.out.println("Same ref");
                }
            }
        }

 */
}
