public class StarTrekWarpWars {
	public static final char[][] MAZE_MAP = ReadFile.maze();

	public Player player = new Player();
	private Maze maze = new Maze(player);
	private InputQueue queue = new InputQueue(maze);

	public long currentTimeMS = 0;
	private int passedTimeMS = 0;
	private boolean didPlayerMove = false;
	private boolean isItemUsed = false;

	public StarTrekWarpWars() {
		long t = System.currentTimeMillis();

		while (true) {
			if (System.currentTimeMillis() - currentTimeMS >= 500) {
				updateStates(t);

				// Check if the game is over
				if (player.getLifePoints() <= 0) {
					int finalScore = player.getScore() - maze.calculateComputerScore();
					RefreshMechanism.printGameoverScreen(finalScore);
					break;
				}
			} else {
				listenForKeyPresses();
			}
		}
	}

	private void updateStates(long t) {
		passedTimeMS += 500;

		// 3 second updates
		if (passedTimeMS % 3000 == 0)
			updateItemQueue();

		// 1 second updates
		if (passedTimeMS % 1000 == 0) {
			// Move treasures
			maze.moveAllTressuresRandomly();

			decreasePlayerEnergy();

			// Check for traps/warps that has 0 seconds left
			checkTimedOutTrapsAndWarps();

			moveComputersTowardsPlayer();
		}

		// Update UI every 0.5 seconds
		RefreshMechanism refreshMechanism = new RefreshMechanism(currentTimeMS, t, maze, queue, player);
		refreshMechanism.printGameField();
		currentTimeMS = refreshMechanism.getCurrentTime();
		didPlayerMove = true;
		isItemUsed = true;
	}

	private void listenForKeyPresses() {
		if (didPlayerMove) {
			// Listen for player movement keys
			PlayerKeyListeners playerListener = new PlayerKeyListeners(maze, player);
			playerListener.listenPlayerMove();

			// Move twice if player has enough energy
			if (player.getVelocity() == 2) {
				PlayerKeyListeners secondPlayerListener = new PlayerKeyListeners(maze, player);
				secondPlayerListener.listenPlayerMove();
			}
			didPlayerMove = false;
		}

		// Listen for item usage
		if (isItemUsed) {
			BackpackKeyListeners backpackListener = new BackpackKeyListeners(maze, player);
			backpackListener.listenItemUse();
			isItemUsed = false;
		}
	}

	private void updateItemQueue() {
		Object item = queue.popItemFromQueue();
		queue.pushQueueItem(maze);
		maze.updateSlot(item);
	}

	private void checkTimedOutTrapsAndWarps() {
		Object[] usedItems = player.getBackpack().getUsedItems();
		for (int i = 0; i < usedItems.length; i++) {
			Object item = usedItems[i];

			if (item instanceof Trap) {
				Trap trap = (Trap) item;
				int oldSeconds = trap.getSecondsLeft();
				trap.handleTrappingPeripherals(maze, true);
				trap.setSecondsLeft(oldSeconds - 1);

				if (trap.getSecondsLeft() == 0) {
					trap.handleTrappingPeripherals(maze, false);
					player.getBackpack().setUsedItems(Utils.removeObjectFromArray(usedItems, i));
					maze.updateSlot(new Space(trap.getCoords().getX(), trap.getCoords().getY()));
				}
			}

			if (item instanceof Warp) {
				Warp warp = (Warp) item;
				int oldSeconds = warp.getSecondsLeft();
				warp.setSecondsLeft(oldSeconds - 1);
				warp.collectPeripherals(maze, player);

				if (warp.getSecondsLeft() == 0) {
					player.getBackpack().setUsedItems(Utils.removeObjectFromArray(usedItems, i));
					maze.updateSlot(new Space(warp.getCoords().getX(), warp.getCoords().getY()));
				}
			}
		}
	}

	private void moveComputersTowardsPlayer() {
		Object[][] mazeData = maze.getData();

		// Iterate through maze to move every single computer robot.
		for (Object[] row : mazeData) {
			for (Object slot : row) {
				if (slot instanceof ComputerRobot) {
					ComputerRobot robot = (ComputerRobot) slot;
					if (robot.getIsTrapped() || robot.getIsMoved())
						continue;
					robot.moveTowardsPlayer(maze, player);
				}
			}
		}

		// Iterate through maze to set every single computer robots "isMoved" attribute
		// to "false".
		for (Object[] row : mazeData) {
			for (Object slot : row) {
				if (slot instanceof ComputerRobot) {
					ComputerRobot robot = (ComputerRobot) slot;
					robot.setMoved(false);
					maze.updateSlot(robot);
				}
			}
		}
	}

	private void decreasePlayerEnergy() {
		if (player.getEnergy() != 0) {
			player.setEnergy(player.getEnergy() - 1);
			player.setVelocity(player.getEnergy() == 0 ? 1 : 2);
		}
	}
}
