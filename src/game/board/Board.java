package game.board;

public class Board implements Cloneable{
    private int width, height;
    private Cell[][] board;

    Board(int width, int height) {
        assert(width > 0 && height > 0);

        this.width = width;
        this.height = height;

        board = new Cell[width][height];

        for(int y = 0; y < height; ++y){
            for(int x = 0; x < width; ++x){
                board[x][y] = new Cell();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Info getInfo(EInfoType key, int x, int y) {
        return board[x][y].getInfo(key);
    }

    //return true if coord (x;y) is beyond board's edges
    private boolean edge(int x, int y) throws ArithmeticException{
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    /**
     * Swap the information concerning the unit/building at coord (x;y) with the cell at coord (x2, y2);
     *
     * @param x    coord X source
     * @param y    coord Y source
     * @param x2   coord X destination
     * @param y2   coord Y destination
     * @param key true if unit is moved, else false for building
     * @return true if the move is done, else false
     */
    boolean move(int x, int y, int x2, int y2, EInfoType key) {
        if(edge(x, y) || edge(x2, y2)) {
            return false;
        }

        Info tmp = board[x][y].getInfo(key);
        board[x][y].addInfo(key, board[x2][y2].getInfo(key));
        board[x2][y2].addInfo(key, tmp);
        return true;
    }


    void addInfo(EInfoType key, Info i, int x, int y) {
        board[x][y].addInfo(key, i);
    }


    void clearCommunication() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                board[x][y].addInfo(EInfoType.COMMUNICATION1, new Info(false));
                board[x][y].addInfo(EInfoType.COMMUNICATION2, new Info(false));
            }
        }
    }

    @Override
    public Board clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (Board) o;
    }
}
