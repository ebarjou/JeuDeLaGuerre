package board;

public class Board implements IMutableBoard {
    private int width, height;
    private Cell[][] board;

    public Board(int width, int height) {
        if(width < 0)
            this.width = 0;
        if(height < 0)
            this.height = 0;

        this.width = width;
        this.height = height;

        board = new Cell[width][height];

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
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

    @Override
    public boolean isInfo(String key, int x, int y) {
        return board[x][y].isInfo(key);
    }

    @Override
    public Info getInfo(String key, int x, int y) {
        return board[x][y].getInfo(key);
    }

    //return true if coord (x;y) is beyond board's edges
    private boolean edge(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height;
    }

    private void swapInfo(int x, int y, int x2, int y2, String key) {
        Info tmp = board[x][y].getInfo(key);
        board[x][y].addInfo(key, board[x2][y2].getInfo(key));
        board[x2][y2].addInfo(key, tmp);
    }

    //Need to use enum for Key used
    private void swap(int x, int y, int x2, int y2, boolean unit) {
        String key = "unit";
        if (!unit)
            key = "building";
        swapInfo(x, y, x2, y2, key);
    }


    /**
     * Swap the information concerning the unit/building at coord (x;y) with the cell at coord (x2, y2);
     *
     * @param x    coord X source
     * @param y    coord Y source
     * @param x2   coord X destination
     * @param y2   coord Y destination
     * @param unit true if unit is moved, else false for building
     * @return true if the move is done, else false
     */

    @Override
    public boolean move(int x, int y, int x2, int y2, boolean unit) {
        if (edge(x, y) || edge(x2, y2)) {
            return false;
        }
        //Check if the information exist depending on the type of the entity
        if ((unit && board[x][y].isInfo("unit")) || (!unit && board[x][y].isInfo("building"))) {
            return false;
        }
        swap(x, y, x2, y2, unit);
        return true;
    }

    @Override
    public void addInfo(String key, Info i, int x, int y) {
        board[x][y].addInfo(key, i);
    }

    @Override
    public void clearCommunication() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                board[x][y].addInfo("com1", new Info("com", 0));
                board[x][y].addInfo("com2", new Info("com", 0));
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
