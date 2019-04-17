package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents the {@code copy} command of the shell. Copies the given source
 * file in the given destination file or directory. If destination file exists,
 * you should ask user is it allowed to overwrite it.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO CopyShellCommand executeCommand
		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if(args.length != 2) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}
		
		String sourceFilePath = args[0];
		String destinationPath = args[0]; // Can be file or directory
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] {
				"Expects two arguments: source file name and destination file name (i.e. paths and names).",
				"If destination file exists, overwrites it if allowed.", 
				"If the second argument is directory,",
				"copies the original file into that directory using the original file name." });
	}

}
