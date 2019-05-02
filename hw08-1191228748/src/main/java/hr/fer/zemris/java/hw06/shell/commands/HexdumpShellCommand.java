package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code hexdump} command of the shell. Command produces a
 * hex-output of the given file.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 1) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		Path path;
		try {
			path = Utility.resolveDir(Paths.get(args[0]), env);

		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(path)) {
			env.writeln("Can't use the command on path that doesn't exist!");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(path)) {
			env.writeln("Can't use the command on directories!");
			return ShellStatus.CONTINUE;
		}

		// Open the file input stream
		try (InputStream is = Files.newInputStream(path)) {
			byte[] buff = new byte[1024];
			int currentLine = 0;
			int lineCount = 0;
			StringBuilder hexLine = new StringBuilder();
			StringBuilder textLine = new StringBuilder();

			while (true) {
				int r = is.read(buff);
				if (r == -1)
					break;

				// Turn to array
				String[] hexArray = Utility.bytetohex(buff, r);

				// Prepare to write and write if needed
				for (int i = 0; i < hexArray.length; i++) {
					lineCount++;

					hexLine.append(hexArray[i] + ((lineCount == 8) ? "|" : " "));

					// If last line
					if (i == hexArray.length - 1) {
						// Append whitespace in place of missing hex strings
						while (lineCount != 16) {
							lineCount++;

							hexLine.append((lineCount == 8) ? "  |" : "   ");

						}
					}

					// Append text
					if (buff[i] < 32 || buff[i] > 127) {
						textLine.append(".");

					} else {
						textLine.append(new String(buff, i, 1, Charset.defaultCharset()));
					}

					// Write a new line if needed
					if (lineCount == 16) {
						lineCount = 0;

						StringBuilder output = new StringBuilder();

						String count = Integer.toHexString(currentLine).toUpperCase();

						output.append("0".repeat(8 - count.length()) + count + ": ");

						output.append(hexLine.toString() + " | " + textLine.toString());

						env.writeln(output.toString());

						hexLine.setLength(0);
						textLine.setLength(0);
						currentLine += 16;
					}

				}

			}
		} catch (IOException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;

		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Expects a single argument: file name, and produces hex-output." });
	}

}
