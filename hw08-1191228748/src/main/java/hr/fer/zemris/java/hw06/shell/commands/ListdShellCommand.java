package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code listd} command for shell. This command lists all the
 * directories from the directory stack.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ListdShellCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("Command takes no arguments!");
			return ShellStatus.CONTINUE;
		}

		Object data = env.getSharedData("cdstack");
		Stack<Path> stack;

		if (data == null || !(data instanceof Stack<?>)) {
			env.writeln("Nema pohranjenih direktorija.");
			return ShellStatus.CONTINUE;

		} else {
			stack = (Stack<Path>) data;
			if(stack.isEmpty()) {
				env.writeln("Nema pohranjenih direktorija.");
				return ShellStatus.CONTINUE;
			}
		}
		StringBuilder sb = new StringBuilder();
		stack.forEach((path) -> sb.insert(0, path.toString() + "\n"));
		env.write(sb.toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Takes no arguments.", "Lists all the directories from the directory stack." });
	}

}
