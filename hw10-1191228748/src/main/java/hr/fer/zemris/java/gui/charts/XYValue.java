package hr.fer.zemris.java.gui.charts;

/**
 * Wrapper for two (x and y) values.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class XYValue {
	/** X value. */
	private final int x;
	/** Y value. */
	private final int y;

	/**
	 * Constructs new {@link XYValue} from the given x and y values.
	 * 
	 * @param x X value.
	 * @param y Y value.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y value.
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "XYValue(" + x + ", " + y + ")";
	}
	
	

}
