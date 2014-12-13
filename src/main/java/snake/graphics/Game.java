package snake.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JFrame;

import snake.Direction;
import snake.DrawableFood;
import snake.DrawableSnake;
import snake.Food;
import snake.GameModel;
import snake.GameOverException;
import snake.Snake;
import snake.XY;

public class Game extends Component implements KeyListener, GameModel {
	private boolean gameRunning;
	private DrawableSnake snake;
	private DrawableFood food;
	private BufferedImage currentFrame;
	private int scale = 10;
	private AffineTransformOp scaleOp;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;

	public Game(DrawableSnake snake, DrawableFood food, int width, int height) {
		this.width = width;
		this.height = height;
		this.xOffset = width / 2;
		this.yOffset = height / 2;
		this.snake = snake;
		this.food = food;
		this.gameRunning = true;
		this.currentFrame = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		AffineTransform at = new AffineTransform();
		at.scale(scale, scale);
		scaleOp = new AffineTransformOp(at,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	}

	public void paint(Graphics g) {
		currentFrame = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		drawSnake();

		drawFood();

		BufferedImage pixelData = scaleOp.filter(currentFrame, null);
		
		if (!gameRunning) {
			writeGameOver(pixelData);
		}
		
		g.drawImage(pixelData, 0, 0, null);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(currentFrame.getWidth(null) * scale,
				currentFrame.getHeight(null) * scale);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'z':
			snake.turn(Direction.LEFT);
			break;
		case 'x':
			snake.turn(Direction.RIGHT);
			break;
		case 'l':
			snake.turn(Direction.UP);
			break;
		case ',':
			snake.turn(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public XY generateRandomCoordinate() {
		XY result = null;
		while (result == null) {
			XY newLocation = new XY((int) (Math.random() * width) - xOffset,
					(int) -(Math.random() * height) + yOffset);
			if (snake == null || !snake.isSnake(newLocation)) {
				result = newLocation;
			}
		}
		return result;
	}

	@Override
	public boolean eat(XY location) {
		boolean success = food.getLocation().equals(location);
		if (success) {
			food.setLocation(generateRandomCoordinate());
		}
		return success;
	}
	
	private void drawFood() {
		XY foodXY = food.getLocation();
		if (foodXY != null) {
			int pixelX = foodXY.x + xOffset;
			int pixelY = -foodXY.y + yOffset;
			currentFrame.setRGB(pixelX, pixelY, Color.RED.getRGB());
		}
	}

	private void drawSnake() {
		for (XY segment : snake.getSegments()) {
			int pixelX = segment.x + xOffset;
			int pixelY = -segment.y + yOffset;
			if (inBounds(pixelX, pixelY)) {
				currentFrame.setRGB(pixelX, pixelY, Color.WHITE.getRGB());
			} else {
				gameRunning = false;
			}
		}
	}

	private void writeGameOver(BufferedImage pixelData) {
		Graphics2D graphics2d = pixelData.createGraphics();
		graphics2d.setFont(new Font("Sans Serif", Font.BOLD, 30));
		graphics2d.setColor(Color.WHITE);
		graphics2d.drawString("Game Over", 50, 50);
		graphics2d.dispose();
	}

	private boolean inBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	private boolean isGameRunning() {
		return gameRunning;
	}

	private void setGameRunning(boolean b) {
		gameRunning = b;
	}

	public static void main(String[] args) throws InterruptedException {

		JFrame f = new JFrame("Snake");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int width = 50;
		int height = 50;

		DrawableSnake snake = new Snake(new XY(0, 0), Direction.LEFT, Arrays.asList(
				new XY(0, 0), new XY(0, -1)));
		DrawableFood food = new Food();
		Game game = new Game(snake, food, width, height);
		food.setGameModelAndRandomiseLocation(game);
		snake.setGameModel(game);

		f.add(game);
		f.addKeyListener(game);

		f.pack();
		f.setVisible(true);

		while (true) {
			Thread.sleep(100);
			if (game.isGameRunning()) {
				try {
					snake.move();
				} catch (GameOverException e) {
					game.setGameRunning(false);
				}
			}
			f.repaint();
		}
	}

}
