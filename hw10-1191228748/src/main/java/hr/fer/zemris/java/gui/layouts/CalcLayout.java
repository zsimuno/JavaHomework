package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Layout implementation that is used as a calculator layout.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CalcLayout implements LayoutManager2 {

	/** Number of rows in the layout. */
	private static final int rowsCount = 5;
	/** Number of columns in the layout. */
	private static final int columnsCount = 7;
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
		if ((row == 1 && (column > 1 && column < columnsCount - 1)))
			return false;

		return row >= 1 && row <= rowsCount && column >= 1 && column <= columnsCount;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLazyoutSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLazyoutSize(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLazyoutSize(target, Component::getMaximumSize);
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
	private Dimension getLazyoutSize(Container parent, Function<Component, Dimension> function) {
		int count = parent.getComponentCount();
		Dimension size = new Dimension(0, 0);

		RCPosition firstPos = new RCPosition(1, 1);
		for (int i = 0; i < count; i++) {
			Component comp = parent.getComponent(i);
			if (!components.containsKey(comp))
				continue;

			Dimension dimension = function.apply(comp);

			if (dimension == null)
				continue;

			if (components.get(comp).equals(firstPos)) {
				dimension.width = (dimension.width - 4 * spacing) / 5;
			}

			size.width = Math.max(size.width, dimension.width);
			size.height = Math.max(size.height, dimension.height);
		}

		size.width *= columnsCount;
		size.height *= rowsCount;
		Insets insets = parent.getInsets();
		size.width += insets.left + insets.right + spacing * (columnsCount - 1);
		size.height += insets.top + insets.bottom + spacing * (rowsCount - 1);
		return size;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();

		int width = parent.getWidth() - (insets.left + insets.right);
		int height = parent.getHeight() - (insets.top + insets.bottom);
		int startx = insets.left;
		int starty = insets.top;
		int elWidth = (int) Math.round((double) (width - spacing * (columnsCount - 1)) / columnsCount);
		int elHeight = (int) Math.round((double) (height - spacing * (rowsCount - 1)) / rowsCount);
		int diffw = width - (elWidth * columnsCount + spacing * (columnsCount - 1));
		int diffh = height - (elHeight * rowsCount + spacing * (rowsCount - 1));
		int addToWidth = diffw == 0 ? 0 : diffw / Math.abs(diffw);
		int addToHeight = diffh == 0 ? 0 : diffh / Math.abs(diffh);
		int pickedColumn = (diffw == 0) ? 1 : columnsCount / Math.abs(diffw);
		int pickedRow = (diffh == 0) ? 1 : rowsCount / Math.abs(diffh);

		for (Component comp : components.keySet()) {
			RCPosition position = components.get(comp);
			int row = position.getRow(), column = position.getColumn();

			if (row == 1 && column == 1) {
				int h = elHeight + ((row % pickedRow == 0) ? addToHeight : 0);
				int w = elWidth * 5 + spacing * 4 + addToWidth * 5 / pickedColumn;
				comp.setBounds(startx, starty, w, h); 

			} else {

				int h = elHeight + ((row % pickedRow == 0) ? addToHeight : 0);
				int w = elWidth + ((column % pickedColumn == 0) ? addToWidth : 0);

				int y = starty + (row - 1) * (elHeight + spacing) + addToHeight * ((row - 1) / pickedRow);
				int x = startx + (column - 1) * (elWidth + spacing) + addToWidth * ((column - 1) / pickedColumn);

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

}
