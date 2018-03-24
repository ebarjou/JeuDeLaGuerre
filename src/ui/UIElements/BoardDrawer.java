package ui.UIElements;

import game.EPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

class BoardDrawer {
    private static final Color NORTH_COLOR_STRONG = Color.rgb(100, 100, 255);
    private static final Color SOUTH_COLOR_STRONG = Color.rgb(255, 100, 100);

    static void drawUnit(GraphicsContext g, EUnitData unitID, EPlayer player, int pos_x, int pos_y, int size){
        StringBuilder b = new StringBuilder();
        switch (player) {
            case PLAYER_NORTH:
                b.append("n");
                break;
            case PLAYER_SOUTH:
                b.append("s");
                break;
        }

        b.append(unitID.getID());
        drawSpriteFromPath(g, b.toString(), pos_x, pos_y, size);
    }

    static void drawBuilding(GraphicsContext g, EBuildingData buildingID, EPlayer player, int pos_x, int pos_y, int size, int margin){
        switch (buildingID) {
            case MOUNTAIN:
                g.setFill(Color.SANDYBROWN);
                g.fillRect(pos_x, pos_y, size, size);
                break;
            case PASS:
                BoardDrawer.drawDebordStylePass(g, pos_x, pos_y, size, margin);
                break;
            case FORTRESS:
                BoardDrawer.drawDebordStyleFort(g, pos_x, pos_y, size);
                break;
            case ARSENAL:
                switch (player) {
                    case PLAYER_NORTH:
                        BoardDrawer.drawDebordStyleArsenal(g, pos_x, pos_y, size, NORTH_COLOR_STRONG);
                        break;
                    case PLAYER_SOUTH:
                        BoardDrawer.drawDebordStyleArsenal(g, pos_x, pos_y, size, SOUTH_COLOR_STRONG);
                        break;
                }
                break;
            default:
                g.setFill(Color.PURPLE);
                g.fillText("" +buildingID.getID(), pos_x + 2 * size / 3, pos_y + 2 * size / 3, size);
                break;
        }
    }

    private static void drawSpriteFromPath(GraphicsContext g, String name, int pos_x, int pos_y, int size){
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

    private static void drawDebordStyleFort(GraphicsContext g, int pos_x, int pos_y, int size) {
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

    private static void drawDebordStylePass(GraphicsContext g, int pos_x, int pos_y, int size, int margin){
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

    private static void drawDebordStyleArsenal(GraphicsContext g, int pos_x, int pos_y, int size, Color color){
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

        g.strokeLine(pos_x - size / 5, pos_y - size/5, pos_x + size *1.2, pos_y - size/5);
        g.strokeLine(pos_x - size/5, pos_y - size/5, pos_x - size/5, pos_y + size * 1.2);
        g.strokeLine(pos_x - size/5, pos_y + size * 1.2, pos_x + size * 1.2, pos_y + size * 1.2);
        g.strokeLine(pos_x + size * 1.2, pos_y - size/5, pos_x + size * 1.2, pos_y + size * 1.2);

        g.strokeLine(pos_x + size / 2 - dashSize/2, pos_y + size / 2, pos_x + size / 2 + dashSize/2, pos_y + size / 2);
        g.strokeLine(pos_x + size / 2, pos_y + size / 2 - dashSize/2, pos_x + size / 2, pos_y + size / 2 + dashSize/2);
    }
}
