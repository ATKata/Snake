package snake;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Snake implements DrawableSnake {
	private Direction direction;
	private Deque<XY> segments;
	private Food food;

	public Snake() {
		this(new XY(0, 0), Direction.UP, Arrays.asList(new XY(0, 0)),
				new Food());
	}

	public Snake(XY headLocation, Direction direction, Iterable<XY> segments,
			Food food) {
		super();
		this.direction = direction;
		this.segments = new ArrayDeque<XY>();
		for (XY xy : segments) {
			this.segments.addLast(xy);
		}
		this.food = food;
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

	public void move() throws GameOverException {
		XY newHeadLocation = getHeadLocation().add(direction.getXY());
		if (isSnake(newHeadLocation)) {
			throw new GameOverException();
		}
		segments.addFirst(newHeadLocation);
		if (!food.eat(newHeadLocation)) {
			segments.removeLast();
		}
	}

	public Iterable<XY> getSegments() {
		return segments;

	}

	@Override
	public boolean isSnake(XY location) {
		return segments.contains(location);
	}
}
