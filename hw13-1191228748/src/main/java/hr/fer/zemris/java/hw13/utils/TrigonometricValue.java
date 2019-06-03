package hr.fer.zemris.java.hw13.utils;

/**
 * Calculates and stores sine and cosine value of an angle (angle given in
 * degrees).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TrigonometricValue {
	/** Angle from which we calculate sine and cosine values. */
	private int angle;
	/** Cosine value of the given angle. */
	private double cosVal;
	/** Sine value of the given angle. */
	private double sinVal;

	/**
	 * Constructs this object (i.e. calculates it's sin and cos values) with the
	 * given angle in degrees.
	 * 
	 * @param angle angle in degrees.
	 */
	public TrigonometricValue(int angle) {
		this.angle = angle;
		double angleRad = angle * Math.PI / 180.0;
		cosVal = Math.cos(angleRad);
		sinVal = Math.sin(angleRad);
	}

	/**
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * @return the cos value of the angle.
	 */
	public double getCos() {
		return cosVal;
	}

	/**
	 * @return the sin value of the angle.
	 */
	public double getSin() {
		return sinVal;
	}

}