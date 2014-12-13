package snake;

public interface GameModel {

	public XY generateRandomCoordinate();

	public boolean eat(XY newHeadLocation);
}
