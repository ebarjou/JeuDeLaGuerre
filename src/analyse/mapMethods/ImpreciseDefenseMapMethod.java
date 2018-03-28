package analyse.mapMethods;

import analyse.EMetricsMapType;
import javafx.scene.paint.Paint;

public class ImpreciseDefenseMapMethod extends AbstractImpreciseMapMethod {

    public ImpreciseDefenseMapMethod() {
        super(false);
    }

    @Override
    public Paint getPaint(double mapValue) {
        return EMetricsMapType.ATTACK_MAP_STATIC.getPaint(mapValue);
    }
}
