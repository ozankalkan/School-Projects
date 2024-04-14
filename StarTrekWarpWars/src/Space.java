public class Space {
	public final static char SYMBOL = ' ';
	public final static boolean isCollidable = true;
	private Coords coords;

	Space(int x, int y) {
		this.coords = new Coords(x, y);
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}
}