package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code cat} command for shell. This command pops the top
 * directory from the stack without changing the current directory.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DropdShellCommand implements ShellCommand {

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
			env.writeln("Stack is empty!");
			return ShellStatus.CONTINUE;

		} else {
			stack = (Stack<Path>) data;
			if(stack.isEmpty()) {
				env.writeln("Stack is empty!");
				return ShellStatus.CONTINUE;
			}
		}

		stack.pop();

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] { "Takes no arguments.",
				"Pops the top directory from the stack without changing the current directory." });
	}

}
