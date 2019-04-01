/**
 * 
 */
package hr.fer.zemris.math;

/**
 * TODO javadoc Vector2D
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Vector2D {

	private double x, y;

	/**
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param offset
	 */
	public void translate(Vector2D offset) {
		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * @param offset
	 * @return
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * @param angle
	 */
	public void rotate(double angle) {
		x = x * Math.cos(angle) - y * Math.sin(angle);
		y = x * Math.sin(angle) + y * Math.cos(angle);
	}

	/**
	 * @param angle
	 * @return
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}

	/**
	 * TODO Ima li smisla?
	 * @param scaler
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * @param scaler
	 * @return
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * @return
	 */
	public Vector2D copy() {
		return new Vector2D(x, y); // TODO dobro?
	}
}
