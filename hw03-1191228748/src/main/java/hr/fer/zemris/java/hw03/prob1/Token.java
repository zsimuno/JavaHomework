/**
 * 
 */
package hr.fer.zemris.java.hw03.prob1;

/**
 * @author Zvonimir Šimunović
 * TODO javadoc
 */
public class Token {
	/**
	 * 
	 */
	private TokenType type;
	/**
	 * 
	 */
	private Object value;
	/**
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
		
	}
	/**
	 * @return
	 */
	public Object getValue() {
		return value;
		
	}
	/**
	 * 
	 * @return
	 */
	public TokenType getType() {
		return type;
		
	}
}
