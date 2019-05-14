package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Represents a position in the layout.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class RCPosition {

	/** Row number of the position.. */
	private final int row;
	/** Column number of the position.. */
	private final int column;

	/**
	 * New position with given row and column number.
	 * 
	 * @param row    row of the position.
	 * @param column column of the position.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row of the position.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column of the position.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}

}