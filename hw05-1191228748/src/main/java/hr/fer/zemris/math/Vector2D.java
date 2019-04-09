/**
 * 
 */
package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Vector2D Class {@code Vector2D} represents a vector in a 2D plane.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Vector2D {

	/**
	 * x value
	 */
	private double x;

	/**
	 * y value
	 */
	private double y;

	/**
	 * Precision for calling equals method
	 */
	private static final double precision = 1e-7;

	/**
	 * Constructs a vector from given x and y values
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the value of x
	 * 
	 * @return the value of x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the value of y
	 * 
	 * @return the value of y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates the vector by the given {@code offset}
	 * 
	 * @param offset offset by which the vector will be translated
	 * @throws NullPointerException if {@code offset} is {@code null}
	 */
	public void translate(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException();
		}

		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * Constructs and returns a new {@code Vector2D} that we get from translating
	 * this one by the given {@code offset}
	 * 
	 * @param offset offset by which the vector will be translated
	 * @return a new {@code Vector2D} that we get from translating this one by the
	 *         given {@code offset}
	 * @throws NullPointerException if {@code offset} is {@code null}
	 */
	public Vector2D translated(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException();
		}
		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * Rotates the vector by the given {@code angle} in radians.
	 * 
	 * @param angle angle in radians by which the vector will be rotated
	 */
	public void rotate(double angle) {
		double newX = x * Math.cos(angle) - y * Math.sin(angle);
		y = x * Math.sin(angle) + y * Math.cos(angle);
		x = newX;
	}

	/**
	 * Constructs and returns a new {@code Vector2D} from rotating this vector by
	 * the given {@code angle} in radians.
	 * 
	 * @param angle in radians angle by which the vector will be rotated
	 * @return new {@code Vector2D} from rotating this vector by the given
	 *         {@code angle}.
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}

	/**
	 * Scales the vector by the given {@code scale}
	 * 
	 * @param scaler scale by which the vector will be scaled
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Constructs and returns a new {@code Vector2D} from scaling this vector by the
	 * given {@code scaler}.
	 * 
	 * @param scaler scale by which the vector will be scaled
	 * @return new {@code Vector2D} from scaling this vector by the given
	 *         {@code scaler}.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Copies this vector in to a new {@code Vector2D} object and returns it.
	 * 
	 * @return new {@code Vector2D} object that is a copy of this one
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
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
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		return Math.abs(x - other.x) < precision && Math.abs(y - other.y) < precision;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
