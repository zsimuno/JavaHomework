/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Zvonimir Šimunović
 *
 */
class SmartScriptLexerTest {

	/**
	 * @param filename
	 * @return
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer#SmartScriptLexer(java.lang.String)}.
	 */
	@Test
	void testSmartScriptLexer() {
		String input = "This is sample text.\r\n" + "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n" + "{$END$}\r\n" + "{$FOR i 0 10 2 $}\r\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + "{$END$}";

		SmartScriptLexer lexer = new SmartScriptLexer(input);
		
		do {
			SmartScriptToken t = lexer.nextSmartScriptToken();
			System.out.println(t.getType() + " " + t.getValue());
			if (t.getType() == SmartScriptTokenType.OPENBRACES) {
				lexer.setState(SmartScriptLexerState.TAG);
			}

			if (t.getType() == SmartScriptTokenType.CLOSEDBRACES) {
				lexer.setState(SmartScriptLexerState.TEXT);
			}
		} while (lexer.getToken().getType() != SmartScriptTokenType.EOF);
	}

	/**
	 * Test method for
	 * {@link hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer#nextSmartScriptToken()}.
	 */
	@Test
	void testNextSmartScriptToken() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer#getToken()}.
	 */
	@Test
	void testGetToken() {
		fail("Not yet implemented");
	}

}
