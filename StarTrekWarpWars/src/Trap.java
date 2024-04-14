
public class Trap {
    public static final char SYMBOL = '=';
    public static final boolean isCollidable = true;
    private boolean isUsed = false;
    private int secondsLeft = 25;
    private Coords coords;
    public static final int SCORE_POINT = 300;

    Trap(Coords coords) {
        this.coords = coords;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void handleTrappingPeripherals(Maze maze, boolean trapPeripherals) {
        SlotScanner slotScanner = new SlotScanner(coords, maze);
        Object[] peripherals = slotScanner.getPeripherals();

        for (Object peripheral : peripherals) {
            if (peripheral instanceof Tressure) {
                Tressure tressure = (Tressure) peripheral;
                if (tressure.SYMBOL == '4' || tressure.SYMBOL == '5') {
                    tressure.setTrapped(trapPeripherals);
                    maze.updateSlot(tressure);
                }
            }

            if (peripheral instanceof ComputerRobot) {
                ComputerRobot robot = (ComputerRobot) peripheral;
                robot.setTrapped(trapPeripherals);
                maze.updateSlot(robot);
            }
        }
    }
}
