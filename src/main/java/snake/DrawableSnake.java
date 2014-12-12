package snake;

public interface DrawableSnake {
	Iterable<XY> getSegments();

	void turn(Direction direction);

	void move() throws GameOverException;
}
