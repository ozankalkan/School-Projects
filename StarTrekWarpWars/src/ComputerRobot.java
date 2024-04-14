
public class ComputerRobot {
	public final static char SYMBOL = 'C';
	private boolean isTrapped = false;
	private boolean isMoved = false;
	private int score = 0;

	private Coords coords;

	ComputerRobot(Coords coords) {
		this.coords = coords;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

	public boolean getIsMoved() {
		return this.isMoved;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}

	public void setTrapped(boolean isTrapped) {
		this.isTrapped = isTrapped;
	}

	public boolean getIsTrapped() {
		return isTrapped;
	}

	public void moveTowardsPlayer(Maze maze, Player player) {
		Coords newCoords = null;
		SlotScanner slotScanner = new SlotScanner(coords, maze);
		Object[] peripherals = slotScanner.getHorizontalAndVerticalPeripherals();
		Tressure highestScoredTressure = Tressure.findHighestScoredTressure(peripherals);

		for (Object peripheral : peripherals) {
			if (peripheral instanceof Player) {
				Backpack backpack = player.getBackpack();
				player.setLifePoints(player.getLifePoints() - 1);
				for (int i = 0; i < 2; i++) {
					if (!backpack.isEmpty())
						backpack.pop();
				}
				return;
			}
		}

		for (Object peripheral : peripherals) {
			if (peripheral instanceof Warp) {
				Warp warp = (Warp) peripheral;
				newCoords = warp.getCoords();
				this.score += Warp.SCORE_POINT;
			}
		}

		for (Object peripheral : peripherals) {
			if (peripheral instanceof Trap) {
				Trap trap = (Trap) peripheral;
				newCoords = trap.getCoords();
				this.score += Trap.SCORE_POINT;
			}
		}

		if (highestScoredTressure != null) {
			newCoords = highestScoredTressure.getCoords();
			this.score += (highestScoredTressure.getScorePoint() * 2);
		}

		// Move to the optimal coords towards the player
		maze.updateSlot(new Space(coords.getX(), coords.getY()));
		if (newCoords == null)
			newCoords = findCoordsTowardsPlayer(player, maze);

		ComputerRobot robot = new ComputerRobot(newCoords);
		robot.setScore(this.score);
		robot.setMoved(true);
		maze.updateSlot(robot);
	}

	private Coords findCoordsTowardsPlayer(Player player, Maze maze) {
		int playerX = player.getCoords().getX(), playerY = player.getCoords().getY();
		int compX = coords.getX(), compY = coords.getY();
		int distanceX = playerX - compX, distanceY = playerY - compY;

		Coords newCoords;
		if (distanceX > 0) {
			newCoords = new Coords(compX + 1, compY);
			if (maze.isSlotEmpty(newCoords))
				return newCoords;
		}

		if (distanceX < 0) {
			newCoords = new Coords(compX - 1, compY);
			if (maze.isSlotEmpty(newCoords))
				return newCoords;
		}

		if (distanceY > 0) {
			newCoords = new Coords(compX, compY + 1);
			if (maze.isSlotEmpty(newCoords))
				return newCoords;
		}

		if (distanceY < 0) {
			newCoords = new Coords(compX, compY - 1);
			if (maze.isSlotEmpty(newCoords))
				return newCoords;
		}

		return coords;
	}
}