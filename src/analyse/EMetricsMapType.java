package analyse;

import analyse.mapMethods.*;
import javafx.scene.paint.Paint;

public enum EMetricsMapType {
    COMMUNICATION_MAP("Communication map", new CommunicationMapMethod()),

    STATIC_ATTACK_MAP("Attack Map (static)", new StaticAttackMapMethod()),
    ATTACK_MAP_1M("Attack Map Deep 1 (slow)", new DeepAttackMapMethod(1)),
    ATTACK_MAP_FAST("Attack Map Low (fast)", new ImpreciseAttackMapMethod()),

    RANGE_MAP_1M("Range Map Deep 1 (slow)", new DeepRangeMapMethod(1)),
    RANGE_MAP_FAST("Range Map Low (fast)", new ImpreciseRangeMapMethod()),

    DEFENSE_MAP_FAST("Defense Map Low (fast)", new ImpreciseDefenseMapMethod()),

    OUTCOME_MAP_FAST("Outcome Map Low (fast)", new ImpreciseOutcomePredictionMapMethod());

    private final String mapName;
    private final IMetricsMapMethod method;

    EMetricsMapType(String mapName, IMetricsMapMethod metricMethod) {
        this.mapName = mapName;
        this.method = metricMethod;
    }

    public IMetricsMapMethod getMethod() {
        return method;
    }

    public Paint getPaint(double value) {
        return method.getPaint(value);
    }

    @Override
    public String toString() {
        return mapName;
    }
}
