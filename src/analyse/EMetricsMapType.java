package analyse;

import analyse.mapMethods.*;
import javafx.scene.paint.Paint;

public enum EMetricsMapType {
	COMMUNICATION_MAP("Communication map", new CommunicationMapMethod()),

    STATIC_ATTACK_MAP("Static Attack Map", new StaticAttackMapMethod()),
    ATTACK_MAP_1M("Attack map using 1 unit (slow)", new AttackMapMethod(1)),
    ATTACK_MAP_FAST("Imprecise Attack Map (fast)", new ImpreciseAttackMapMethod()),

	RANGE_MAP_1M("Range map using 1 unit (slow)", new RangeMapMethod(1)),
	RANGE_MAP_FAST("Imprecise Range Map (fast)", new ImpreciseRangeMapMethod());

	private final String mapName;
    private final IMetricsMapMethod method;
    private final static int maxIndex = 7;

    EMetricsMapType(String mapName, IMetricsMapMethod metricMethod){
        this.mapName = mapName;
    	this.method = metricMethod;
	}

	public static int getMaxIndex(){
    	return maxIndex;
	}

    public IMetricsMapMethod getMethod() {
        return method;
    }

    public Paint getPaint(double value) {
        return method.getPaint(value);
    }

	public String getMapName() {
		return mapName;
	}

	@Override
	public String toString() {
		return mapName;
	}
}
