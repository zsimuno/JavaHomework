package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Symbol command can print or change current shell symbols.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO parse arguments in symbol command
		
		String[] args = Utility.parseNonPathArguments(arguments);
		
		if(args.length == 1) { // Get symbol
			
			switch (args[0]) {
			case "PROMPT":
				env.getPromptSymbol();
				break;
				
			case "MORELINES":
				env.getMorelinesSymbol();
				break;
				
			case "MULTILINE":
				env.getMultilineSymbol();
				break;
				
			default:
				env.writeln("No such shell symbol.");
				return ShellStatus.CONTINUE;
			}
			
		} else if (args.length == 2) { // Change symbol

			// Is given symbol one character
			if(args[0].length() != 1) {
				env.writeln("Symbols are single character only!");
				return ShellStatus.CONTINUE;
			}
			char newSymbol = args[1].charAt(0);
			
			switch (args[0]) {
			case "PROMPT":
				env.setPromptSymbol(newSymbol);
				break;
				
			case "MORELINES":
				env.setMorelinesSymbol(newSymbol);
				break;
				
			case "MULTILINE":
				env.setMultilineSymbol(newSymbol);
				break;
				
			default:
				env.writeln("No such shell symbol.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Wrong number of arguments.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] {
				"If started with one argument, it must print the given shell symbol.",
				"If started with two arguments, it must change the given shell symbol",
				"given value in the second argument." });
	}

}
