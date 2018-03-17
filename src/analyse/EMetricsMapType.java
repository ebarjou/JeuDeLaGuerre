package analyse;

import analyse.metricsMethods.IMetricsMapMethod;
import analyse.metricsMethods.AttackMapMethod;

public enum EMetricsMapType {

    ATTACK_MAP(new AttackMapMethod());


    private IMetricsMapMethod method;

    EMetricsMapType(IMetricsMapMethod metricMethod){
        this.method = metricMethod;
    }

    public IMetricsMapMethod getMethod() {
        return method;
    }
}
