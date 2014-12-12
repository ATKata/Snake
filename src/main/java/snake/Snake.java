package snake;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;




public class Snake {
	private XY headLocation;
	private Direction direction;
	private Deque<XY> segments;
	private boolean alive;

	public Snake() {
		this(new XY(0,0), Direction.UP, new ArrayDeque<XY>(Arrays.asList(new XY(0,0))));
	}

	public Snake(XY headLocation, Direction direction, Deque<XY> segments) {
		super();
		this.alive = true;
		this.headLocation = headLocation;
		this.direction = direction;
		this.segments = segments;
	}

	public int size() {
		return segments.size();
	}

	private void feed() {
		segments.addFirst(headLocation);
	}

	public void turn(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public XY getHeadLocation() {
		return headLocation;
	}

	public void move() {
		moveAndFeed();
		if(isAlive()){
			segments.removeLast();
		}
	}

	public void moveAndFeed() {
		headLocation = headLocation.add(direction.getXY());
		if(segments.contains(headLocation)){
			setAlive(false);
		} else {
			segments.addFirst(headLocation);
		}
	}

	public XY getTailLocation(int i) {
		return null;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Iterable<XY> getSegments() {
		return segments;
		
	}
}
