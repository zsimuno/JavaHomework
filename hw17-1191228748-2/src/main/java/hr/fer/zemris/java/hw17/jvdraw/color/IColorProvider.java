package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * Interface for classes that provide color.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IColorProvider {
	/**
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds a listener that listens when the color changes on this provider.
	 * 
	 * @param l listener to when the color changes
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes a listener that listens when the color changes on this provider.
	 * 
	 * @param l listener to when the color changes
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
