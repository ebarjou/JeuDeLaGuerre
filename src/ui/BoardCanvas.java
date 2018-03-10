package ui;

import game.EPlayer;
import game.board.IBoard;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class BoardCanvas extends Canvas {
    private static final Color NORTH_COLOR_STRONG = Color.rgb(100, 100, 255);
    private static final Color NORTH_COLOR_LIGHT = Color.rgb(200, 200, 255);
    private static final Color SOUTH_COLOR_STRONG = Color.rgb(255, 100, 100);
    private static final Color SOUTH_COLOR_LIGHT = Color.rgb(255, 200, 200);
    private static final int FONT_SIZE = 14;
    private static final int MARGIN = 1;
    private static final int COMM_STROKE_SIZE = 2;

    private int indicesWidth;
    private int indicesHeight;
    private GraphicsContext g;


    BoardCanvas(int w, int h, int indicesW, int indicesH) {
        super(w + indicesW, h + indicesH);
        indicesWidth = indicesW;
        indicesHeight = indicesH;

        g = this.getGraphicsContext2D();
        g.setLineWidth(2);
        g.setFont(new Font(FONT_SIZE));
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);

        draw(null);
    }

    /**
     * @param board Board to be displayed
     * Refresh the canvas according to the given Board.
     */
    void draw(IBoard board) {
        g.setFill(Color.LIGHTGREY);
        //Fill background
        g.fillRect(0, 0, getWidth(), getHeight());

        if (board != null) {
            int caseSize = (int) (getWidth() / (board.getWidth()));

            //Display letters and numbers around the board
            printCasesHIndex(board, 35, indicesHeight / 2, caseSize);
            printCasesVIndex(board, indicesWidth / 2, 35, caseSize);

            //draw board background
            g.setFill(Color.BLACK);
            g.fillRect(indicesWidth, indicesHeight, getWidth()-indicesWidth,  getHeight()-indicesHeight);

            //draw individual cell background
            for (int j = 0; j < board.getHeight(); ++j) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    drawCellBackground(board, i, j,
                            MARGIN + i * caseSize + indicesWidth,
                            MARGIN + j * caseSize + indicesHeight,
                            caseSize - MARGIN * 2);
                }
            }

            //draw individual cell items
            for (int j = 0; j < board.getHeight(); ++j) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    int x = MARGIN + i * caseSize + indicesWidth, y = MARGIN + j * caseSize + indicesHeight, size = caseSize - MARGIN * 2;
                    drawCellItem(board, i, j, x, y, size);
                }
            }

            g.setStroke(Color.ORANGERED);
            g.strokeLine(0 + indicesWidth, (board.getHeight() / 2) * caseSize + indicesHeight, board.getWidth() * caseSize + indicesWidth, board.getHeight() * caseSize / 2 + indicesHeight);
        }

    }

    private void printCasesHIndex(IBoard board, int offsetx, int offsety, int caseSize) {
        g.save();
        g.setFill(Color.BLACK);
        for (int i = 0; i < board.getWidth(); ++i) {
            g.fillText("" + (i + 1), offsetx + i * caseSize, offsety, 20);
        }
        g.restore();
    }

    private void printCasesVIndex(IBoard board, int offsetx, int offsety, int caseSize) {
        g.save();
        g.setFill(Color.BLACK);
        for (int j = 0; j < board.getHeight(); ++j) {
            String s = getLettersFromInt(j);
            g.fillText(s, offsetx, offsety + j * caseSize, 20);
        }
        g.restore();
    }

    private void drawCellBackground(IBoard board, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.IVORY);
        g.fillRect(pos_x, pos_y, size, size);
        if (board.isInCommunication(EPlayer.PLAYER_NORTH, x, y) && board.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
            g.setFill(SOUTH_COLOR_LIGHT);
            g.fillRect(pos_x, pos_y, size, size);
            g.setFill(NORTH_COLOR_LIGHT);
            g.fillPolygon(new double[] {pos_x, pos_x + size, pos_x}, new double[] {pos_y, pos_y, pos_y + size}, 3);
        } else if (board.isInCommunication(EPlayer.PLAYER_NORTH, x, y)) {
            g.setFill(NORTH_COLOR_LIGHT);
            g.fillRect(pos_x, pos_y, size, size);
        } else if (board.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
            g.setFill(SOUTH_COLOR_LIGHT);
            g.fillRect(pos_x, pos_y, size, size);
        }
        g.restore();
    }

    private void drawCellItem(IBoard board, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.DARKBLUE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.setLineWidth(size / 2);

        try {
            switch (board.getBuildingType(x, y)) {
                case MOUNTAIN:
                    g.setFill(Color.SANDYBROWN);
                    g.fillRect(pos_x, pos_y, size, size);
                    break;
                case PASS:
                    BuildingDrawer.drawDebordStylePass(g, pos_x, pos_y, size, MARGIN);
                    break;
                case FORTRESS:
                    BuildingDrawer.drawDebordStyleFort(g, pos_x, pos_y, size);
                    break;
                case ARSENAL:
                    switch (board.getBuildingPlayer(x, y)) {
                        case PLAYER_NORTH:
                            BuildingDrawer.drawDebordStyleArsenal(g, pos_x, pos_y, size, NORTH_COLOR_STRONG);
                            break;
                        case PLAYER_SOUTH:
                            BuildingDrawer.drawDebordStyleArsenal(g, pos_x, pos_y, size, SOUTH_COLOR_STRONG);
                            break;
                    }
                    break;
                default:
                    g.setFill(Color.PURPLE);
                    g.fillText("" + board.getBuildingType(x, y).getID(), pos_x + 2 * size / 3, pos_y + 2 * size / 3, size);
                    break;
            }
        } catch (NullPointerException e) {}

        try {
            drawUnit(board.getUnitType(x, y).getID(), board.getUnitPlayer(x, y), pos_x, pos_y, size);
        } catch (NullPointerException e) {}
        g.restore();
    }

    private void drawUnit(String unitID, EPlayer player, int pos_x, int pos_y, int size){
        StringBuilder b = new StringBuilder();
        switch (player) {
            case PLAYER_NORTH:
                b.append("n");
                break;
            case PLAYER_SOUTH:
                b.append("s");
                break;
        }

        b.append(unitID);
        drawSpriteFromPath(b.toString(), pos_x, pos_y, size);
    }

    private void drawSpriteFromPath(String name, int pos_x, int pos_y, int size){
        try{
            Image i = new Image("file:res/" + name + ".png");
            if (i.getHeight() == 0) //TODO: Find a better handling
                throw new IllegalArgumentException();
            g.drawImage(i, pos_x, pos_y, size, size);
        } catch (IllegalArgumentException e){
            g.setFill(Color.PURPLE);
            g.fillText(name, pos_x + size/2, pos_y + size/2);
        }
    }

    private String getLettersFromInt(int n) {
        String res = "";
        int base = 26;
        while (n >= 0) {
            int val = n % base;
            char c = 'A';
            c += val;
            res = c + res;
            n = n / base - 1;
        }
        return res;
    }
}
