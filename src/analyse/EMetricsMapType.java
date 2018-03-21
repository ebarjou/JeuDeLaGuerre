package analyse;

import analyse.mapMethods.IMetricsMapMethod;
import analyse.mapMethods.AttackMapMethod;
import analyse.mapMethods.RangeMapMethod;

public enum EMetricsMapType {

    ATTACK_MAP(new AttackMapMethod()),
    RANGE_MAP(new RangeMapMethod());


    private IMetricsMapMethod method;

    EMetricsMapType(IMetricsMapMethod metricMethod){
        this.method = metricMethod;
    }

    public IMetricsMapMethod getMethod() {
        return method;
    }
}
