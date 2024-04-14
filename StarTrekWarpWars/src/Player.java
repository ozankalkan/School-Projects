
public class Player {
	private int energy;
	private int score;
	private int lifePoints;
	private Backpack backpack;
	private Coords coords;
	private int velocity;
	public final static char SYMBOL = 'P';

	public Player() {
		super();
		this.energy = 50;
		this.score = 0;
		this.lifePoints = 5;
		this.backpack = new Backpack();
		this.coords = Utils.findEmptyPosition();
		this.velocity = 2;
	}
	
	public Backpack getBackpack() {
		return backpack;
	}

	public void setBackpack(Backpack backpack) {
		this.backpack = backpack;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
}
