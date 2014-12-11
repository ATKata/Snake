package snake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		snake.feed();
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
	public void testSnakeShouldMove() {
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
	public void testSnakeTailIs1StepBehind() {
		snake.feed(); // Generate Tail

		assertEquals(0, snake.getTailLocation(0).x);
		assertEquals(-1, snake.getTailLocation(0).y);

		snake.move(); // Move Upwards
		assertEquals(0, snake.getTailLocation(0).x);
		assertEquals(0, snake.getTailLocation(0).y);
	}

	@Test
	public void testSnakeTailIs1StepBehindWhenTurning() {
		snake.feed(); // Generate Tail
		snake.move(); // Move Upwards
		snake.turn(Direction.RIGHT); // Change direction
		snake.move(); // Move Right

		assertEquals(0, snake.getTailLocation(0).x);
		assertEquals(1, snake.getTailLocation(0).y);
	}

	@Test
	public void testSnakeInitiallyAlive() {
		assertTrue(snake.isAlive());
	}

	@Test
	public void testSnakeShouldDieWhenItHitsItself() {
		snake.turn(Direction.DOWN);
		assertFalse(snake.isAlive());
	}

	@Test
	public void testSnakeShouldDieWhenItHitsItsTail() {
		snake.feed(); // Generate Tail
		snake.feed(); // Generate Tail
		snake.feed(); // Generate Tail
		snake.feed(); // Generate Tail

		snake.turn(Direction.RIGHT);
		snake.move();
		assertTrue(snake.isAlive());

		snake.turn(Direction.DOWN);
		snake.move();
		assertTrue(snake.isAlive());

		snake.turn(Direction.LEFT);
		snake.move();
		assertFalse(snake.isAlive());
	}

}
