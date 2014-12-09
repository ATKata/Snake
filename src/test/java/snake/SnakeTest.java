package snake;

import static org.junit.Assert.assertEquals;

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
		assertEquals(0, snake.getHead().x);
		assertEquals(0, snake.getHead().y);
	}

	@Test
	public void testSnakeShouldInitiallyHaveUpwardsVelocity() {
		assertEquals(Snake.VELOCITY, snake.getVelocity().y);
		assertEquals(0, snake.getVelocity().x);
	}

	@Test
	public void testSnakeShouldMove() {
		snake.move(); // Move Upwards
		assertEquals(0, snake.getHead().x);
		assertEquals(1, snake.getHead().y);

		snake.move(); // Move Upwards
		assertEquals(0, snake.getHead().x);
		assertEquals(2, snake.getHead().y);
	}

	@Test
	public void testSnakeShouldTurnClockwise() {
		snake.turnClockwise();
		assertEquals(Snake.VELOCITY, snake.getVelocity().x);
		assertEquals(0, snake.getVelocity().y);

		snake.turnClockwise();
		assertEquals(0, snake.getVelocity().x);
		assertEquals(-Snake.VELOCITY, snake.getVelocity().y);
	}

	@Test
	public void testSnakeShouldTurnAntiClockwise() {
		snake.turnAntiClockwise();
		assertEquals(-Snake.VELOCITY, snake.getVelocity().x);
		assertEquals(0, snake.getVelocity().y);

		snake.turnAntiClockwise();
		assertEquals(0, snake.getVelocity().x);
		assertEquals(-Snake.VELOCITY, snake.getVelocity().y);
	}

	@Test
	public void testSnakeTailIs1StepBehind() {
		snake.feed(); // Generate Tail

		snake.move(); // Move Upwards
		assertEquals(0, snake.getTail(0).x);
		assertEquals(0, snake.getTail(0).y);

		snake.move(); // Move Upwards
		assertEquals(0, snake.getTail(0).x);
		assertEquals(1, snake.getTail(0).y);
	}

	@Test
	public void testSnakeTailIs1StepBehindWhenTurning() {
		snake.feed(); // Generate Tail

		snake.move(); // Move Upwards
		snake.turnClockwise(); // Rotate Clockwise
		snake.move(); // Move Right
		assertEquals(0, snake.getTail(0).x);
		assertEquals(1, snake.getTail(0).y);
	}

}
