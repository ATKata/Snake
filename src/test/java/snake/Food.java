package snake;

public class Food {

	private XY location;
	private int width;
	private int height;
	private Snake snake;

	public Food(int width, int height) {
		this.width = width;
		this.height = height;
		location = newRandomLocation();
	}

	public Food() {
		this(100, 100);
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
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
			location = newRandomLocation();
		}
		return success;
	}

	private XY newRandomLocation() {
		XY result = null;
		while (result == null) {
			XY newLocation = new XY((int) (Math.random() * width) - width / 2,
					(int) (Math.random() * height) - height / 2);
			if (snake == null || !snake.isSnake(newLocation)) {
				result = newLocation;
			}
		}
		return result;
	}
}
