package board;

import java.util.Stack;

public class BoardMaster implements IBoardMaster{
    private IMutableBoard actualBoard;
    private static BoardMaster boardMaster;
    private Stack<IMutableBoard> history;

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
     * If one of the parameters is negative, create a board with dimension (0x0)
     * @param width
     * @param height
     */
    public void init(int width, int height){
        if(width < 0 || height < 0)
            actualBoard = new Board(0, 0);
        else
            actualBoard = new Board(width, height);
    }

    public IBoard getBoard(){
        return actualBoard;
    }

    @Override
    public boolean revert(){
        if(history.empty())
            return false;
        actualBoard = history.pop();
        return false;
    }

    @Override
    public void clearHistory() {
        while(!history.empty())
            history.pop();
    }

    @Override
    public boolean move(int x, int y, int x2, int y2, boolean unit) {
        history.push(actualBoard.clone());
        if(actualBoard.move(x, y, x2, y2, unit)){
            return true;
        }
        history.pop();
        return false;
    }

    @Override
    public void setBuilding(EntityID e, int x, int y) {
        actualBoard.setBuilding(e, x, y);
    }

    @Override
    public void setCommunication(int x, int y, boolean isCommunicate) {
        actualBoard.setCommunication(x, y, isCommunicate);
    }

    @Override
    public void setUnit(EntityID e, int x, int y) {
        actualBoard.setUnit(e, x, y);
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
