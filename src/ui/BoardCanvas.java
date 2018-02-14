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
    private GraphicsContext g;

    BoardCanvas(int w, int h) {
        super(w, h);
        g = this.getGraphicsContext2D();
        g.setFont(new Font(FONT_SIZE));
        draw(null);
    }

    void draw(Board board) {
        g.fillRect(0, 0, getWidth(), getHeight());
        if (board != null) {
            int caseSize = (int) (getWidth() / (board.getWidth()));
            for (int j = 0; j < board.getHeight(); ++j) {
                for (int i = 0; i < board.getWidth(); ++i) {
                    g.setFill(Color.BLACK);
                    g.fillRect(i * caseSize, j * caseSize, caseSize, caseSize);
                    g.setFill(Color.IVORY);
                    int x = MERGE + i * caseSize, y = MERGE + j * caseSize, size = caseSize - MERGE * 2;
                    g.fillRect(x, y, size, size);

                    drawCellBackground(board, i, j, x, y, size);
                    drawCellItem(board, i, j, x, y, size);
                }
            }
            g.setStroke(Color.ORANGERED);
            g.setLineWidth(2);
            g.strokeLine(0, (board.getHeight() / 2) * caseSize, board.getWidth() * caseSize, board.getHeight() * caseSize / 2);
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
            g.fillText("" + board.getUnit(x, y).getUnit().getID(), pos_x + size / 3, pos_y + size / 3, size);
        g.setFill(Color.PURPLE);
        if (board.getBuilding(x, y) != null)
            g.fillText("" + board.getBuilding(x, y).getBuilding().getID(), pos_x + 2 * size / 3, pos_y + 2 * size / 3, size);
        g.restore();
    }
}
