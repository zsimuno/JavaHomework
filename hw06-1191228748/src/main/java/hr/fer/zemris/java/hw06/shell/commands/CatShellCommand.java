package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents the {@code cat} command for shell. This command opens given file
 * and writes its content to console.
 * 
 * <p>
 * The first argument is path to some file and is mandatory. The second argument
 * is charset name that should be used to interpret chars from bytes.
 * </p>
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO CatShellCommand executeCommand

		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		
		if(args.length == 1) {
			String filePath = args[0];
			Charset charset = Charset.defaultCharset();
			
			
		} else if (args.length == 2) {
			String filePath = args[0];
			String charsetString = args[1];
			
			
		} else {
			env.writeln("Invalid number of arguments!");
			
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Takes one or two arguments. The first argument is path to some file and is mandatory.",
						"The second argument is charset name that should be used to interpret chars from bytes.",
						"If not provided, a default platform charset should be used.",
						"This command opens given file and writes its content to console." });
	}

}
