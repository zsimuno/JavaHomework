/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * @author Zvonimir Šimunović
 *
 */
class SmartScriptParserTest {

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

	@Test
	public void testNotNull() {
		SmartScriptParser parser = new SmartScriptParser("");
		assertNotNull(parser.getDocumentNode());
	}

	@Test
	public void testNullInput() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));
	}

	@Test
	public void invalidForLoop1() {
		assertThrows(SmartScriptParserException.class,
				() -> new SmartScriptParser("{$ FOR * \" 1 \" -10 \" 1 \" $}{$END$}"));
	}

	@Test
	public void invalidForLoop2() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year @sin 10 $}{$END$}"));
	}

	@Test
	public void invalidForLoop3() {
		assertThrows(SmartScriptParserException.class,
				() -> new SmartScriptParser("{$ FOR year 1 10 \" 1 \" \"10\" $}{$END$}"));
	}

	@Test
	public void invalidForLoop4() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year $}{$END$}"));
	}

	@Test
	public void invalidForLoop5() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 1 3 $}{$END$}"));
	}

	@Test
	public void invalidForLoop6() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR 3 1 10 1 $}{$END$}"));
	}

	@Test
	public void validForLoops1() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1 10 1 $}{$END$}");
		DocumentNode document1 = parser.getDocumentNode();
		DocumentNode document2 = parser.getDocumentNode();
		document2.addChildNode(new ForLoopNode(new ElementVariable("i"), (Element) new ElementConstantInteger(-1),
				(Element) new ElementConstantInteger(10), (Element) new ElementConstantInteger(1)));

		assertEquals(document2, document1);
	}

	@Test
	public void validForLoops2() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR sco_re \"-1\"10 \"1\" $}{$END$}");
		DocumentNode document1 = parser.getDocumentNode();
		DocumentNode document2 = parser.getDocumentNode();
		document2.addChildNode(new ForLoopNode(new ElementVariable("sco_re"), (Element) new ElementString("-1"),
				(Element) new ElementConstantInteger(10), (Element) (Element) new ElementString("1")));

		assertEquals(document2, document1);
	}

	@Test
	public void validForLoops3() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR year 1 last_year $}{$END$}");
		DocumentNode document1 = parser.getDocumentNode();
		DocumentNode document2 = parser.getDocumentNode();
		document2.addChildNode(new ForLoopNode(new ElementVariable("year"), (Element) new ElementConstantInteger(1),
				(Element) new ElementVariable("last_year"), (Element) null));

		assertEquals(document2, document1);
	}
	
	@Test
	public void testParser() {
		SmartScriptParser parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");
		DocumentNode document1 = parser.getDocumentNode();
		DocumentNode document2 = parser.getDocumentNode();
		document2.addChildNode(new TextNode("Example {$=1$}. Now actually write one "));
		Element[] elements = {(Element) new ElementVariable("i")};
		document2.addChildNode(new EchoNode(elements));

		assertEquals(document2, document1);
	}
	
	@Test
	public void testEchoNode() {
		SmartScriptParser parser = new SmartScriptParser("{$= i i * @sin \"0.000\" @decfmt $}");
		DocumentNode document1 = parser.getDocumentNode();
		DocumentNode document2 = parser.getDocumentNode();
		Element[] elements = {(Element) new ElementVariable("i"), (Element) new ElementVariable("i"), 
				(Element) new ElementOperator('*'),
				(Element) new ElementFunction("sin"),
				(Element) new ElementString("0.000"),
				(Element) new ElementFunction("decfmt")};
		document2.addChildNode(new EchoNode(elements));

		assertEquals(document2, document1);
	}

}
