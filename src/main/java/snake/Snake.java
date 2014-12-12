package snake;

public class Snake {
	private int size = 1;
	private XY location = new XY(0, 0);
	private Direction direction = Direction.UP;

	public int size() {
		return size;
	}

	public void feed() {
		size++;
	}

	public void turn(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public XY getHeadLocation() {
		return location;
	}

	public void move() {
		location = location.add(direction.getXY());
	}

	public XY getTailLocation(int i) {
		return null;
	}

	public boolean isAlive() {
		return false;
	}

	public void moveAndFeed() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented");
		
	}

	public Iterable<XY> getSegments() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented");
		
	}
}
