package snake;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Snake implements DrawableSnake {
	private Direction direction;
	private Deque<XY> segments;
	private GameModel gameModel;

	public Snake() {
		this(new XY(0, 0), Direction.UP, Arrays.asList(new XY(0, 0)));
	}

	public Snake(XY headLocation, Direction direction, Iterable<XY> segments) {
		super();
		this.direction = direction;
		this.segments = new ConcurrentLinkedDeque<XY>();
		for (XY xy : segments) {
			this.segments.addLast(xy);
		}
	}

	@Override
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;
	}

	public int size() {
		return segments.size();
	}

	@Override
	public void turn(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public XY getHeadLocation() {
		return segments.getFirst();
	}

	@Override
	public boolean move() throws GameOverException {
		boolean eats;
		XY newHeadLocation = getHeadLocation().add(direction.getXY());
		if (isSnake(newHeadLocation)) {
			throw new GameOverException();
		}
		segments.addFirst(newHeadLocation);
		eats = gameModel.eat(newHeadLocation);
		if (!eats) {
			segments.removeLast();
		}
		return eats;
	}

	@Override
	public Iterable<XY> getSegments() {
		return Collections.unmodifiableCollection(segments);
	}

	@Override
	public boolean isSnake(XY location) {
		return segments.contains(location);
	}
}
