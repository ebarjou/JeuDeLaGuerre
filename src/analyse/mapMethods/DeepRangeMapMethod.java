package analyse.mapMethods;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DeepRangeMapMethod extends DeepAttackMapMethod {
    public DeepRangeMapMethod(int deep) {
        super(deep);
    }

    @Override
    public Paint getPaint(double mapValue) {
        if ((int) mapValue == 0)
            return Color.rgb(0, 0, 0, 0);

        int player = (int) (((mapValue - (int) mapValue) + 0.05) * 10);

        int blue = (player == 1 ? 255 : 0);
        int red = (player == 2 ? 255 : 0);
        return Color.rgb(red, 0, blue, 0.5);
    }
}
