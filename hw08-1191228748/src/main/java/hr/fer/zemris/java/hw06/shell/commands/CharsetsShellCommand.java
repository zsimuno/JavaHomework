/**
 * 
 */
package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents {@code charset} command for the shell. Takes no arguments and
 * lists names of supported charsets. A single charset name is written per line.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!Utility.hasNoArguments(arguments)) {
			env.writeln("Charset command takes no arguments!");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, Charset> charsets = Charset.availableCharsets();

		charsets.forEach((name, charset) -> {
			env.writeln(name);
		});

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charset";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility
				.turnToUnmodifiableList(new String[] { "Takes no arguments and lists names of supported charsets.",
						"A single charset name is written per line." });
	}

}
