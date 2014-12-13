package snake;

public interface DrawableSnake {
	public Iterable<XY> getSegments();

	public void turn(Direction direction);

	public void move() throws GameOverException;

	public boolean isSnake(XY newLocation);

	public void setGameModel(GameModel game);
}
