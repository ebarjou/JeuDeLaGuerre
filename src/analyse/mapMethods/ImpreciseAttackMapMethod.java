package analyse.mapMethods;

import analyse.EMetricsMapType;
import javafx.scene.paint.Paint;

public class ImpreciseAttackMapMethod extends AbstractImpreciseMapMethod {

    public ImpreciseAttackMapMethod() {
        super(true);
    }

    @Override
    public Paint getPaint(double mapValue) {
        return EMetricsMapType.ATTACK_MAP_STATIC.getPaint(mapValue);
    }
}
