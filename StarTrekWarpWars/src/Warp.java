
public class Warp {
    public static final char SYMBOL = '*';
    public static final boolean isCollidable = true;
    private boolean isUsed = false;
    private int secondsLeft = 25;
    private Coords coords;
    public static final int SCORE_POINT = 300;

    Warp(Coords coords) {
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

    public void collectPeripherals(Maze maze, Player player) {
        SlotScanner slotScanner = new SlotScanner(coords, maze);
        Object[] peripherals = slotScanner.getPeripherals();

        for (Object peripheral : peripherals) {
            switch (Utils.getClassNameOfObj(peripheral)) {
                case Entities.COMPUTERROBOT:
                    ComputerRobot robot = (ComputerRobot) peripheral;
                    int x = robot.getCoords().getX(), y = robot.getCoords().getY();
                    maze.updateSlot(new Space(x, y));
                    player.setScore(player.getScore() + 300);
                    break;
                case Entities.TRESSURE:
                    Tressure tressure = (Tressure) peripheral;
                    int tressureX = tressure.getCoords().getX(), tressureY = tressure.getCoords().getY();
                    maze.updateSlot(new Space(tressureX, tressureY));
                    player.setScore(player.getScore() + tressure.getScorePoint());
                    break;
            }
        }
    }
}