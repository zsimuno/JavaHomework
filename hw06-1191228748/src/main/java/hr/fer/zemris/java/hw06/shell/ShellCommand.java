/**
 * 
 */
package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that represents one shell command.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface ShellCommand {
	/**
	 * Executes the command
	 * 
	 * @param env       environment that the command will be executed in.
	 * @param arguments arguments of the command
	 * @return shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns command name.
	 * 
	 * @return command name.
	 */
	String getCommandName();

	/**
	 * Returns command description.
	 * 
	 * @return command description.
	 */
	List<String> getCommandDescription();
}
