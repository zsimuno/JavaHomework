package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

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
		// TODO MkdirShellCommand executeCommand
		return null;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[]{"Takes a single argument: directory name, and creates the appropriate directory structure."});
	}

}
