package coloring.algorithms;

import java.util.Objects;

/**
 * Represents one pixel.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Pixel {

	/**
	 * X-coordinate of the pixel.
	 */
	public int x;

	/**
	 * Y-coordinate of the pixel.
	 */
	public int y;

	/**
	 * Constructs a new pixel from given coordinates.
	 * 
	 * @param x X-coordinate of the pixel.
	 * @param y Y-coordinate of the pixel.
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "(" + x + ", y=" + y + ")";
	}

}
