package analyse.mapMethods;

import analyse.EMetricsMapType;
import javafx.scene.paint.Paint;

public class DeepAttackMapMethod extends AbstractDeepMapMethod{

    public DeepAttackMapMethod(int deep) {
        super(true, deep);
    }

    @Override
    public Paint getPaint(double mapValue) {
        return EMetricsMapType.ATTACK_MAP_STATIC.getPaint(mapValue);
    }
}
