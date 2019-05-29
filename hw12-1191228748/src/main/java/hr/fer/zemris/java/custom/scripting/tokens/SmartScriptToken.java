package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Represents a token that a lexer gets from some input text.
 * 
 * @author Zvonimir Šimunović
 * 
 */
public class SmartScriptToken {
	/**
	 * type of the token
	 */
	private SmartScriptTokenType type;
	/**
	 * value stored in this token
	 */
	private Object value;

	/**
	 * Constructs a Token based on inputed type and value
	 * 
	 * @param type  type of the token
	 * @param value value that will be stored in the token
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;

	}

	/**
	 * Returns value stored in the token.
	 * 
	 * @return value stored in the token
	 */
	public Object getValue() {
		return value;

	}

	/**
	 * Returns type of the token
	 * 
	 * @return type of the token
	 */
	public SmartScriptTokenType getType() {
		return type;

	}
}