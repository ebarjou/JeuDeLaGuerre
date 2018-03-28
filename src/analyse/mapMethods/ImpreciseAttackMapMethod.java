package analyse.mapMethods;

import analyse.EMetricsMapType;
import javafx.scene.paint.Paint;

public class ImpreciseAttackMapMethod extends AbstractImpreciseMapMethod {

    public ImpreciseAttackMapMethod() {
        super(true);
    }

    @Override
    public Paint getPaint(double mapValue) {
        return EMetricsMapType.STATIC_ATTACK_MAP.getPaint(mapValue);
    }
}
