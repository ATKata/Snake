package snake;

public interface DrawableFood {

	public void setLocation(XY generateRandomCoordinate);

	public XY getLocation();

	public void setGameModelAndRandomiseLocation(GameModel game);

}
