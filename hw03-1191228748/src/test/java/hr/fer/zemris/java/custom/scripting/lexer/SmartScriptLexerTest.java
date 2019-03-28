/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/**
 * Tests for {@link SmartScriptLexer}
 * 
 * @author Zvonimir Šimunović
 *
 */
class SmartScriptLexerTest {

	

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		assertNotNull(lexer.nextSmartScriptToken());
	}

	@Test
	public void testNullInput() {
		assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextSmartScriptToken().getType(),
				"Needed only EOF on empty input.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling SmartScriptToken must
		// return each time what SmartScriptToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextSmartScriptToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than than needed.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than than needed.");
	}

	@Test
	public void testNextAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// EOF
		lexer.nextSmartScriptToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextSmartScriptToken());
	}

	@Test
	public void testNoActualContent() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		assertEquals(SmartScriptTokenType.EOF, lexer.nextSmartScriptToken().getType(),
				"Input had no content. SmartScriptLexer should generated only EOF token.");
	}

	@Test
	public void testEchoTag() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("{$= i i * @sin \"0.000\" @decfmt $}");

		lexer.setState(SmartScriptLexerState.TAG);

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$"),
				new SmartScriptToken(SmartScriptTokenType.EQUALSSIGN, Character.valueOf('=')),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
				new SmartScriptToken(SmartScriptTokenType.OPERATOR, Character.valueOf('*')),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sin"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "0.000"),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "decfmt"),
				new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\{ \\\\");

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "{ \\"));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	@Test
	public void testEscape2() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}");

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "Example { bla } blu {$=1$}. Nothing interesting {=here}"));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}

	@Test
	public void testInvalidEscapeEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\");

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextSmartScriptToken());
	}

	@Test
	public void testInvalidEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\c    ");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextSmartScriptToken());
	}
	
	@Test
	public void testValidVariableName1() {
		SmartScriptLexer lexer = new SmartScriptLexer("A7_bb");
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "A7_bb"));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	@Test
	public void testValidVariableName2() {
		SmartScriptLexer lexer = new SmartScriptLexer("counter");
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "counter"));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	@Test
	public void testValidVariableName3() {
		SmartScriptLexer lexer = new SmartScriptLexer("tmp_34");
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "tmp_34"));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
	}
	
	@Test
	public void testInvalidVarName1() {
		SmartScriptLexer lexer = new SmartScriptLexer("_a21");
		lexer.setState(SmartScriptLexerState.TAG);
		
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextSmartScriptToken());
	}
	
	@Test
	public void testInvalidVarName2() {
		SmartScriptLexer lexer = new SmartScriptLexer("32");
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(32)));
	}
	
	@Test
	public void testInvalidVarName3() {
		SmartScriptLexer lexer = new SmartScriptLexer("3s_ee");
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(3)));
	}

	@Test
	public void testTooBigInteger() {
		SmartScriptLexer lexer = new SmartScriptLexer("12345678912123123432123");

		lexer.setState(SmartScriptLexerState.TAG);

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextSmartScriptToken());
	}
	




	@Test
	public void testForLoopTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 1 10 1 $}");

		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "FOR"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(10)),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
				new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testForLoopDifferent() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR sco_re \"-1\" 10 \"1\" $}");

		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "FOR"),
				new SmartScriptToken(SmartScriptTokenType.VARIABLE, "sco_re"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "-1"),
				new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(10)),
				new SmartScriptToken(SmartScriptTokenType.STRING, "1"),
				new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testString() {
		// Lets check for several symbols...
		SmartScriptLexer lexer = new SmartScriptLexer("{$ \r\n\t \"My \\\"smart\\\" string \\r\\n\\t\"  $}");

		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "My \"smart\" string \r\n\t"),
				new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTwoLexers() {
		SmartScriptLexer lexer1 = new SmartScriptLexer("{$ FOR i-1.35bbb\"1\" $}");
		SmartScriptLexer lexer2 = new SmartScriptLexer("{$ FOR i -1.35 bbb \"1\" $}");
		lexer1.setState(SmartScriptLexerState.TAG);
		lexer2.setState(SmartScriptLexerState.TAG);
		
		checkTwoLexers(lexer1, lexer2);

	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextSmartScriptToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	private void checkTwoLexers(SmartScriptLexer lexer1, SmartScriptLexer lexer2) {
		int counter = 0;
		while (true) {
			SmartScriptToken first = lexer1.nextSmartScriptToken();
			SmartScriptToken second = lexer2.nextSmartScriptToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(first.getType(), second.getType(), msg);
			assertEquals(first.getValue(), second.getValue(), msg);
			counter++;
			if(first.getType() == SmartScriptTokenType.EOF) {
				break;
			}
		}
	}
	
	@Test
	public void testNullState() {
		assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer("").setState(null));
	}

	@Test
	public void testNotNullInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertNotNull(lexer.nextSmartScriptToken(), "Null was returned bud needed a token.");
	}

	@Test
	public void testEmptyInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertEquals(SmartScriptTokenType.EOF, lexer.nextSmartScriptToken().getType(),
				"Must be only EOF.");
	}

	@Test
	public void testGetReturnsLastNextInTAG() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken token = lexer.nextSmartScriptToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than than needed.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than than needed.");
	}

	@Test
	public void testNextAfterEOFInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		// will obtain EOF
		lexer.nextSmartScriptToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextSmartScriptToken());
	}

	@Test
	public void testEmptyInTagState() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		lexer.setState(SmartScriptLexerState.TAG);

		assertEquals(SmartScriptTokenType.EOF, lexer.nextSmartScriptToken().getType(),
				"Input had no content. SmartScriptLexer should generated only EOF token.");
	}

	@Test
	public void testMultipleStates() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$} word");

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows "));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$"));

		lexer.setState(SmartScriptLexerState.TAG);

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EQUALSSIGN, Character.valueOf('=')));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \"Long\" Smith"));
		
		checkToken(lexer.nextSmartScriptToken(),
				new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}"));

		lexer.setState(SmartScriptLexerState.TEXT);

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, " word"));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));

	}
	
	@Test
	public void testMultipleStatesAgain() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now actually write one "));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$"));

		lexer.setState(SmartScriptLexerState.TAG);

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EQUALSSIGN, Character.valueOf('=')));
		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)));
		
		checkToken(lexer.nextSmartScriptToken(),
				new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}"));

		checkToken(lexer.nextSmartScriptToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));

	}

	private void checkToken(SmartScriptToken actual, SmartScriptToken expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
	}

}
