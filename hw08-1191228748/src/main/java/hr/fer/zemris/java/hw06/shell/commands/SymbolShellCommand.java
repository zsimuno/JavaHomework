package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Symbol command can print or change current shell symbols.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] args = Utility.parseNonPathArguments(arguments);
		
		if(args.length == 1) { // Get symbol
			Character symbol;
			switch (args[0]) {
			case "PROMPT":
				symbol = env.getPromptSymbol();
				break;
				
			case "MORELINES":
				symbol = env.getMorelinesSymbol();
				break;
				
			case "MULTILINE":
				symbol = env.getMultilineSymbol();
				break;
				
			default:
				env.writeln("No such shell symbol.");
				return ShellStatus.CONTINUE;
			}
			
			env.writeln("Symbol for " + args[0] + " is '" + symbol.toString() + "'");
			
		} else if (args.length == 2) { // Change symbol

			// Is given symbol one character
			if(args[1].length() != 1) {
				env.writeln("Symbols are single character only!");
				return ShellStatus.CONTINUE;
			}
			Character newSymbol = args[1].charAt(0);
			Character oldSymbol;
			
			switch (args[0]) {
			case "PROMPT":
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(newSymbol);
				break;
				
			case "MORELINES":
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(newSymbol);
				break;
				
			case "MULTILINE":
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(newSymbol);
				break;
				
			default:
				env.writeln("No such shell symbol.");
				return ShellStatus.CONTINUE;
			}
			
			env.writeln("Symbol for " + args[0] + " changed from '" + oldSymbol.toString() + "' to '" + newSymbol.toString() + "'");
			
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
