package snake;

public enum Direction {
	RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0), UP(0, 1);

	private int x;
	private int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public XY getXY() {
		return new XY(x, y);
	}
}
