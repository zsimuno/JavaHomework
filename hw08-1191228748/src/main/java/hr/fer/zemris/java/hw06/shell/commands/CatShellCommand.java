package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code cat} command for shell. This command opens given file
 * and writes its content to console.
 * 
 * <p>
 * The first argument is path to some file and is mandatory. The second argument
 * is charset name that should be used to interpret chars from bytes.
 * </p>
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// Get arguments
		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Charset charset;

		try {
			charset = getCharset(args);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Path path;
		try {
			// Get file
			path = Utility.resolveDir(Paths.get(args[0]), env);
		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(path) || !Files.isReadable(path)) {
			env.writeln("Can't use the command on directories or files that are not readable!");
			return ShellStatus.CONTINUE;
		}

		// Read from file and decode and then write on screen
		try (BufferedReader br = Files.newBufferedReader(path, charset)) {

			br.lines().forEach((line) -> {
				env.writeln(line);
			});

		} catch (Exception e) {
			env.writeln("File cannot be read!");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Retrieves the charset from command arguments if one is provided or returns
	 * the default one.
	 * 
	 * @param args arguments of the cat command
	 * @return Charset used in the cat command
	 * @throws IllegalArgumentException if there is a problem with the given charset
	 *                                  (if one is given).
	 */
	private Charset getCharset(String args[]) {
		if (args.length == 1) { // Charset not provided
			return Charset.defaultCharset();

		} else if (args.length == 2) { // Charset provided

			String charsetString = args[1];

			if (!Charset.isSupported(charsetString)) {
				throw new IllegalArgumentException("Invalid charset!");
			}

			try {
				return Charset.forName(charsetString);
			} catch (Exception e) {
				throw new IllegalArgumentException("Charset getting error: " + e.getMessage());
			}

		} else {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Takes one or two arguments. The first argument is path to some file and is mandatory.",
						"The second argument is charset name that should be used to interpret chars from bytes.",
						"If not provided, a default platform charset should be used.",
						"This command opens given file and writes its content to console." });
	}

}
