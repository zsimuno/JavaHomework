/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a operator element
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ElementOperator extends Element {

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
	String asText() {
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

}
