package snake;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SnakeTest {

	private Snake snake;

	@Before
	public void setUp() {
		snake = new Snake();
	}

	@Test
	public void testSnakeHasInitialSizeOf1() {
		assertEquals(1, snake.size());
	}

	@Test
	public void testSnakeShouldGrowBy1WhenFed() {
		List<XY> segments = new ArrayList<XY>();
		segments.add(new XY(0, 0));
		segments.add(new XY(0, -1));
		snake = new Snake(new XY(0, 0), Direction.UP, segments, new Food());
		assertEquals(2, snake.size());
	}

	@Test
	public void testSnakeShouldStartAtOrigin() {
		assertEquals(0, snake.getHeadLocation().x);
		assertEquals(0, snake.getHeadLocation().y);
	}

	@Test
	public void testSnakeShouldInitiallyFaceUpwards() {
		assertEquals(Direction.UP, snake.getDirection());
	}

	@Test
	public void testSnakeShouldMove() throws GameOverException {
		snake.move(); // Move Upwards
		assertEquals(0, snake.getHeadLocation().x);
		assertEquals(1, snake.getHeadLocation().y);

		snake.move(); // Move Upwards
		assertEquals(0, snake.getHeadLocation().x);
		assertEquals(2, snake.getHeadLocation().y);
	}

	@Test
	public void testSnakeShouldTurnTurn() {
		snake.turn(Direction.RIGHT);
		assertEquals(Direction.RIGHT, snake.getDirection());

		snake.turn(Direction.DOWN);
		assertEquals(Direction.DOWN, snake.getDirection());

		snake.turn(Direction.LEFT);
		assertEquals(Direction.LEFT, snake.getDirection());

		snake.turn(Direction.UP);
		assertEquals(Direction.UP, snake.getDirection());
	}

	@Test
	public void testSnakeShouldGrowAndMoveWhenFed() throws GameOverException {
		List<XY> segments = new ArrayList<XY>();
		segments.add(new XY(0, 0));
		Food food = new Food();
		food.setLocation(new XY(0,1));
		snake = new Snake(new XY(0, 0), Direction.UP, segments, food);
		food.setSnake(snake);
		snake.move();
		assertThat(snake.getSegments(), contains(new XY(0, 1), new XY(0, 0)));
	}

	@Test(expected = GameOverException.class)
	public void testSnakeShouldDieWhenItHitsItself() throws GameOverException {
		List<XY> segments = new ArrayList<XY>();
		segments.add(new XY(0, 0));
		segments.add(new XY(0, -1));
		snake = new Snake(new XY(0, 0), Direction.UP, segments, new Food());
		snake.turn(Direction.DOWN);
		snake.move();
	}

	@Test(expected=GameOverException.class)
	public void testSnakeShouldDieWhenItHitsItsTail() throws GameOverException {
		List<XY> segments = new ArrayList<XY>();
		segments.add(new XY(0, 0));
		segments.add(new XY(0, -1));
		segments.add(new XY(0, -2));
		segments.add(new XY(0, -3));
		snake = new Snake(new XY(0, 0), Direction.UP, segments, new Food());

		try {
			snake.turn(Direction.RIGHT);
			snake.move();

			snake.turn(Direction.DOWN);
			snake.move();
		} catch (GameOverException e) {
			fail("Snake should still be alive.");
		}

		snake.turn(Direction.LEFT);
		snake.move();

	}

}
