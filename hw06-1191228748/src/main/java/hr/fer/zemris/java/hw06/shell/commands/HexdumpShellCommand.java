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
		// TODO Hex check and finish
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

		Path path = Paths.get(args[0]);

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

				String[] hexArray = Utility.bytetohex(buff, r);

				for (int i = 0; i < hexArray.length; i++) {
					lineCount++;

					// Append hex text
					if (lineCount == 8) {
						hexLine.append(hexArray[i] + "|");
					} else {
						hexLine.append(hexArray[i] + " ");
					}

					// Append text
					if (buff[i] < 32 || buff[i] > 127) {
						textLine.append(".");

					} else {
						textLine.append(new String(buff, i, 1, Charset.defaultCharset()));
					}

					if (lineCount == 16) {
						lineCount = 0;

						StringBuilder output = new StringBuilder();

						String count = Integer.toString(currentLine);

						output.append("0".repeat(8 - count.length()) + count + ": ");

						output.append(hexLine.toString() + " | " + textLine.toString());

						env.writeln(output.toString());

						hexLine.setLength(0);
						textLine.setLength(0);
						currentLine += 10;
					}

				}

				// If there are still lines to write
				if (lineCount != 0) {
					StringBuilder output = new StringBuilder();

					while (lineCount != 16) {
						lineCount++;
						
						if (lineCount == 8) {
							hexLine.append("  |");
						} else {
							hexLine.append("   ");
						}
					}

					String count = Integer.toString(currentLine);

					output.append("0".repeat(8 - count.length()) + count + ": ");

					output.append(hexLine.toString() + " | " + textLine.toString());

					env.writeln(output.toString());

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
