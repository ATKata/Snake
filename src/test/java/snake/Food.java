package snake;

public class Food {

	private XY location;
	private GameModel gameModel;

	public void setGameModelAndRandomiseLocation(GameModel game) {
		this.gameModel = game;
		location = gameModel.generateRandomCoordinate();
	}

	public XY getLocation() {
		return location;
	}

	public void setLocation(XY xy) {
		location = xy;
	}

	public boolean eat(XY newHeadLocation) {
		boolean success = location != null && location.equals(newHeadLocation);
		if (success) {
			location = gameModel.generateRandomCoordinate();
		}
		return success;
	}
}
