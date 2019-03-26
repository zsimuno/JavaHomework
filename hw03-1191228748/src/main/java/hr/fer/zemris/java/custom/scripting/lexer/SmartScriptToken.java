package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * @author Zvonimir Šimunović
 * TODO javadoc
 */
public class SmartScriptToken {
	/**
	 * 
	 */
	private SmartScriptTokenType type;
	/**
	 * 
	 */
	private Object value;
	/**
	 * @param type
	 * @param value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
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
	public SmartScriptTokenType getType() {
		return type;
		
	}
}