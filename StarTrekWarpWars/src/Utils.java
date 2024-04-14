import java.util.Random;
public class Utils {
	public static EnigmaExtended enigma = new EnigmaExtended();

	public static int getRandomNumber(int min, int max) {
		Random random = new Random();
		int value = random.nextInt(max + min) + min;

		return value;
	}

	public static Coords findEmptyPosition() {
		while (true) {
			int x = getRandomNumber(0, 54);
			int y = getRandomNumber(0, 23);

			if (StarTrekWarpWars.MAZE_MAP[y][x] == ' ') {
				return new Coords(x, y);
			}
		}
	}

	public static String getClassNameOfObj(Object object) {
		return object.getClass().getName();
	}

	public static char getSymbolOfEntity(Object object) {
		String className = getClassNameOfObj(object);

		switch (className) {
			case Entities.PLAYER:
				return Player.SYMBOL;
			case Entities.SPACE:
				return Space.SYMBOL;
			case Entities.WALL:
				return Wall.SYMBOL;
			case Entities.TRESSURE:
				Tressure tressure = (Tressure) object;
				return tressure.SYMBOL;
			case Entities.TRAP:
				return Trap.SYMBOL;
			case Entities.WARP:
				return Warp.SYMBOL;
			case Entities.COMPUTERROBOT:
				return ComputerRobot.SYMBOL;
		}

		return ' ';
	}

	public static boolean isClassCollidable(Object object) {
		String className = Utils.getClassNameOfObj(object);

		switch (className) {
			case Entities.SPACE:
				return Space.isCollidable;
			case Entities.WALL:
				return Wall.isCollidable;
			case Entities.TRESSURE:
				return Tressure.isCollidable;
			case Entities.WARP:
				Warp warp = (Warp) object;
				return Warp.isCollidable && !warp.getIsUsed();
			case Entities.TRAP:
				Trap trap = (Trap) object;
				return Trap.isCollidable && !trap.getIsUsed();
		}

		return false;
	}

	public static void setCursor(int x, int y) {
		enigma.console.getTextWindow().setCursorPosition(x, y);
	}

	public static void print(String text) {
		enigma.console.getTextWindow().output(text);
	}

	public static void print(int x, int y, char text) {
		enigma.console.getTextWindow().output(x, y, text);
	}

	public static Object[] pushObjectToArray(Object[] oldArray, Object item) {
		if (oldArray == null || oldArray.length == 0) {
			Object[] newArray = { item };
			return newArray;
		}

		Object[] newArray = new Object[oldArray.length + 1];
		for (int i = 0; i < oldArray.length; i++)
			newArray[i] = oldArray[i];
		newArray[oldArray.length] = item;
		return newArray;
	}

	public static Object[] removeObjectFromArray(Object[] oldArray, int index) {
		if (oldArray == null || oldArray.length == 0) {
			return new Object[0];
		}

		Object[] newArray = new Object[oldArray.length - 1];

		for (int i = 0; i < oldArray.length; i++) {
			if (i == index)
				continue;

			if (i > index) {
				newArray[i - 1] = oldArray[i];
			}
		}

		return newArray;
	}
}
