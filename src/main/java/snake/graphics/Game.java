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
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import snake.Direction;
import snake.DrawableFood;
import snake.DrawableSnake;
import snake.Food;
import snake.GameModel;
import snake.GameOverException;
import snake.Snake;
import snake.XY;

public class Game extends Component implements KeyListener, GameModel, Runnable {
	private boolean gameRunning;
	private DrawableSnake snake;
	private DrawableFood food;

	private int scale = 20;
	private int width;
	private int height;
	private int xOffset;
	private int yOffset;

	private long sleepTime;
	private int score;
	private Thread animationThread;

	public Game(int width, int height) {
		init(width, height);
	}

	private void init(int width, int height) {
		this.score = 0;
		this.width = width;
		this.height = height;
		this.xOffset = width / 2;
		this.yOffset = height / 2;
		this.gameRunning = true;

		this.snake = new Snake(new XY(0, 0), Direction.LEFT, Arrays.asList(
				new XY(0, 0), new XY(0, -1)));

		this.food = new Food();

		this.food.setGameModelAndRandomiseLocation(this);
		this.snake.setGameModel(this);

		this.sleepTime = 100;

		animationThread = new Thread(this);
	}

	public void paint(Graphics g) {
		BufferedImage pixelData = new BufferedImage(width * scale, height
				* scale, BufferedImage.TYPE_INT_RGB);

		drawSnake(pixelData.getGraphics());

		drawFood(pixelData.getGraphics());

		if (!gameRunning) {
			writeGameOver(pixelData);
		}

		g.drawImage(pixelData, 0, 0, null);
		g.dispose();

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width * scale, height * scale);
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
		case 'r':
			restartGame();
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

	@Override
	public void tick() throws GameOverException {
		if (snake.move()) {
			score++;
			if (sleepTime > 0) {
				sleepTime--;
			}
		}
	}

	private void restartGame() {
		gameRunning = false;
		animationThread.interrupt();
		try {
			animationThread.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		init(width, height);
		animationThread.start();
	}

	@Override
	public void run() {
		{
			while (true) {
				try {
					Thread.sleep(sleepTime);
					if (gameRunning) {
						tick();
					}
				} catch (InterruptedException | GameOverException e) {
					gameRunning = false;
					return;
				} finally {
					repaint();
				}
			}
		}
	}

	private void drawFood(Graphics graphics) {
		XY foodXY = food.getLocation();
		if (foodXY != null) {
			int pixelX = (foodXY.x + xOffset) * scale;
			int pixelY = (-foodXY.y + yOffset) * scale;
			graphics.setColor(Color.RED);
			graphics.fillOval(pixelX, pixelY, scale, scale);
		}
	}

	private void drawSnake(Graphics graphics) {
		for (XY segment : snake.getSegments()) {
			int pixelX = (segment.x + xOffset) * scale;
			int pixelY = (-segment.y + yOffset) * scale;
			if (inBounds(pixelX, pixelY)) {
				graphics.setColor(Color.GREEN);
				graphics.fillOval(pixelX, pixelY, scale, scale);
			} else {
				gameRunning = false;
			}
		}
	}

	private void writeGameOver(BufferedImage pixelData) {
		Graphics2D graphics2d = pixelData.createGraphics();
		graphics2d.setFont(new Font("Sans Serif", Font.BOLD, 30));
		graphics2d.setColor(Color.WHITE);
		graphics2d.drawString("Game Over - Score: " + score, 50, 50);
	}

	private boolean inBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < width * scale && y < height * scale;
	}

	private void start() {
		animationThread.start();
	}

	public static void main(String[] args) throws InvocationTargetException,
			InterruptedException {
		int width = 50;
		int height = 50;

		JFrame f = new JFrame("Snake");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);

		Game game = new Game(width, height);

		SwingUtilities.invokeAndWait(() -> {
			f.add(game);
			f.addKeyListener(game);
			f.pack();
			f.setVisible(true);
		});

		game.start();
	}

}
