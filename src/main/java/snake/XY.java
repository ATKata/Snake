package snake;

public class XY {

	public final int x;
	public final int y;

	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public XY add(XY direction) {
		return new XY(this.x + direction.x, this.y + direction.y);
	}
}
