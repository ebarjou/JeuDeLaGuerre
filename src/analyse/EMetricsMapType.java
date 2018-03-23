package analyse;

import analyse.mapMethods.*;
import ui.UIElements.IDrawableInfo;

public enum EMetricsMapType {
	COMMUNICATION_MAP("Communication map", new CommunicationMapMethod()),

    STATIC_ATTACK_MAP("Static Attack Map", new StaticAttackMapMethod()),
    ATTACK_MAP_1M("Attack map using 1 unit (slow)", new AttackMapMethod(1)),
    ATTACK_MAP_FAST("Imprecise Attack map using all units (fast)", new ImpreciseAttackMapMethod()),

	RANGE_MAP_1M("Range map using 1 unit (slow)", new RangeMapMethod(1)),
	RANGE_MAP_FAST("Imprecise Range map using all units (fast)", new ImpreciseRangeMapMethod());




	private final String mapName;
    private final IMetricsMapMethod method;
    private final IDrawableInfo drawMethod;
    private final static int maxIndex = 7;

    EMetricsMapType(String mapName, IMetricsMapMethod metricMethod){
        this.mapName = mapName;
    	this.method = metricMethod;
        this.drawMethod = metricMethod;
	}

	public static int getMaxIndex(){
    	return maxIndex;
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

	public static EMetricsMapType getType(int i){
    	switch (i){
			case 0 : return COMMUNICATION_MAP;
			case 2 : return STATIC_ATTACK_MAP;
			case 3 : return ATTACK_MAP_1M;
			case 4 : return ATTACK_MAP_FAST;
			case 6 : return RANGE_MAP_1M;
			case 7 : return RANGE_MAP_FAST;
			default: return null;
		}
	}
}
