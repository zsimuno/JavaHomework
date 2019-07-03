package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * Listens to when the color changes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface ColorChangeListener {
	/**
	 * Called when the color changes.
	 * 
	 * @param source   source where the color changed
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
