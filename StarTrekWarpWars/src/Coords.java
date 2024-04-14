
public class Coords {
	private int x;
	private int y;

	public Coords(int x, int y, boolean isIngameCoords) {
		super();

		if (x < 1 || x > 53 || y < 1 || y > 21) {
			throw new IllegalArgumentException("Invalid coordinates!");
		}

		this.x = x;
		this.y = y;
	}

	public Coords(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public static boolean isCoordsSame(Coords coords1, Coords coords2) {
		int x1 = coords1.getX(), y1 = coords1.getY();
		int x2 = coords2.getX(), y2 = coords2.getY();
		
		if (x1 == x2 && y1 == y2) {
			return true;
		}
		
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
