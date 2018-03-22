package analyse.mapMethods;

import analyse.EMetricsMapType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ImpreciseRangeMapMethod extends ImpreciseAttackMapMethod {
	@Override
	public Paint getPaint(double mapValue) {
		return EMetricsMapType.RANGE_MAP_1M.getDrawMethod().getPaint(mapValue);
	}
}
