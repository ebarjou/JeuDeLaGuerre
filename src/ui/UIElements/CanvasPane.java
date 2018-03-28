package ui.UIElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

class CanvasPane extends Pane {
    private final BoardCanvas canvas;

    public CanvasPane(double width, double height, BoardCanvas canvas) {
        this.canvas = canvas;
        setMinWidth(width);
        setMinHeight(height);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);
        getChildren().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = snapSize(getWidth()) - x - snappedRightInset();
        final double h = snapSize(getHeight()) - y - snappedBottomInset();
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.updateWidth(w);
        canvas.updateHeight(h);
    }
}