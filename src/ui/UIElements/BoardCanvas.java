package ui.UIElements;

import analyse.EMetricsMapType;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ui.IntLetterConverter;

class BoardCanvas extends Canvas {

    private static final Color NORTH_COLOR_LIGHT = Color.rgb(200, 200, 255);
    private static final Color SOUTH_COLOR_LIGHT = Color.rgb(255, 200, 200);
    private static final int FONT_SIZE = 14;
    private static final int MARGIN = 1;

    private int coords_width;
    private int dx = 0, dy = 0;
    private int board_width, board_height;
    private GraphicsContext g;
    private int caseSize;
    private GameState gameState;

    private EMetricsMapType targetMetricsMapType;
    private EMetricsMapType currentMetricsMapType;
    private double[][][] metrics;
    private boolean displayMetrics[];
    private boolean displayMetricsNumbers;

    BoardCanvas(int w, int h, int coords_width, TextField textField) {
        super(w + coords_width, h + coords_width);
        this.caseSize = 0;
        this.currentMetricsMapType = null;
        this.targetMetricsMapType = EMetricsMapType.COMMUNICATION_MAP;
        this.coords_width = coords_width;
        this.board_height = (int) (getHeight() - coords_width);
        this.board_width = (int) (getWidth() - coords_width);
        this.metrics = new double[2][][];
        this.displayMetrics = new boolean[2];
        displayMetrics[0] = true;
        displayMetrics[1] = true;
        displayMetricsNumbers = false;

        g = this.getGraphicsContext2D();
        g.setLineWidth(2);
        g.setFont(new Font(FONT_SIZE));
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);

