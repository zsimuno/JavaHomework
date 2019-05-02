package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code popd} command for shell. This command pops the
 * directory from the top of the directory stack and puts it as a current
 * directory (if one exists).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PopdShellCommand implements ShellCommand {

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

		Path top = stack.pop();

		if (Files.exists(top)) {
			env.setCurrentDirectory(top);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Takes no arguments.", "Pops the directory from the top of the directory stack",
						"and puts it as a current directory (if one exists).",
						"If the directory from the stack top doesn't exist then ",
						"the current one doesn't change but the top one is still removed." });
	}

}
