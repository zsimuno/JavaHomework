/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.elems;

/**
 * TODO
 * @author Zvonimir Šimunović
 *
 */
public class ElementOperator extends Element {

	/**
	 * 
	 * name of the element
	 */
	private String symbol;
	
	

	/**
	 * @param name
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	String asText() {
		return symbol;
	}

	/**
	 * Returns the name of the element
	 * 
	 * @return the name
	 */
	public String getSymbol() {
		return symbol;
	}

}
