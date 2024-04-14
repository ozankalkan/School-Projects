
public class Maze {
	public static final char[][] MAZE_MAP = ReadFile.maze();
	private Object[][] data = new Object[23][55];

	Maze(Player player) {
		for (int row = 0; row < MAZE_MAP.length; row++) {
			for (int col = 0; col < MAZE_MAP[0].length; col++) {
				if (MAZE_MAP[row][col] == Player.SYMBOL) {
					data[row][col] = player;
					Utils.print(col, row, Player.SYMBOL);
				} else

				if (MAZE_MAP[row][col] == Wall.SYMBOL) {
					Wall wall = new Wall();
					data[row][col] = wall;
					Utils.print(col, row, Wall.SYMBOL);
				} else {
					Space space = new Space(col, row);
					data[row][col] = space;
					Utils.print(col, row, Space.SYMBOL);
				}
			}
		}

		// Set player
		int playerYCoord = player.getCoords().getY();
		int playerXCoord = player.getCoords().getX();
		data[playerYCoord][playerXCoord] = player;
		Utils.print(playerXCoord, playerYCoord, Player.SYMBOL);

		// Set an initial computer robot so that the maze will start by
		// at least 1 computer robot in it
		Coords robotCoords = Utils.findEmptyPosition();
		ComputerRobot robot = new ComputerRobot(robotCoords);
		int robotYCoord = robotCoords.getY();
		int robotXCoord = robotCoords.getX();
		data[robotYCoord][robotXCoord] = robot;
		Utils.print(robotXCoord, robotYCoord, ComputerRobot.SYMBOL);
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	public void updateSlot(Object newObject) {
		switch (Utils.getClassNameOfObj(newObject)) {
			case Entities.TRESSURE:
				Tressure tressure = (Tressure) newObject;
				data[tressure.getCoords().getY()][tressure.getCoords().getX()] = newObject;
				break;
			case Entities.PLAYER:
				Player player = (Player) newObject;
				data[player.getCoords().getY()][player.getCoords().getX()] = newObject;
				break;
			case Entities.SPACE:
				Space space = (Space) newObject;
				data[space.getCoords().getY()][space.getCoords().getX()] = newObject;
				break;
			case Entities.TRAP:
				Trap trap = (Trap) newObject;
				data[trap.getCoords().getY()][trap.getCoords().getX()] = newObject;
				break;
			case Entities.WARP:
				Warp warp = (Warp) newObject;
				data[warp.getCoords().getY()][warp.getCoords().getX()] = newObject;
				break;
			case Entities.COMPUTERROBOT:
				ComputerRobot computerRobot = (ComputerRobot) newObject;
				data[computerRobot.getCoords().getY()][computerRobot.getCoords().getX()] = newObject;
				break;
		}
	}

	public void moveAllTressuresRandomly() {
		// Iterate through maze to move every tressure.
		for (Object[] row : data) {
			for (Object mazeElement : row) {
				if (mazeElement instanceof Tressure) {
					Tressure tressure = (Tressure) mazeElement;
					if (tressure.SYMBOL == '4' || tressure.SYMBOL == '5') {
						if (tressure.getIsTrapped() || tressure.getIsMoved())
							continue;

						Coords prevCoords = tressure.getCoords();
						int prevX = prevCoords.getX(), prevY = prevCoords.getY();
						Coords newCoords = findRandomSpaceForSlot(tressure.getCoords());

						Tressure newTressure = new Tressure(tressure.SYMBOL, newCoords);
						newTressure.setIsMoved(true);
						updateSlot(newTressure);
						updateSlot(new Space(prevX, prevY));
					}
				}
			}
		}

		// Iterate through maze to set every single tressures "isMoved" attribute
		// to "false".
		for (Object[] row : data) {
			for (Object slot : row) {
				if (slot instanceof Tressure) {
					Tressure tressure = (Tressure) slot;
					tressure.setIsMoved(false);
					updateSlot(tressure);
				}
			}
		}
	}

	public Coords findRandomSpaceForSlot(Coords coord) {
		int x = coord.getX(), y = coord.getY();
		int randomDirection = Utils.getRandomNumber(1, 4);

		while (true) {
			Object selectedObject = data[y - 1][x];

			switch (randomDirection) {
				case 1:
					if (y == 0)
						break;
					selectedObject = data[y - 1][x];
					break;
				case 2:
					if (x == 54)
						break;
					selectedObject = data[y][x + 1];
					break;
				case 3:
					if (y == 22)
						break;
					selectedObject = data[y + 1][x];
					break;
				case 4:
					if (x == 0)
						break;
					selectedObject = data[y][x - 1];
					break;
				default:
					if (y == 0)
						break;
					selectedObject = data[y - 1][x];
					break;
			}

			if (selectedObject instanceof Space) {
				Space space = (Space) selectedObject;
				return space.getCoords();
			}
			randomDirection = Utils.getRandomNumber(1, 4);

			// Do not move if no empty space available around
			if (!(data[y - 1][x] instanceof Space
					|| data[y][x + 1] instanceof Space
					|| data[y + 1][x] instanceof Space
					|| data[y][x - 1] instanceof Space)) {
				return new Coords(x, y);
			}
		}
	}

	public int calculateComputerScore() {
		int computerScore = 0;

		for (Object[] row : data) {
			for (Object slot : row) {
				if (slot instanceof ComputerRobot) {
					ComputerRobot robot = (ComputerRobot) slot;
					computerScore += robot.getScore();
				}
			}
		}

		return computerScore;
	}

	public boolean isSlotEmpty(Coords coords) {
		int x = coords.getX(), y = coords.getY();
		if (this.data[y][x] instanceof Space)
			return true;
		return false;
	}
}