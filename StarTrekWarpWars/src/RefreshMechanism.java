public class RefreshMechanism {
	public static EnigmaExtended enigma = new EnigmaExtended();
	private long currentTime;
	private long f;
	private Maze maze;
	private InputQueue queue;
	private Player player;

	public RefreshMechanism(long currentTime, long f, Maze maze, InputQueue queue, Player player) {
		this.currentTime = currentTime;
		this.f = f;
		this.maze = maze;
		this.queue = queue;
		this.player = player;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void printGameField() {
		// Update timer
		currentTime = (System.currentTimeMillis() - f) / 1000;
		String timeString = String.valueOf(currentTime);
		Utils.setCursor(66, 23);
		Utils.print(timeString);

		// Print maze items
		printMazeItems();

		// Update menu elements
		printBackpack();
		printInnerBackpack();
		printStats();
		printQueue();

		currentTime = System.currentTimeMillis();
	}

	private void printQueue() {
		Utils.setCursor(56, 1);
		Utils.print("Input Queue");

		for (int i = 2; i < 5; i += 2) {
			Utils.setCursor(56, i);
			Utils.print("<<<<<<<<<<<<<<<");
		}

		Utils.setCursor(56, 3);
		Utils.print(queue.getQueueString());
	}

	private void printBackpack() {
		for (int i = 0; i < 8; i++) {
			Utils.print(60, 6 + i, '|');
			Utils.print(64, 6 + i, '|');
		}

		Utils.setCursor(60, 14);
		Utils.print("+---+");

		Utils.setCursor(60, 15);
		Utils.print("P.Backpack ");
	}

	private void printStats() {
		Utils.setCursor(56, 17);
		Utils.print("P.Energy: " + player.getEnergy() + "   ");

		Utils.setCursor(56, 18);
		Utils.print("P.Score: " + player.getScore() + "    ");

		Utils.setCursor(56, 19);
		Utils.print("P.Life: " + player.getLifePoints());

		Utils.setCursor(56, 21);
		Utils.print("C.Score: " + maze.calculateComputerScore() + "    ");

		Utils.setCursor(56, 23);
		Utils.print("Time: ");
	}

	private void printMazeItems() {
		Object[][] mazeData = maze.getData();

		for (int y = 0; y < 23; y++) {
			for (int x = 0; x < 55; x++) {
				Object item = mazeData[y][x];
				Utils.print(x, y, Utils.getSymbolOfEntity(item));
			}
		}
	}

	private void printInnerBackpack() {
		Object[] items = new Object[8];
		Backpack backpack = player.getBackpack();
		int backpackSize = backpack.size();

		for (int i = 0; i < 8; i++) {
			Utils.print(62, 13 - i, ' ');
		}

		for (int i = 0; i < backpackSize; i++) {
			items[i] = backpack.pop();
		}

		for (int i = 0; i < backpackSize; i++) {
			backpack.push(items[backpackSize - 1 - i]);
			Utils.print(62, 13 - i, Utils.getSymbolOfEntity(items[backpackSize - 1 - i]));
		}
	}

	public static void printGameoverScreen(int finalScore) {
		String score = String.valueOf(finalScore);
		enigma.clearConsole();
		Utils.print("--------------------------------------\n");
		Utils.print("---------------GAME OVER--------------\n");
		Utils.print("--------------------------------------\n");
		Utils.print("-------------Score:  " + finalScore + "  ");
		for (int i = 0; i < 15 - score.length(); i++) {
			Utils.print("-");
		}
		Utils.print("\n");
		Utils.print("--------------------------------------\n");
		Utils.print("--------------------------------------\n");
		Utils.print("--------------------------------------\n");
	}
}
