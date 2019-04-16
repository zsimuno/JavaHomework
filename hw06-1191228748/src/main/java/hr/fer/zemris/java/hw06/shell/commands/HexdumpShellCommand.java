package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents the {@code hexdump} command of the shell. Command produces a
 * hex-output of the given file.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO HexdumpShellCommand executeCommand
		return null;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[]{"Expects a single argument: file name, and produces hex-output."});
	}

}
