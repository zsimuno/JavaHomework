package hr.fer.zemris.java.hw17.trazilica;

/**
 * Interface for commands of the searching application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface SearchCommand {

	/**
	 * Execute searching command.
	 * 
	 * @param arguments arguments of the command.
	 * @param data   list of calculated results of the last query.
	 * @return {@code true} if the shell should continue and {@code false} if it
	 *         shouldn't.
	 */
	public boolean execute(String arguments, SearchData data);
}
