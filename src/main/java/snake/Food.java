package snake;

public class Food implements DrawableFood{

	private XY location;
	private GameModel gameModel;

	@Override
	public void setGameModelAndRandomiseLocation(GameModel game) {
		this.gameModel = game;
		location = gameModel.generateRandomCoordinate();
	}

	@Override
	public XY getLocation() {
		return location;
	}

	@Override
	public void setLocation(XY xy) {
		location = xy;
	}
}
