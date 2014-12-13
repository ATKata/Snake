package snake;

public interface GameModel {

	public XY generateRandomCoordinate();

	public boolean eat(XY newHeadLocation);
	
	public void tick() throws GameOverException;
}
