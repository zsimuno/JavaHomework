package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Label that shows the current foreground and current background color.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener {

	/** */
	private static final long serialVersionUID = 1L;

	/** Provider that provides the color for the foreground. */
	private IColorProvider fgColorProvider;
	/** Provider that provides the color for the background. */
	private IColorProvider bgColorProvider;

	/**
	 * Constructor.
	 * 
	 * @param fgColorProvider provider that provides the color for the foreground
	 * @param bgColorProvider provider that provides the color for the background
	 */
	public JColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider.addColorChangeListener(this);
		this.bgColorProvider.addColorChangeListener(this);
		setLabel();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setLabel();
	}

	/**
	 * Sets the label to the appropriate text that shows current fg and bg colors.
	 */
	private void setLabel() {
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor();
		String fgStr = String.format("Foreground color: (%d, %d, %d), ", fgColor.getRed(), fgColor.getGreen(),
				fgColor.getBlue());
		String bgStr = String.format("background color: (%d, %d, %d).", bgColor.getRed(), bgColor.getGreen(),
				bgColor.getBlue());

		this.setText(fgStr + bgStr);
	}

}
