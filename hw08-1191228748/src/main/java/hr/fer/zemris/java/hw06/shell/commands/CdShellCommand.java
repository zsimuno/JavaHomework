package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code cd} command for shell. This command changes the current
 * environment directory.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CdShellCommand implements ShellCommand {

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

		if (args.length != 1) {
			env.writeln("Cd command takes only one argument.");
			return ShellStatus.CONTINUE;
		}

		Path path;
		try {
			path = Utility.resolveDir(Paths.get(args[0]), env);
		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(path)) {
			env.writeln("Given path must be an existing directory!");
			return ShellStatus.CONTINUE;
		}

		env.setCurrentDirectory(path);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Takes one argument.", "Argument is a new directory of the environment.",
						"That is, a new current directory.", "Given path can be relative or absolute." });
	}

}
