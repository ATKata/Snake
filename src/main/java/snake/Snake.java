package snake;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;




public class Snake {
	private Direction direction;
	private Deque<XY> segments;
	private boolean alive;

	public Snake() {
		this(new XY(0,0), Direction.UP, Arrays.asList(new XY(0,0)));
	}

	public Snake(XY headLocation, Direction direction, Iterable<XY> segments) {
		super();
		this.alive = true;
		this.direction = direction;
		this.segments = new ArrayDeque<XY>();
		for(XY xy: segments){
			this.segments.addLast(xy);
		}
	}

	public int size() {
		return segments.size();
	}


	public void turn(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public XY getHeadLocation() {
		return segments.getFirst();
	}

	public void move() {
		moveAndFeed();
		if(isAlive()){
			segments.removeLast();
		}
	}

	public void moveAndFeed() {
		XY newHeadLocation = getHeadLocation().add(direction.getXY());
		if(segments.contains(newHeadLocation)){
			setAlive(false);
		} else {
			segments.addFirst(newHeadLocation);
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
