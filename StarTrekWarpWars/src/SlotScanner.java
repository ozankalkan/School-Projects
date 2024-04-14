/*
    This class represents a 3x3 area that allows us to scan 8 peripheral slots of a given center slot.
    
    Peripherals is going to return an array with this format for example:
    [
        Space, Space, Wall,
        Space, Player, Space,
        Tressure, Wall, Wall
    ]
*/
public class SlotScanner {
    private Coords centerCoords;
    private Object[] peripherals;

    SlotScanner(Coords centerCoords, Maze maze) {
        this.centerCoords = centerCoords;

        Object[][] mazeData = maze.getData();
        peripherals = scanPeripherals(mazeData);
    }

    private Object[] scanPeripherals(Object[][] maze) {
        Object[] peripherals = new Object[8];

        int y = centerCoords.getY();
        int x = centerCoords.getX();

        peripherals[0] = maze[y - 1][x - 1];
        peripherals[1] = maze[y - 1][x];
        peripherals[2] = maze[y - 1][x + 1];

        peripherals[3] = maze[y][x - 1];
        peripherals[4] = maze[y][x + 1];

        peripherals[5] = maze[y + 1][x - 1];
        peripherals[6] = maze[y + 1][x];
        peripherals[7] = maze[y + 1][x + 1];

        return peripherals;
    }

    public Object[] getPeripherals() {
        return this.peripherals;
    }

    public Object[] getHorizontalAndVerticalPeripherals() {
        Object[] peripherals = new Object[4];

        peripherals[0] = this.peripherals[1]; // Up
        peripherals[1] = this.peripherals[4]; // Right
        peripherals[2] = this.peripherals[6]; // Down
        peripherals[3] = this.peripherals[3]; // Left

        return peripherals;
    }
}
