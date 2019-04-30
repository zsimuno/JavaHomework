package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents {@code help} command of the shell. Can list all commands or usage
 * for one command.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (Utility.hasNoArguments(arguments)) {
			
			env.writeln("SUPPORTED COMMANDS:");
			// Print all commands
			env.commands().forEach((name, command) -> {
				env.writeln("  " + name);
			});
			
			return ShellStatus.CONTINUE;
		}

		String[] args = Utility.parseNonPathArguments(arguments);

		if (args.length != 1) {
			env.writeln("Wrong number of arguments!");
			return ShellStatus.CONTINUE;
		}

		ShellCommand command = env.commands().get(args[0]);

		if (command == null) {
			env.writeln("No such command!");
			return ShellStatus.CONTINUE;
		}

		env.writeln("NAME: \n");
		env.writeln(command.getCommandName());
		env.writeln("\nDESCRIPTION: \n");
		command.getCommandDescription().forEach(env::writeln);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] {
				"If started with no arguments, it must list names of all supported commands.",
				"If started with single argument, it must print name and the description of selected command (or print",
				"appropriate error message if no such command exists)." });
	}

}
