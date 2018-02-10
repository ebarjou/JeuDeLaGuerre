package ui.display;

import game.board.Board;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class BoardCanvas extends Canvas {
    GraphicsContext g;

    public BoardCanvas(int w, int h){
        super(w, h);
        g = this.getGraphicsContext2D();
        draw(null);
    }

    public void draw(Board board){
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(board != null){
            int merge = 1;
            int caseSize = (int) (getWidth()/(board.getWidth()));
            g.setFill(Color.IVORY);
            for(int j = 0; j < board.getHeight(); ++j){
                for(int i = 0; i < board.getWidth(); ++i){
                    g.fillRect(merge+i*caseSize, merge+j*caseSize, caseSize-merge, caseSize-merge);
                    drawCell(board, i, j, merge+i*caseSize, merge+j*caseSize, caseSize);
                }
            }
        }
    }

    public void drawCell(Board board, int x, int y, int  pos_x, int pos_y, int size){
        g.save();
        g.setFill(Color.DARKBLUE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        if(board.getUnit(x, y) != null) g.fillText(""+board.getUnit(x, y).getId().getName().charAt(0), pos_x+size/2, pos_y+size/4, size);
        g.setFill(Color.PURPLE);
        if(board.getBuilding(x, y) != null) g.fillText(""+board.getBuilding(x, y).getId().getName().charAt(0), pos_x+size/2, pos_y+3*size/4, size);
        g.restore();
    }
}
