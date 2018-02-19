package ui;

import game.EPlayer;
import game.board.Board;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class BoardCanvas extends Canvas {
    private static final int FONT_SIZE = 14;
    private static final int MERGE = 1;
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

    void draw(Board board) {
        g.setFill(Color.LIGHTGREY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (board != null) {
            int caseSize = (int) (getWidth() / (board.getWidth()));

            g.setFill(Color.BLACK);
            g.setTextAlign(TextAlignment.CENTER);
            g.setTextBaseline(VPos.CENTER);
            printCasesHIndex(board, 35, indicesHeight/2, caseSize);
            printCasesVIndex(board, indicesWidth/2, 35, caseSize);

            for (int j = 0; j < board.getHeight(); ++j) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    g.setFill(Color.BLACK);
                    g.fillRect(i * caseSize + indicesWidth, j * caseSize + indicesHeight, caseSize, caseSize);
                    g.setFill(Color.IVORY);
                    int x = MERGE + i * caseSize + indicesWidth, y = MERGE + j * caseSize + indicesHeight, size = caseSize - MERGE * 2;
                    g.fillRect(x, y, size, size);

                    drawCellBackground(board, i, j, x, y, size);
                    drawCellItem(board, i, j, x, y, size);
                }
            }
            g.setStroke(Color.ORANGERED);
            g.setLineWidth(2);
            g.strokeLine(0 + indicesWidth, (board.getHeight() / 2) * caseSize + indicesHeight, board.getWidth() * caseSize + indicesWidth, board.getHeight() * caseSize / 2 + indicesHeight);
        }
    }

    private void printCasesHIndex(Board board, int offsetx, int offsety, int caseSize) {
        for (int i = 0; i < board.getWidth(); ++i) {
            g.fillText("" + (i+1), offsetx + i*caseSize, offsety, 20);
        }
    }

    private String getLettersFromInt(int n) {
        String res = "";
        int base = 26;
        while (n >= 0) {
            int val = n % base;
            char c = 'A'; c += val;
            res = c + res;

            n = n / base - 1;
        }
        return res;
    }

    private void printCasesVIndex(Board board, int offsetx, int offsety, int caseSize) {
        for (int j = 0; j < board.getHeight(); ++j) {
            String s = getLettersFromInt(j);
            g.fillText(s, offsetx, offsety + j*caseSize, 20);
        }
    }

    private void drawCellBackground(Board board, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        if (board.getCommunication(EPlayer.PLAYER1, x, y) && board.getCommunication(EPlayer.PLAYER2, x, y)) {
            g.setStroke(Color.ORANGERED);
            g.setLineWidth(COMM_STROKE_SIZE * 1.5);
            g.strokeRect(pos_x + COMM_STROKE_SIZE, pos_y + COMM_STROKE_SIZE, size - COMM_STROKE_SIZE * 2, size - COMM_STROKE_SIZE * 2);
            g.setStroke(Color.CORNFLOWERBLUE);
            g.setLineWidth(COMM_STROKE_SIZE);
        } else if (board.getCommunication(EPlayer.PLAYER1, x, y)) {
            g.setStroke(Color.CORNFLOWERBLUE);
            g.setLineWidth(COMM_STROKE_SIZE);
        } else if (board.getCommunication(EPlayer.PLAYER2, x, y)) {
            g.setStroke(Color.ORANGERED);
            g.setLineWidth(COMM_STROKE_SIZE);
        }
        if (board.getCommunication(EPlayer.PLAYER1, x, y) || board.getCommunication(EPlayer.PLAYER2, x, y))
            g.strokeRect(pos_x + COMM_STROKE_SIZE / 2, pos_y + COMM_STROKE_SIZE / 2, size - COMM_STROKE_SIZE, size - COMM_STROKE_SIZE);
        g.restore();
    }

    private void drawCellItem(Board board, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.DARKBLUE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.setLineWidth(size / 2);
        if (board.getUnit(x, y) != null)
            g.fillText("" + board.getUnit(x, y).getUnitData().getID(), pos_x + size / 3, pos_y + size / 3, size);
        g.setFill(Color.PURPLE);
        if (board.getBuilding(x, y) != null)
            g.fillText("" + board.getBuilding(x, y).getBuildingData().getID(), pos_x + 2 * size / 3, pos_y + 2 * size / 3, size);
        g.restore();
    }
}
