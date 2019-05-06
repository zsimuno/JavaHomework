package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Represents a 3D vector with x, y and z points.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Vector3 {

	/**
	 * X value.
	 */
	private double x;
	/**
	 * Y value.
	 */
	private double y;
	/**
	 * Z value.
	 */
	private double z;

	/**
	 * Precision of the equals method
	 */
	private static final double precision = 1e-6;

	/**
	 * Constructs a new {@code Vector3} object from given coordinates.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param z z coordinate.
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Norm of this vector.
	 * 
	 * @return Norm of this vector.
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	/**
	 * This vector normalized.
	 * 
	 * @return This vector normalized.
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(this.x / norm, this.y / norm, this.z / norm);
	}

	/**
	 * Creates a new {@link Vector3} that is {@code this} + {@code Vector3}.
	 * 
	 * @param other {@code Vector3} that is to be added to {@code this}
	 * @return new {@link Vector3} that is {@code this} + {@code other}
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Creates a new {@link Vector3} that is {@code this} - {@code Vector3}.
	 * 
	 * @param other {@code Vector3} that is to be subtracted from {@code this}
	 * @return new {@link Vector3} that is {@code this} - {@code other}
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * Creates a new {@link Vector3} that is dot product of {@code this} and
	 * {@code Vector3}.
	 * 
	 * @param other other {@code Vector3}.
	 * @return new {@link Vector3} that is dot product of {@code this} and
	 *         {@code Vector3}.
	 */
	public double dot(Vector3 other) {
		return this.x * other.x + this.y * other.y;
	}

	/**
	 * Creates a new {@link Vector3} that is vector product of {@code this} and
	 * {@code Vector3}.
	 * 
	 * @param other other {@code Vector3}.
	 * @return new {@link Vector3} that is vector product of {@code this} and
	 *         {@code Vector3}.
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z,
				this.x * other.y - this.y * other.x);
	}

	/**
	 * Scales this vector with the given scale.
	 * 
	 * @param s factor of scaling.
	 * @return {@code Vector3} object scaled with the given factor.
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}

	/**
	 * Cosine of the angle between this vector and {@code other}.
	 * 
	 * @param other other {@code Vector3}.
	 * @return Cosine of the angle between this vector and {@code other}.
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * This vector to array with three elements.
	 * 
	 * @return this vector as double array with three elements.
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector3))
			return false;
		Vector3 other = (Vector3) obj;
		return x - other.x < precision && y - other.y < precision && z - other.z < precision;
	}

}
