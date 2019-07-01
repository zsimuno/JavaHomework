package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;

/**
 * TODO results javadoc
 * @author Zvonimir Šimunović
 *
 */
public class ResultsCommand implements SearchCommand {

	@Override
	public boolean execute(String arguments) {
		// TODO Auto-generated method stub
		if(arguments != null) {
			System.out.println("Command takes no arguments!");
			return true;
		}
		return true;
	}

}
