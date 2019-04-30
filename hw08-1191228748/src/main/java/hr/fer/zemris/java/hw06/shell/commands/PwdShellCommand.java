package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code pwd} command for shell. This command prints the current
 * environment directory.
 * 
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PwdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command takes no arguments!");
			return ShellStatus.CONTINUE;
		}

		env.writeln(env.getCurrentDirectory().toString());

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] { "Takes no arguments.", "Prints the current directory." });
	}

}
