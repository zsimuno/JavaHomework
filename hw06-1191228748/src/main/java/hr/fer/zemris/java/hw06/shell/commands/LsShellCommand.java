package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents {@code ls} command for the shell. Command writes the given
 * directory listing (not recursive).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LsShellCommand implements ShellCommand {

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

		// Get the directory path
		Path directoryPath;
		try {
			directoryPath = Paths.get(args[0]);
			
		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(directoryPath)) {
			env.writeln("Not a directory!");
			return ShellStatus.CONTINUE;
		}

		// Get all files and directories and write the necessary data
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Path path : stream) {

				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();

				// First column
				String directory = attributes.isDirectory() ? "d" : "-";
				String readable = Files.isReadable(path) ? "r" : "-";
				String writable = Files.isWritable(path) ? "w" : "-";
				String executable = Files.isExecutable(path) ? "x" : "-";

				// Second column
				String fileSize = Long.toString(Files.size(path));
				fileSize = " ".repeat(10 - fileSize.length()) + fileSize;

				// Third column
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

				// Last column
				String name = path.getFileName().toString();

				// Write it to the user
				env.writeln(directory + readable + writable + executable + " " + fileSize + " " + formattedDateTime
						+ " " + name);

			}
		} catch (IOException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] {
				"takes a single argument – directory – and writes a directory listing (not recursive).",
				"The output consists of 4 columns. First column indicates if current object is directory ( d ), readable ( r ),",
				"writable ( w ) and executable ( x ). Second column contains object size in bytes.",
				"Follows file creation date/time and finally file name." });
	}

}
