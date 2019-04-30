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
 * Represents the {@code mkdir} (make directory) command for shell. Command
 * makes a directory with the given name.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);

		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 1) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		Path directoryPath;
		try {
			directoryPath = Utility.resolveDir(Paths.get(args[0]), env);

		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
			env.writeln("Directory already exists!");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.createDirectories(directoryPath);
		} catch (Exception e) {
			env.writeln("Error while creating: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] {
				"Takes a single argument: directory name, and creates the appropriate directory structure." });
	}

}
