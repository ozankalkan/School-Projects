import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    public static char[][] maze() {
        char[][] maze = new char[23][55];

        File reader = new File("./src/maze.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(reader);
            for (int y = 0; y < 23; y++) {
                String currLine = scanner.nextLine();

                for (int x = 0; x < 55; x++) {
                    maze[y][x] = currLine.charAt(x);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return maze;
    }
}
