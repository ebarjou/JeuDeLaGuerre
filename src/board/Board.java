package board;

public class Board implements IMutableBoard{
    private int width, height;
    private Cell[][] board;

    public Board(int width, int height){
        this.width = width;
        this.height = height;

        board = new Cell[width][height];

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                board[x][y] = new Cell();
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    //return true if coord (x;y) is beyond board's edges
    private boolean edge(int x, int y){
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    private void swapUnit(int x, int y, int x2, int y2){
        EntityID tmp = board[x][y].getUnit();
        board[x][y].setUnit(board[x2][y2].getUnit());
        board[x2][y2].setUnit(tmp);
    }

    private void swapBuilding(int x, int y, int x2, int y2){
        EntityID tmp = board[x][y].getBuilding();
        board[x][y].setBuilding(board[x2][y2].getBuilding());
        board[x2][y2].setBuilding(tmp);
    }

    private void swap(int x, int y, int x2, int y2, boolean unit){
        if(unit)
            swapUnit(x, y, x2, y2);
        else
            swapBuilding(x, y, x2, y2);
    }

    //Move the entityID in the cell at the coord (x;y) to the cell (x2;y2)
    //Swap the entityID between this two cells if the coords are valid.
    //if unit is true, it's the entityID corresponding to the unit in the cells that is swaped,
    // else it's the building.
    @Override
    public boolean move(int x, int y, int x2, int y2, boolean unit) {
        if(edge(x, y) || edge(x2, y2)){
            return false;
        }
        //Stack need to be defined (Who will handle the stack, Need a clone method ?)
        //history.push(this);
        swap(x, y, x2, y2, unit);
        return true;
    }

    @Override
    public void setCommunication(int x, int y, boolean isCommunicate) {
        if(!edge(x, y)){
            board[x][y].setCommunicate(isCommunicate);
        }
    }

    @Override
    public boolean getCommunication(int x, int y) {
        return !edge(x, y) && board[x][y].isCommunicate();
    }

    @Override
    public void clearCommunication(){
        for(int y = 0; y < height; ++y){
            for(int x = 0; x < width; ++x){
                board[x][y].setCommunicate(false);
            }
        }
    }

    @Override
    public EntityID getBuilding(int x, int y) {
        return !edge(x, y) ? board[x][y].getBuilding() : null;
    }

    @Override
    public EntityID getUnit(int x, int y) {
        return !edge(x, y) ? board[x][y].getUnit() : null;
    }

    @Override
    public void setBuilding(EntityID e, int x, int y) {
        if(!edge(x, y))
            board[x][y].setBuilding(e);
    }

    @Override
    public void setUnit(EntityID e, int x, int y) {
        if(!edge(x, y))
            board[x][y].setUnit(e);
    }

    @Override
    public boolean isEmptyUnit(int x, int y) {
        return !edge(x, y) && (board[x][y].getUnit() == null);
    }

    @Override
    public boolean isEmptyBuilding(int x, int y) {
        return !edge(x, y) && board[x][y].getBuilding() == null;
    }

    @Override
    public Board clone(){
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return (Board)o;
    }


}
