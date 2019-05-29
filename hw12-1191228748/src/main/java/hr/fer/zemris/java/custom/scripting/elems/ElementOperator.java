/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a operator element
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementOperator implements Element {

	/**
	 * Symbol of the operator
	 */
	private Character symbol;

	/**
	 * Constructs {@link ElementOperator} from the given {@code symbol}
	 * 
	 * @param symbol symbol of the operator
	 */
	public ElementOperator(Character symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol.toString();
	}

	/**
	 * Returns the symbol of the operator
	 * 
	 * @return the symbol of the operator
	 */
	public Character getSymbol() {
		return symbol;
	}


	@Override
	public String toString() {
		return symbol.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		return Objects.equals(symbol, other.symbol);
	}
	
	
	
	

}
