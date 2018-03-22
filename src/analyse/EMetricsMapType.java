package analyse;

import analyse.mapMethods.*;
import ui.UIElements.IDrawableInfo;

public enum EMetricsMapType {
	COMMUNICATION_MAP("Communication map", new CommunicationMapMethod()),
    STATIC_ATTACK_MAP("Static Attack Map", new StaticAttackMapMethod()),
    ATTACK_MAP_1M("Attack map using 1 unit", new AttackMapMethod(1)),
    ATTACK_MAP_FAST("Imprecise Attack map using all units", new ImpreciseAttackMapMethod()),
	RANGE_MAP_1M("Range map using 1 unit", new RangeMapMethod(1)),
	RANGE_MAP_FAST("Imprecise Range map using all units", new ImpreciseRangeMapMethod());




	private final String mapName;
    private final IMetricsMapMethod method;
    private final IDrawableInfo drawMethod;

    EMetricsMapType(String mapName, IMetricsMapMethod metricMethod){
        this.mapName = mapName;
    	this.method = metricMethod;
        this.drawMethod = metricMethod;
    }



    public IMetricsMapMethod getMethod() {
        return method;
    }

    public IDrawableInfo getDrawMethod() {
        return drawMethod;
    }

	public String getMapName() {
		return mapName;
	}
}
