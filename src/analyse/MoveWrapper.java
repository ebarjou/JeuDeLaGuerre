package analyse;

import ruleEngine.Coordinates;

public class MoveWrapper {
    public Coordinates sourceCoordinates;
    public Coordinates targetCoordinates;

    public MoveWrapper(Coordinates sourceCoordinates, Coordinates targetCoordinates) {
        this.sourceCoordinates = sourceCoordinates;
        this.targetCoordinates = targetCoordinates;
    }

    public Coordinates getSourceCoordinates() {
        return sourceCoordinates;
    }

    public Coordinates getTargetCoordinates() {
        return targetCoordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MoveWrapper)) {
            return false;
        }

        MoveWrapper o = (MoveWrapper) obj;
        return o.getSourceCoordinates().getX() == sourceCoordinates.getX() &&
                o.getSourceCoordinates().getY() == sourceCoordinates.getY() &&
                o.getTargetCoordinates().getX() == targetCoordinates.getX() &&
                o.getTargetCoordinates().getY() == targetCoordinates.getY();
    }
}
