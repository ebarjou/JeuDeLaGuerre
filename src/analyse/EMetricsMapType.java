package analyse;

import analyse.mapMethods.IMetricsMapMethod;
import analyse.mapMethods.AttackMapMethod;
import analyse.mapMethods.ImpreciseRangeMapMethod;
import analyse.mapMethods.RangeMapMethod;

public enum EMetricsMapType {

    ATTACK_MAP(new AttackMapMethod()),
    RANGE_MAP_1M(new RangeMapMethod(1)),
    RANGE_MAP_FAST(new ImpreciseRangeMapMethod());


    private IMetricsMapMethod method;

    EMetricsMapType(IMetricsMapMethod metricMethod){
        this.method = metricMethod;
    }

    public IMetricsMapMethod getMethod() {
        return method;
    }
}
