package ui.display;

import game.EPlayer;
import game.board.Board;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class BoardCanvas extends Canvas {
    GraphicsContext g;

    public BoardCanvas(int w, int h){
        super(w, h);
        g = this.getGraphicsContext2D();
        g.setFont(new Font(18));
        draw(null);
    }

    public void draw(Board board){
        g.fillRect(0, 0, getWidth(), getHeight());
        if(board != null){
            int merge = 1;
            int caseSize = (int) (getWidth()/(board.getWidth()));
            for(int j = 0; j < board.getHeight(); ++j){
                for(int i = 0; i < board.getWidth(); ++i){
                    g.setFill(Color.BLACK);
                    g.fillRect(i*caseSize, j*caseSize, caseSize, caseSize);
                    g.setFill(Color.IVORY);
                    int x = merge+i*caseSize, y = merge+j*caseSize, size = caseSize-merge*2;
                    g.fillRect(x, y, size, size);

                    drawCellBackground(board, i, j, x, y, size);
                    drawCellItem(board, i, j, x, y, size);
                }
            }
            g.setStroke(Color.rgb(255, 50, 50));
            g.setLineWidth(2);
            g.strokeLine(0, (board.getHeight() / 2) * caseSize, board.getWidth() * caseSize, board.getHeight() * caseSize / 2);
        }
    }

    public void drawCellBackground(Board board, int x, int y, int  pos_x, int pos_y, int size){
        int stroke_size = 2;
        g.save();
        if(board.getCommunication(EPlayer.PLAYER1, x, y) && board.getCommunication(EPlayer.PLAYER2, x, y)){
            g.setStroke(Color.ORANGERED);
            g.setLineWidth(stroke_size+1);
            g.strokeRect(pos_x+stroke_size, pos_y+stroke_size, size-stroke_size*2, size-stroke_size*2);
            //g.restore();
            g.setStroke(Color.CORNFLOWERBLUE);
            g.setLineWidth(stroke_size);
            g.strokeRect(pos_x+stroke_size/2, pos_y+stroke_size/2, size-stroke_size, size-stroke_size);
            //g.restore();
        } else if(board.getCommunication(EPlayer.PLAYER1, x, y)){
            g.setStroke(Color.CORNFLOWERBLUE);
            g.setLineWidth(stroke_size);
            g.strokeRect(pos_x+stroke_size/2, pos_y+stroke_size/2, size-stroke_size, size-stroke_size);
            //g.restore();
        } else if(board.getCommunication(EPlayer.PLAYER2, x, y)){
            g.setStroke(Color.ORANGERED);
            g.setLineWidth(stroke_size);
            g.strokeRect(pos_x+stroke_size/2, pos_y+stroke_size/2, size-stroke_size, size-stroke_size);
            //g.restore();
        }
        g.restore();
    }

    public void drawCellItem(Board board, int x, int y, int  pos_x, int pos_y, int size){
        g.save();
        g.setFill(Color.DARKBLUE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.setLineWidth(size/2);
        if(board.getUnit(x, y) != null)
            g.fillText(""+board.getUnit(x, y).getUnit().getID(), pos_x+size/3, pos_y+size/3, size);
        g.setFill(Color.PURPLE);
        if(board.getBuilding(x, y) != null)
            g.fillText(""+board.getBuilding(x, y).getBuilding().getID(), pos_x+2*size/3, pos_y+2*size/3, size);
        g.restore();
    }
}
