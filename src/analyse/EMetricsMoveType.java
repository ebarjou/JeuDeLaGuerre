package analyse;

import analyse.moveMethods.AllAvailableMovesMethod;
import analyse.moveMethods.IMetricsMoveMethod;

public enum EMetricsMoveType {
    ALL_AVAILABLE_MOVES(new AllAvailableMovesMethod());

    private final IMetricsMoveMethod method;

    EMetricsMoveType(IMetricsMoveMethod method) {
        this.method = method;
    }

    public IMetricsMoveMethod getMethod() {
        return method;
    }
}
