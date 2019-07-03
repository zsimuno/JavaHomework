package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;
import hr.fer.zemris.java.hw17.trazilica.SearchData;

/**
 * Command that exits the application. (returns {@code false}).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ExitCommand implements SearchCommand {

	@Override
	public boolean execute(String arguments, SearchData data) {
		if(arguments != null) {
			System.out.println("Command takes no arguments!");
			return true;
		}
		return false;
	}

}
