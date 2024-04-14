public class Entities {
    public static final String WARP = "Warp";
    public static final String TRAP = "Trap";
    public static final String PLAYER = "Player";
    public static final String COMPUTERROBOT = "ComputerRobot";
    public static final String SPACE = "Space";
    public static final String TRESSURE = "Tressure";
    public static final String WALL = "Wall";

    public static boolean isCollectible(Object peripheral) {
        String collidedClass = Utils.getClassNameOfObj(peripheral);

        switch (collidedClass) {
            case TRESSURE:
                return true;
            case WARP:
                return true;
            case TRAP:
                return true;
        }

        return false;
    }
}
