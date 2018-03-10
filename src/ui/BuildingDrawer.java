package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BuildingDrawer {

    static void drawDebordStyleFort(GraphicsContext g, int pos_x, int pos_y, int size) {
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

    static void drawDebordStylePass(GraphicsContext g, int pos_x, int pos_y, int size, int margin){
        g.setLineWidth(margin * 2);
        int caseStartOffset = margin;
        int caseEndOffset = size + margin;
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

    static void drawDebordStyleArsenal(GraphicsContext g, int pos_x, int pos_y, int size, Color color){
        g.setStroke(color);
        g.setLineWidth(2);
        double dashSize = size / 3;

        g.strokeLine(pos_x, pos_y, pos_x + size, pos_y);
        g.strokeLine(pos_x, pos_y, pos_x, pos_y + size);
        g.strokeLine(pos_x, pos_y + size, pos_x + size, pos_y + size);
        g.strokeLine(pos_x + size, pos_y, pos_x + size, pos_y + size);

        g.strokeLine(pos_x + size / 5, pos_y + size/5, pos_x + size *0.8, pos_y + size/5);
        g.strokeLine(pos_x + size/5, pos_y + size/5, pos_x + size/5, pos_y + size * 0.8);
        g.strokeLine(pos_x + size/5, pos_y + size * 0.8, pos_x + size * 0.8, pos_y + size * 0.8);
        g.strokeLine(pos_x + size * 0.8, pos_y + size/5, pos_x + size * 0.8, pos_y + size * 0.8);

        g.strokeLine(pos_x + size / 2 - dashSize/2, pos_y + size / 2, pos_x + size / 2 + dashSize/2, pos_y + size / 2);
        g.strokeLine(pos_x + size / 2, pos_y + size / 2 - dashSize/2, pos_x + size / 2, pos_y + size / 2 + dashSize/2);
    }
}