        this.setOnMouseClicked(new ClickHandler(textField));
        draw(null);
    }

    private void refreshStateAndMetrics(GameState newGameState) {
        boolean refreshMetrics = false;
        if (gameState == null && newGameState == null) return;
        if (newGameState != null) { //refresh board with the new gameState
            this.gameState = newGameState;
            refreshMetrics = true;
        }
        if (gameState != null && targetMetricsMapType != null && currentMetricsMapType != targetMetricsMapType) {
            refreshMetrics = true;
        }
        if (refreshMetrics) {
            this.metrics[0] = targetMetricsMapType.getMethod().compute(gameState, EPlayer.values()[0]);
            this.metrics[1] = targetMetricsMapType.getMethod().compute(gameState, EPlayer.values()[1]);
            currentMetricsMapType = targetMetricsMapType;
        }
    }

    /**
     * @param newGameState Board to be displayed
     *                     Refresh the canvas according to the given Board.
     */
    void draw(GameState newGameState) {
        this.board_height = (int) (getHeight() - coords_width);
        this.board_width = (int) (getWidth() - coords_width);
        refreshStateAndMetrics(newGameState);
        dx = 0;
        dy = 0;

        //Fill background
        g.setFill(Color.LIGHTGREY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (this.gameState != null) {

            if (getWidth() / gameState.getWidth() > getHeight() / gameState.getHeight()) {
                caseSize = (int) Math.floor(board_height / (double) gameState.getHeight());
                dx = (int) ((getWidth() - (caseSize * gameState.getWidth() + coords_width)) / 2.0);
            } else {
                caseSize = (int) Math.floor(board_width / (double) gameState.getWidth());
                dy = (int) ((getHeight() - (caseSize * gameState.getHeight() + coords_width)) / 2.0);
            }

            //draw canvas background
            g.setFill(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

            //Display letters and numbers around the board
            printCasesHIndex(gameState, dx + 35, dy + coords_width / 2, caseSize);
            printCasesVIndex(gameState, dx + coords_width / 2, dy + 35, caseSize);
            dx += coords_width;
            dy += coords_width;

            //draw board background
            g.setFill(Color.BLACK);
            g.fillRect(dx, dy, caseSize * gameState.getWidth(), caseSize * gameState.getHeight());

            //draw individual cell background
            for (int j = 0; j < gameState.getHeight(); ++j) {
                for (int i = 0; i < gameState.getWidth(); ++i) {
                    drawCellBackground(gameState, i, j,
                            dx + MARGIN + i * caseSize,
                            dy + MARGIN + j * caseSize,
                            caseSize - MARGIN * 2);
                }
            }

            //draw individual cell items
            for (int j = 0; j < gameState.getHeight(); ++j) {
                for (int i = 0; i < gameState.getWidth(); ++i) {
                    int x = dx + MARGIN + i * caseSize,
                            y = dy + MARGIN + j * caseSize,
                            size = caseSize - MARGIN * 2;
                    drawCellItem(gameState, i, j, x, y, size);
                }
            }

            g.setStroke(Color.ORANGERED);
            //g.strokeLine(dx, dy + (gameState.getHeight() / 2) * caseSize, dx + gameState.getWidth() * caseSize, dy + (gameState.getHeight() / 2) * caseSize);
        }
    }

    void updateHeight(double height) {
        super.setHeight(height);
        draw(gameState);
    }

    void updateWidth(double width) {
        super.setWidth(width);
        draw(gameState);
    }

    private void drawCellBackground(GameState gameState, int x, int y, int pos_x, int pos_y, int size) {
        g.save();
        g.setFill(Color.IVORY);
        g.fillRect(pos_x, pos_y, size, size);
        if (metrics[0] != null && metrics[1] != null)
            switch (targetMetricsMapType) {
                //TODO: Put minimal color value
                case COMMUNICATION_MAP:
                    if (displayMetrics[0] && displayMetrics[1] && gameState.isInCommunication(EPlayer.PLAYER_NORTH, x, y) && gameState.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
                        g.setFill(SOUTH_COLOR_LIGHT);
                        g.fillRect(pos_x, pos_y, size, size);
                        g.setFill(NORTH_COLOR_LIGHT);
                        g.fillPolygon(new double[]{pos_x, pos_x + size, pos_x}, new double[]{pos_y, pos_y, pos_y + size}, 3);
                    } else if (displayMetrics[0] && gameState.isInCommunication(EPlayer.PLAYER_NORTH, x, y)) {
                        g.setFill(NORTH_COLOR_LIGHT);
                        g.fillRect(pos_x, pos_y, size, size);
                    } else if (displayMetrics[1] && gameState.isInCommunication(EPlayer.PLAYER_SOUTH, x, y)) {
                        g.setFill(SOUTH_COLOR_LIGHT);
                        g.fillRect(pos_x, pos_y, size, size);
                    }
                    break;
                default:
                    if (displayMetrics[0]) {
                        Paint p = targetMetricsMapType.getPaint(metrics[0][x][y]);
                        g.setFill(p);
                        g.fillRect(pos_x, pos_y, size, size);
                    }

                    if (displayMetrics[1]) {
                        Paint p = targetMetricsMapType.getPaint(metrics[1][x][y]);
                        g.setFill(p);
                        g.fillRect(pos_x, pos_y, size, size);
                    }

                    if (displayMetricsNumbers && displayMetrics[0]){
                        g.setFill(Color.DARKBLUE);
                        g.fillText("" + (int)metrics[0][x][y], pos_x + size/2, pos_y + size * 0.25);
                    }

                    if (displayMetricsNumbers && displayMetrics[1]){
                        g.setFill(Color.DARKRED);
                        g.fillText("" + (int)metrics[1][x][y], pos_x + size/2, pos_y + size * 0.75);
                    }
                    break;
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
        } catch (IllegalBoardCallException ignored) {
        }

        try {
            BoardDrawer.drawUnit(g, gameState.getUnitType(x, y), gameState.getUnitPlayer(x, y), pos_x, pos_y, size);
        } catch (IllegalBoardCallException ignored) {
        }
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

    public void setDisplayMetrics(int i, boolean display) {
        this.displayMetrics[i] = display;
    }

    public void setDisplayMetricsNumbers(boolean display) {
        this.displayMetricsNumbers = display;
    }

    public void setMetricsMapType(EMetricsMapType type) {
        this.targetMetricsMapType = type;
    }

    private class ClickHandler implements EventHandler<MouseEvent> {
        TextField textField;

        ClickHandler(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (caseSize <= 0 || textField == null) return;
            if (mouseEvent.getX() < dx || mouseEvent.getX() > dx + caseSize * gameState.getWidth()
                    || mouseEvent.getY() < dy || mouseEvent.getY() > dy + caseSize * gameState.getHeight())
                return;

            int col = (int) ((mouseEvent.getX() - dx) / caseSize);
            int row = (int) ((mouseEvent.getY() - dy) / caseSize);
            textField.setText(textField.getText() + " " + IntLetterConverter.getLettersFromInt(row) + (col + 1));
        }
    }
}
