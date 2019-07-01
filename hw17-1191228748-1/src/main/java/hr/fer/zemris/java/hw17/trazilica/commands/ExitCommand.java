package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;

/**
 * Command that exits the application. (returns {@code false}).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ExitCommand implements SearchCommand {

	@Override
	public boolean execute(String arguments) {
		if(arguments != null) {
			System.out.println("Command takes no arguments!");
			return true;
		}
		return false;
	}

}
