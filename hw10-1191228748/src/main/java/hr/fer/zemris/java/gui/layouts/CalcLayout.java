package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Layout implementation that is used as a calculator layout.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CalcLayout implements LayoutManager2 {

	/** Number of rows in the layout. */
	private static final double rows = 5.0;
	/** Number of columns in the layout. */
	private static final double columns = 7.0;
	/** Number of pixels between elements in the layout. */
	private int spacing;
	/** Map of all components in this layout and their positions. */
	private Map<Component, RCPosition> components = new HashMap<>();

	/**
	 * Constructs a new {@link CalcLayout} where the space between the elements is
	 * set to given value.
	 * 
	 * @param spaceBetween space between the elements of the layout.
	 * @throws CalcLayoutException if {@code spaceBetween} is not greater than zero.
	 */
	public CalcLayout(int spacing) {
		if (spacing < 0)
			throw new CalcLayoutException("Space between elements must be greater than zero!");
		this.spacing = spacing;
	}

	/**
	 * Constructs a new {@link CalcLayout} where the space between the elements is
	 * set to zero.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (!(constraints instanceof RCPosition || constraints instanceof String)) {
			throw new UnsupportedOperationException("Invalid constraint!");
		}
		RCPosition position;
		if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			String[] pos = ((String) constraints).split(",");
			try {
				position = new RCPosition(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
			} catch (Exception e) {
				throw new CalcLayoutException("Invalid constraint!");
			}
		}
		int row = position.getRow(), column = position.getColumn();

		if (components.containsValue(position))
			throw new CalcLayoutException("Element already exists with those bounds!");

		if (!areConstraintsValid(row, column))
			throw new CalcLayoutException("Invalid constraint number!");

		components.put(comp, position);
	}

	/**
	 * Checks if the given constraints are valid.
	 * 
	 * @param row    row number.
	 * @param column column number.
	 * @return {@code true} if the given constraints are valid, {@code false}
	 *         otherwise.
	 */
	private boolean areConstraintsValid(int row, int column) {
		if ((row == 1 && (column > 1 && column < columns - 1)))
			return false;

		return between(row, 1, rows) && between(column, 1, columns);
	}

	/**
	 * Checks if the given {@code number} is between given {@code i} and {@code j}
	 * parameters. (both are inclusive, i.e. i <= number <= j)
	 * 
	 * @param number number to be checked.
	 * @param i      lower bound.
	 * @param j      upper bound.
	 * @return {@code true} if i <= number <= j and {@code false} otherwise.
	 */
	private boolean between(int number, int i, double j) {
		return number >= i && number <= j;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLazyoutSize(parent, "preferred");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLazyoutSize(parent, "min");
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLazyoutSize(target, "max");
	}

	/**
	 * Gets the requested layout size. Scales the layout size form the largest of
	 * all either preferred, minimum or maximum layout sizes (which one is
	 * determined by {@code type} parameter).
	 * 
	 * @param parent parent container.
	 * @param type   determines whether we want the preferred, minimum or maximum
	 *               size.
	 * @return preferred, minimum or maximum dimensions of this layout.
	 */
	private Dimension getLazyoutSize(Container parent, String type) {
		int count = parent.getComponentCount();
		Dimension size = new Dimension(0, 0);

		for (int i = 0; i < count; i++) {
			Component comp = parent.getComponent(i);

			Dimension dimension;
			if (type.equals("min")) {
				dimension = comp.getMinimumSize();
			} else if (type.equals("max")) {
				dimension = comp.getMaximumSize();
			} else {
				dimension = comp.getPreferredSize();
			}

			if (components.get(comp).equals(new RCPosition(1, 1))) {
				dimension.width = (int) Math.ceil(dimension.width * 1.0 / (columns - 2)) - 2;
			}

			size.width = Math.max(size.width, dimension.width);
			size.height = Math.max(size.height, dimension.height);
		}

		size.width *= columns;
		size.height *= rows;
		Insets insets = parent.getInsets();
		size.width += insets.left + insets.right + spacing * (columns - 1);
		size.height += insets.top + insets.bottom + spacing * (rows - 1);
		return size;
	}

	@Override
	public void layoutContainer(Container parent) {
		int count = parent.getComponentCount();

		Insets insets = parent.getInsets();

		int width = parent.getWidth() - (insets.left + insets.right);
		int height = parent.getHeight() - (insets.top + insets.bottom);
		double elWidth = (width - spacing * (columns - 1)) / columns;
		double elHeight = (height - spacing * (rows - 1)) / rows;
		int wFloor = (int) Math.floor(elWidth);
		int wCeil = (int) Math.ceil(elWidth);

		int hFloor = (int) Math.floor(elHeight);
		int hCeil = (int) Math.ceil(elHeight);

		int x, y;
		for (int i = 0; i < count; i++) {
			Component comp = parent.getComponent(i);
			RCPosition position = components.get(comp);

			if (position.getRow() == 1 && position.getColumn() == 1) {
				comp.setBounds(0, 0, wFloor * 2 + wCeil * 3 + spacing * 4, hCeil);

			} else {
				int row = position.getRow(), column = position.getColumn();
				int w = ((column % 2 == 0) ? wFloor : wCeil);
				int h = ((row % 2 == 0) ? hFloor : hCeil);

				y = 0;
				for (int j = 1; j < row; j++) {
					y += ((j % 2 == 0) ? hFloor : hCeil) + spacing;
				}

				x = 0;
				for (int j = 1; j < column; j++) {
					x += ((j % 2 == 0) ? wFloor : wCeil) + spacing;
				}

				comp.setBounds(x, y, w, h);

			}

		}

	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public String toString() {
		return "CalcLayout [spaceBetween=" + spacing + "]";
	}

}
