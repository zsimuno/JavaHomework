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
		// TODO Cat finish maybe

		// Get arguments
		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		Charset charset;

		if (args.length == 1) { // Charset not provided
			charset = Charset.defaultCharset();

		} else if (args.length == 2) { // Charset provided

			String charsetString = args[1];
			
			if(!Charset.isSupported(charsetString)) {
				env.writeln("Invalid charset!");
				return ShellStatus.CONTINUE;
			}

			try {
				charset = Charset.forName(charsetString);
			} catch (Exception e) {
				env.writeln("Charset getting error: " + e.getMessage());
				return ShellStatus.CONTINUE;
			}

		} else {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		// Get file
		Path path = Paths.get(args[0]);

		if (Files.isDirectory(path)) {
			env.writeln("Can't use the command on directories!");
			return ShellStatus.CONTINUE;
		}
				
		if(!Files.isReadable(path)) {
			env.writeln("File is not readable!");
			return ShellStatus.CONTINUE;
		}

		// Read from file and decode and then write on screen
		try (BufferedReader br = Files.newBufferedReader(path, charset)) {

			br.lines().forEach((line) -> {
				env.writeln(line);
			});

		} catch (Exception e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
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
