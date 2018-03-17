package ui;

import analyse.EMetricsMapType;
import analyse.MetricsModule;
import game.EPlayer;
import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

class BoardCanvas extends Canvas {

    private static final Color NORTH_COLOR_LIGHT = Color.rgb(200, 200, 255);
    private static final Color SOUTH_COLOR_LIGHT = Color.rgb(255, 200, 200);
    private static final int FONT_SIZE = 14;
    private static final int MARGIN = 1;
    private static final int COMM_STROKE_SIZE = 2;

    private int indicesWidth;
    private int indicesHeight;
    private GraphicsContext g;
    private int caseSize;


    BoardCanvas(int w, int h, int indicesW, int indicesH, TextField textField) {
        super(w + indicesW, h + indicesH);
        indicesWidth = indicesW;
        indicesHeight = indicesH;
        caseSize = 0;

        g = this.getGraphicsContext2D();
        g.setLineWidth(2);
        g.setFont(new Font(FONT_SIZE));
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);

        this.setOnMouseClicked(new ClickHandler(textField));

        draw(null);
    }

    /**
     * @param gameState Board to be displayed
     * Refresh the canvas according to the given Board.
     */
    void draw(GameState gameState) {

        g.setFill(Color.LIGHTGREY);
        //Fill background
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameState != null) {
            caseSize = (int) (getWidth() / (gameState.getWidth()));

            //Display letters and numbers around the board
            printCasesHIndex(gameState, 35, indicesHeight / 2, caseSize);
            printCasesVIndex(gameState, indicesWidth / 2, 35, caseSize);

            //draw board background
            g.setFill(Color.BLACK);
            g.fillRect(indicesWidth, indicesHeight, getWidth()-indicesWidth,  getHeight()-indicesHeight);

            //draw individual cell background
            for (int j = 0; j < gameState.getHeight(); ++j) {
                for (int i = 0; i < gameState.getWidth(); ++i) {
                    drawCellBackground(gameState, i, j,
                            MARGIN + i * caseSize + indicesWidth,
                            MARGIN + j * caseSize + indicesHeight,
                            caseSize - MARGIN * 2);
                }
            }

            //draw individual cell items
            for (int j = 0; j < gameState.getHeight(); ++j) {
                for (int i = 0; i < gameState.getWidth(); ++i) {
                    int x = MARGIN + i * caseSize + indicesWidth, y = MARGIN + j * caseSize + indicesHeight, size = caseSize - MARGIN * 2;
                    drawCellItem(gameState, i, j, x, y, size);
                }
            }

            g.setStroke(Color.ORANGERED);
            g.strokeLine(indicesWidth, (gameState.getHeight() / 2) * caseSize + indicesHeight, gameState.getWidth() * caseSize + indicesWidth, gameState.getHeight() * caseSize / 2 + indicesHeight);

            //metrics display (to be removed)
            try {
				double[][] stats = MetricsModule.getInfoMap(EMetricsMapType.ATTACK_MAP, gameState, EPlayer.PLAYER_NORTH);
                printAttack(stats, caseSize);
            }catch (Exception e){

            }
        }

    }

    private void drawCellBackground(GameState gameState, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.IVORY);
        g.fillRect(pos_x, pos_y, size, size);
        if (gameState.isInCommunication(EPlayer.PLAYER_NORTH, x, y) && gameState.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
            g.setFill(SOUTH_COLOR_LIGHT);
            g.fillRect(pos_x, pos_y, size, size);
            g.setFill(NORTH_COLOR_LIGHT);
            g.fillPolygon(new double[] {pos_x, pos_x + size, pos_x}, new double[] {pos_y, pos_y, pos_y + size}, 3);
        } else if (gameState.isInCommunication(EPlayer.PLAYER_NORTH, x, y)) {
            g.setFill(NORTH_COLOR_LIGHT);
            g.fillRect(pos_x, pos_y, size, size);
        } else if (gameState.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
            g.setFill(SOUTH_COLOR_LIGHT);
            g.fillRect(pos_x, pos_y, size, size);
        }
        g.restore();
    }

    private void drawCellItem(GameState gameState, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.DARKBLUE);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.setLineWidth(size / 2);

        try {
            BoardDrawer.drawBuilding(g, gameState.getBuildingType(x, y), gameState.getBuildingPlayer(x, y), pos_x, pos_y, size, MARGIN);
        } catch (IllegalBoardCallException e) {}

        try {
            BoardDrawer.drawUnit(g, gameState.getUnitType(x, y), gameState.getUnitPlayer(x, y), pos_x, pos_y, size);
        } catch (IllegalBoardCallException e) {}
        g.restore();
    }

    private void printCasesHIndex(GameState gameState, int offsetx, int offsety, int caseSize) {
        g.save();
        g.setFill(Color.BLACK);
        for (int i = 0; i < gameState.getWidth(); ++i) {
            g.fillText("" + (i + 1), offsetx + i * caseSize, offsety, 20);
        }
        g.restore();
    }

    private void printCasesVIndex(GameState gameState, int offsetx, int offsety, int caseSize) {
        g.save();
        g.setFill(Color.BLACK);
        for (int j = 0; j < gameState.getHeight(); ++j) {
            String s = IntLetterConverter.getLettersFromInt(j);
            g.fillText(s, offsetx, offsety + j * caseSize, 20);
        }
        g.restore();
    }

    //quick implementation example
    private void printAttack(double[][] map, int size){
        for(int i = 0; i < map.length; ++i){
            for (int j = 0; j < map[i].length; ++j){
                g.fillText("" + (int)map[i][j], (i+1) * size, (j+1) * size);
            }
        }
    }

    private class ClickHandler implements EventHandler<MouseEvent> {
        TextField textField;

        public ClickHandler(TextField textField){
            this.textField = textField;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if(caseSize <= 0 || textField == null) return;
            int col = (int)((mouseEvent.getX()-indicesWidth)/caseSize);
            int row = (int)((mouseEvent.getY()-indicesHeight)/caseSize);

            textField.setText(textField.getText() + " " + IntLetterConverter.getLettersFromInt(row) + (col+1));
        }
    }
}
