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
	private static final int rows = 5;
	/** Number of columns in the layout. */
	private static final int columns = 7;
	/** Number of pixels between elements in the layout. */
	private int spaceBetween;
	/** Map of all components in this layout and their positions. */
	private Map<Component, RCPosition> components = new HashMap<>();

	/**
	 * Constructs a new {@link CalcLayout} where the space between the elements is
	 * set to given value.
	 * 
	 * @param spaceBetween space between the elements of the layout.
	 * @throws CalcLayoutException if {@code spaceBetween} is not greater than zero.
	 */
	public CalcLayout(int spaceBetween) {
		if (spaceBetween < 0)
			throw new CalcLayoutException("Space between elements must be greater than zero!");
		this.spaceBetween = spaceBetween;
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
				throw new UnsupportedOperationException("Invalid constraint!");
			}
		}
		// TODO check constraints
		int row = position.getRow(), column = position.getColumn();

		RCPosition pos = components.get(comp);
		if (pos != null && pos.equals(position))
			throw new UnsupportedOperationException("Element already exists with same bounds!");

		if (!between(row, 1, rows) && !between(column, 1, columns))
			throw new UnsupportedOperationException("Invalid constraint number!");

		if (row == 1 && (column > 1 && column < columns - 1))
			throw new UnsupportedOperationException("Invalid constraint number!");

		components.put(comp, position);
	}

	/**
	 * Checks if the given {@code number} is between given {@code i} and {@code j}
	 * parameters. (Both bounds are inclusive, i.e. i <= number <= j)
	 * 
	 * @param number number to be checked.
	 * @param i      lower bound.
	 * @param j      upper bound.
	 * @return {@code true} if i <= number <= j and {@code false} otherwise.
	 */
	private boolean between(int number, int i, int j) {
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
				dimension.width /= columns;
				dimension.height /= rows;
			}

			size.width = Math.max(size.width, dimension.width);
			size.height = Math.max(size.height, dimension.height);
		}

		size.width *= columns;
		size.height *= rows;
		Insets insets = parent.getInsets();
		size.width += insets.left + insets.right + spaceBetween * columns;
		size.height += insets.top + insets.bottom + spaceBetween * rows;
		return size;
	}

	@Override
	public void layoutContainer(Container parent) {
		int count = parent.getComponentCount();

		int x, y;
		int padding = spaceBetween / 2;
//		System.out.println(parent.getWidth() / columns); // TODO
//		System.out.println(parent.getWidth() + " " + columns); // TODO
		int w = parent.getWidth() / columns - padding;
		int h = parent.getHeight() / rows - padding;
		int parentWidth = parent.getWidth();
		int parentHeight = parent.getHeight();
		for (int i = 0; i < count; i++) {
			Component comp = parent.getComponent(i);
			RCPosition position = components.get(comp);
			if (position.getRow() == 1 && position.getColumn() == 1) {
				comp.setBounds(0, 0, parent.getWidth() * 5 / 7 - padding, parent.getHeight() * 1 / 5 - padding);
			} else {
				int row = position.getRow(), column = position.getColumn();
				if (row == 1) {
					y = 0;
				} else if (row == rows) {
					y = parentHeight - h;
				} else {
					y = ((row - 1) * parentHeight / rows) + padding;
				}

				if (column == 1) {
					x = 0;
				} else if (column == columns) {
					x = parentWidth - w;
				} else {
					x = ((column - 1) * parentWidth / columns) + padding;
				}
//				System.out.println(w); // TODO
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
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "CalcLayout [spaceBetween=" + spaceBetween + "]";
	}

}
