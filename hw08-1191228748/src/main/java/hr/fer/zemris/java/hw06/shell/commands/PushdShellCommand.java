package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code pushd} command for shell. This command pushes the
 * current directory to the directory stack and sets the current directory to
 * the given one.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PushdShellCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
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
			env.writeln("Command takes one argument!");
			return ShellStatus.CONTINUE;
		}

		Path directoryPath;
		// Get the directory path
		try {
			directoryPath = Utility.resolveDir(Paths.get(args[0]), env);

		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(directoryPath)) {
			env.writeln("Given path must be an existing directory!");
			return ShellStatus.CONTINUE;
		}

		Object data = env.getSharedData("cdstack");
		Stack<Path> stack;

		if (data == null || !(data instanceof Stack<?>)) {
			stack = new Stack<>();
			env.setSharedData("cdstack", stack);

		} else {
			stack = (Stack<Path>) data;
		}

		stack.push(directoryPath);
		env.setCurrentDirectory(directoryPath);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Takes one arguments", "Pushes the current directory to the directory stack and ",
						" sets the current directory to the given one." });
	}

}
