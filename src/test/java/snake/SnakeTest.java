package snake;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;

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
		Deque<XY> segments = new ArrayDeque<XY>();
		segments.add(new XY(0,0));
		segments.add(new XY(0,-1));
		snake = new Snake(new XY(0,0), Direction.UP, segments);
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
	public void testSnakeShouldGrowAndMoveWhenFed() {
		snake.moveAndFeed();
		assertThat(snake.getSegments(), contains( new XY(0,1), new XY(0,0)));
	}
	
	@Test
	public void testSnakeInitiallyAlive() {
		assertTrue(snake.isAlive());
	}

	@Test
	public void testSnakeShouldDieWhenItHitsItself() {
		Deque<XY> segments = new ArrayDeque<XY>();
		segments.add(new XY(0,0));
		segments.add(new XY(0,-1));
		snake = new Snake(new XY(0,0), Direction.UP, segments);
		snake.turn(Direction.DOWN);
		snake.move();
		assertFalse(snake.isAlive());
	}

	@Test
	public void testSnakeShouldDieWhenItHitsItsTail() {
		Deque<XY> segments = new ArrayDeque<XY>();
		segments.add(new XY(0,0));
		segments.add(new XY(0,-1));
		segments.add(new XY(0,-2));
		segments.add(new XY(0,-3));
		snake = new Snake(new XY(0,0), Direction.UP, segments);

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
