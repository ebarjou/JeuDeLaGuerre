package ui;

import game.EPlayer;
import game.board.IBoard;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class BoardCanvas extends Canvas {
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
        g.setFont(new Font(FONT_SIZE));
        draw(null);
    }

    void draw(IBoard board) {
        g.setFill(Color.LIGHTGREY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (board != null) {
            int caseSize = (int) (getWidth() / (board.getWidth()));

            g.setFill(Color.BLACK);
            g.setTextAlign(TextAlignment.CENTER);
            g.setTextBaseline(VPos.CENTER);
            printCasesHIndex(board, 35, indicesHeight / 2, caseSize);
            printCasesVIndex(board, indicesWidth / 2, 35, caseSize);

            for (int j = 0; j < board.getHeight(); ++j) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    g.setFill(Color.BLACK);
                    g.fillRect(i * caseSize + indicesWidth, j * caseSize + indicesHeight, caseSize, caseSize);
                    g.setFill(Color.IVORY);
                    int x = MARGIN + i * caseSize + indicesWidth, y = MARGIN + j * caseSize + indicesHeight, size = caseSize - MARGIN * 2;
                    g.fillRect(x, y, size, size);

                    drawCellBackground(board, i, j, x, y, size);
                }
            }

            for (int j = 0; j < board.getHeight(); ++j) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    int x = MARGIN + i * caseSize + indicesWidth, y = MARGIN + j * caseSize + indicesHeight, size = caseSize - MARGIN * 2;
                    drawCellItem(board, i, j, x, y, size);
                }
            }

            g.setStroke(Color.ORANGERED);
            g.setLineWidth(2);
            g.strokeLine(0 + indicesWidth, (board.getHeight() / 2) * caseSize + indicesHeight, board.getWidth() * caseSize + indicesWidth, board.getHeight() * caseSize / 2 + indicesHeight);
        }

    }

    private void printCasesHIndex(IBoard board, int offsetx, int offsety, int caseSize) {
        for (int i = 0; i < board.getWidth(); ++i) {
            g.fillText("" + (i + 1), offsetx + i * caseSize, offsety, 20);
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

    private void printCasesVIndex(IBoard board, int offsetx, int offsety, int caseSize) {
        for (int j = 0; j < board.getHeight(); ++j) {
            String s = getLettersFromInt(j);
            g.fillText(s, offsetx, offsety + j * caseSize, 20);
        }
    }

    private void drawCellBackground(IBoard board, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        //TODO: Change color density here
        int comColorHigh = 255;
        int comColorLow = 200;
        if (board.isInCommunication(EPlayer.PLAYER_NORTH, x, y) && board.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
            //g.setStroke(Color.ORANGERED);
            //g.setLineWidth(COMM_STROKE_SIZE * 1.5);
            //g.strokeRect(pos_x + COMM_STROKE_SIZE, pos_y + COMM_STROKE_SIZE, size - COMM_STROKE_SIZE * 2, size - COMM_STROKE_SIZE * 2);
            //g.setStroke(Color.CORNFLOWERBLUE);
            //g.setLineWidth(COMM_STROKE_SIZE);
            //g.setFill(Color.rgb(comColorHigh, comColorLow + comColorLowModifier, comColorHigh));
            g.setFill(Color.rgb(comColorHigh, comColorLow, comColorLow));
            g.fillRect(pos_x, pos_y, size, size);
            g.setFill(Color.rgb(comColorLow, comColorLow, comColorHigh));
            g.fillPolygon(new double[] {pos_x, pos_x + size, pos_x}, new double[] {pos_y, pos_y, pos_y + size}, 3);
        } else if (board.isInCommunication(EPlayer.PLAYER_NORTH, x, y)) {
            //g.setStroke(Color.CORNFLOWERBLUE);
            //g.setLineWidth(COMM_STROKE_SIZE);
            g.setFill(Color.rgb(comColorLow, comColorLow, comColorHigh));
            g.fillRect(pos_x, pos_y, size, size);
        } else if (board.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
            //g.setStroke(Color.ORANGERED);
            //g.setLineWidth(COMM_STROKE_SIZE);
            g.setFill(Color.rgb(comColorHigh, comColorLow, comColorLow));
            g.fillRect(pos_x, pos_y, size, size);
        }
        //if (board.isInCommunication(EPlayer.PLAYER_NORTH, x, y) || board.isInCommunication(EPlayer.PLAYER_SOUTH, x, y))
        //    g.strokeRect(pos_x + COMM_STROKE_SIZE / 2, pos_y + COMM_STROKE_SIZE / 2, size - COMM_STROKE_SIZE, size - COMM_STROKE_SIZE);
        g.restore();
    }

    private void drawCellItem(IBoard board, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.DARKBLUE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.setLineWidth(size / 2);
        try {
            g.fillText("" + board.getUnitType(x, y).getID(), pos_x + size / 3, pos_y + size / 3, size);
        } catch (NullPointerException e) {

        }
        try {
            switch (board.getBuildingType(x, y)) {
                case MOUNTAIN:
                    g.setFill(Color.SANDYBROWN);
                    g.fillRect(pos_x, pos_y, size, size);
                    break;

                case PASS:
                    drawDebordStylePass(pos_x, pos_y, size);
                    break;

                case FORTRESS:
                    drawDebordStyleFort(pos_x, pos_y, size);
                    break;

                case ARSENAL:
                    switch (board.getBuildingPlayer(x, y)) {
                        //TODO: Proper color managed values here
                        case PLAYER_NORTH:
                            drawDebordStyleArsenal(pos_x, pos_y, size, Color.rgb(100, 100, 255));
                            break;
                        case PLAYER_SOUTH:
                            drawDebordStyleArsenal(pos_x, pos_y, size, Color.rgb(255, 100, 100));
                            break;
                    }
                    break;

                default:
                    g.setFill(Color.PURPLE);
                    g.fillText("" + board.getBuildingType(x, y).getID(), pos_x + 2 * size / 3, pos_y + 2 * size / 3, size);
                    break;
            }
        } catch (NullPointerException e) {

        }
        g.restore();
    }

    //TODO: Cause some issues if the case is on a border. Maybe the canvas should have a cute border around it

    private void drawDebordStyleFort(int pos_x, int pos_y, int size) {
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        double sizeSplits = size / 3;

        g.strokeLine(pos_x, pos_y, pos_x + size, pos_y);
        g.strokeLine(pos_x, pos_y, pos_x, pos_y + size);
        g.strokeLine(pos_x, pos_y + size, pos_x + size, pos_y + size);
        g.strokeLine(pos_x + size, pos_y, pos_x + size, pos_y + size);

        g.strokeLine(pos_x, pos_y, pos_x - sizeSplits, pos_y - sizeSplits);
        g.strokeLine(pos_x + size, pos_y, pos_x + size + sizeSplits, pos_y - sizeSplits);
        g.strokeLine(pos_x, pos_y + size, pos_x - sizeSplits, pos_y + size + sizeSplits);
        g.strokeLine(pos_x + size, pos_y + size, pos_x + size + sizeSplits, pos_y + size + sizeSplits);

    }

    private void drawDebordStylePass(int pos_x, int pos_y, int size){
        g.setLineWidth(MARGIN * 2);
        int caseStartOffset = MARGIN;
        int caseEndOffset = size + MARGIN;
        int dashSize = 2;
        for (int i = pos_x - caseStartOffset; i < pos_x + caseEndOffset; i += dashSize * 2){
            g.setStroke(Color.SANDYBROWN);
            g.strokeLine(i, pos_y - caseStartOffset, i + dashSize, pos_y - caseStartOffset);
            g.strokeLine(i, pos_y + caseEndOffset, i + dashSize, pos_y + caseEndOffset);

            g.setStroke(Color.IVORY);
            g.strokeLine(i + dashSize, pos_y - caseStartOffset, i + dashSize*2, pos_y - caseStartOffset);
            g.strokeLine(i + dashSize, pos_y + caseEndOffset, i + dashSize*2, pos_y + caseEndOffset);
        }

        for (int i = pos_y - caseStartOffset; i < pos_y + caseEndOffset; i += dashSize * 2){
            g.setStroke(Color.SANDYBROWN);
            g.strokeLine(pos_x - caseStartOffset, i, pos_x - caseStartOffset, i + dashSize);
            g.strokeLine(pos_x + caseEndOffset, i, pos_x + caseEndOffset, i + dashSize);

            g.setStroke(Color.IVORY);
            g.strokeLine(pos_x - caseStartOffset, i + dashSize, pos_x - caseStartOffset, i + dashSize*2);
            g.strokeLine(pos_x + caseEndOffset, i + dashSize, pos_x + caseEndOffset, i + dashSize*2);
        }
    }

    private void drawDebordStyleArsenal(int pos_x, int pos_y, int size, Color color){
        g.setStroke(color);
        g.setLineWidth(2);
        double dashSize = size / 3;

        g.strokeLine(pos_x, pos_y, pos_x + size, pos_y);
        g.strokeLine(pos_x, pos_y, pos_x, pos_y + size);
        g.strokeLine(pos_x, pos_y + size, pos_x + size, pos_y + size);
        g.strokeLine(pos_x + size, pos_y, pos_x + size, pos_y + size);

        g.strokeLine(pos_x + size / 2 - dashSize/2, pos_y + size / 2, pos_x + size / 2 + dashSize/2, pos_y + size / 2);
        g.strokeLine(pos_x + size / 2, pos_y + size / 2 - dashSize/2, pos_x + size / 2, pos_y + size / 2 + dashSize/2);
    }
}
