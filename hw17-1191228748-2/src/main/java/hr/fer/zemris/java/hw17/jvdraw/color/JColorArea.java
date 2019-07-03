package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

public class JColorArea extends JComponent implements IColorProvider {

	/** */
	private static final long serialVersionUID = 1L;

	/** Currently selected color. */
	private Color selectedColor;

	/** All listeners that listen to when the color changes. */
	private Set<ColorChangeListener> colorListeners = new HashSet<>();

	/** Listener that opens an dialog to choose a new selected color. */
	MouseListener chooseColor = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			Color oldColor = selectedColor;
			selectedColor = JColorChooser.showDialog(JColorArea.this, "Select color", Color.black);
			notifyColorChangeListeners(oldColor);
		}
	};

	/**
	 * Contructor.
	 * 
	 * @param selectedColor currently selected color to be set
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.addMouseListener(chooseColor);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		colorListeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		colorListeners.remove(l);
	}

	/**
	 * Notifies all color change listeners that the color has been changed.
	 */
	private void notifyColorChangeListeners(Color oldColor) {
		for (ColorChangeListener l : colorListeners) {
			l.newColorSelected(this, oldColor, selectedColor);
		}
		
	}
}
