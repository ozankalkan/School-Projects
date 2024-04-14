
public class Tressure {
    public char SYMBOL;
    private Coords coords;
    private boolean isTrapped = false;
    private boolean isMoved = false;
    private int scorePoint;
    public final static boolean isCollidable = true;
    
    Tressure(char type, Coords coords) {
        SYMBOL = type;
        scorePoint = calculateScorePoint();
        this.coords = coords;
    }

    public boolean getIsMoved() {
        return this.isMoved;
    }

    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }
    
    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords, Maze maze) {
        this.coords = coords;
    }

    public void setTrapped(boolean isTrapped) {
        this.isTrapped = isTrapped;
    }

    public boolean getIsTrapped() {
        return isTrapped;
    }

    public int getScorePoint() {
        return scorePoint;
    }

    public void setScorePoint(int scorePoint) {
        this.scorePoint = scorePoint;
    }

    public int calculateScorePoint() {
        switch (SYMBOL) {
            case '1':
                return 1;
            case '2':
                return 5;
            case '3':
                return 15;
            case '4':
                return 50;
            case '5':
                return 150;
        }
        return 0;
    }

    public static Tressure findHighestScoredTressure(Object[] peripherals) {
        Tressure highestScoredTressure = null;

        for (Object peripheral : peripherals) {
            if (peripheral instanceof Tressure) {
                Tressure tressure = (Tressure) peripheral;
                if (highestScoredTressure == null) {
                    highestScoredTressure = tressure;
                }

                if (highestScoredTressure.getScorePoint() < tressure.getScorePoint()) {
                    highestScoredTressure = tressure;
                }
            }
        }

        return highestScoredTressure;
    }
}
