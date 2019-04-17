package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		// TODO Copy finish maybe
		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 2) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		Path sourceFilePath = Paths.get(args[0]);
		Path destinationPath = Paths.get(args[1]); // Can be file or directory

		if (Files.isDirectory(sourceFilePath)) {
			env.writeln("Source path must be a file!");
			return ShellStatus.CONTINUE;
		}

		// If destination directory than add the file with the same name to that
		// directory
		if (Files.isDirectory(destinationPath)) {
			destinationPath = Paths.get(destinationPath.toString(), sourceFilePath.getFileName().toString());
		}
		
		
		// Ignore if equal files and move on
		if(sourceFilePath.equals(destinationPath)) {
			return ShellStatus.CONTINUE;
		}

		
		// If file exists ask user to overwrite or not
		if (Files.exists(destinationPath)) {
			while (true) {
				env.writeln("Destination file exists. Do you want to overwrite it? (yes/no)");
				env.write(env.getPromptSymbol().toString());

				String userInput = env.readLine();

				if (userInput.equalsIgnoreCase("yes")) {
					break;

				} else if (userInput.equalsIgnoreCase("no")) {
					return ShellStatus.CONTINUE;
				}
			}

		}
		
		if(!Files.exists(destinationPath.getParent())) {
			env.writeln("Parent directory of the file doesn't exist!");
			return ShellStatus.CONTINUE;
		}

		// Open the file input and output streams
		try (InputStream is = Files.newInputStream(sourceFilePath);
				OutputStream os = Files.newOutputStream(destinationPath)) {
			byte[] buff = new byte[1024];

			while (true) {
				// Read from file
				int r = is.read(buff);

				if (r == -1)
					break;

				// Write to file
				os.write(buff, 0, r);
			}
			
		} catch (IOException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;

		}

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
				"If destination file exists, overwrites it if allowed.", "If the second argument is directory,",
				"copies the original file into that directory using the original file name." });
	}

}
