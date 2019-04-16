package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents the {@code exit} command for shell. Command terminates the shell.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() != 0) {
			env.writeln("Exit command takes no arguments!");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[]{"Terminates the shell."});
	}

}
