package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;
import hr.fer.zemris.java.hw06.shell.namebuilder.FilterResult;
import hr.fer.zemris.java.hw06.shell.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw06.shell.namebuilder.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.namebuilder.NameBuilderParserException;

/**
 * Represents the {@code massrename} command for shell. This command massively
 * renames and moves files to new directory.
 * 
 * <p>
 * It supports 4 subcommands.
 * <li>filter - shows the filtering result of the mask</li>
 * <li>groups - shows groups that are made with the filtering results</li>
 * <li>show - shows the file names after the renaming</li>
 * <li>execute - renames and moves the files</li>
 * </p>
 * 
 * @author Zvonimir Šimunović
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// Get arguments
		String[] args;
		try {
			args = Utility.parseMultipleArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 4 && args.length != 5) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}

		Path sourceFilePath;
		Path destinationPath;
		try {
			sourceFilePath = Utility.resolveDir(Paths.get(args[0]), env);
			destinationPath = Utility.resolveDir(Paths.get(args[1]), env);
		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(sourceFilePath) || !Files.isDirectory(destinationPath)) {
			env.writeln("Paths must be paths to existing directories!");
			return ShellStatus.CONTINUE;
		}

		String cmd = args[2];
		String mask = args[3];
		String rest = "";

		if (args.length == 5) {
			rest = args[4];
		}

		List<FilterResult> files;
		try {
			files = filter(sourceFilePath, mask);

		} catch (Exception e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		switch (cmd) {
		case "filter":
			filterCmd(files, env);
			break;

		case "groups":
			groups(files, env);
			break;

		case "show":
			try {
				show(files, mask, rest, env);
			} catch (Exception e) {
				env.writeln("Show cmd error: " + e.getMessage());
				return ShellStatus.CONTINUE;
			}
			break;

		case "execute":
			try {
				execute(files, destinationPath, mask, rest, env);
			} catch (Exception e) {
				env.writeln("Execute cmd error: " + e.getMessage());
				return ShellStatus.CONTINUE;
			}
			break;

		default:
			env.writeln("No such command for massrename!");
			break;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Executes the filter command. Prints files that match the given pattern.
	 * 
	 * @param files list of files in the source directory.
	 * @param env   environment of the command.
	 */
	private void filterCmd(List<FilterResult> files, Environment env) {
		files.forEach((result) -> {
			env.writeln(result.toString());
		});

	}

	/**
	 * Executes the groups command. Prints groups made with filtering.
	 * 
	 * @param files list of files in the source directory.
	 * @param env   environment of the command.
	 */
	private void groups(List<FilterResult> files, Environment env) {
		files.forEach((result) -> {
			env.write(result.toString());
			for (int i = 0, numberOfGroups = result.numberOfGroups(); i <= numberOfGroups; i++) {
				env.write(" " + i + ": " + result.group(i));
			}
			env.write("%n");
		});

	}

	/**
	 * Executes the show command. Shows how the file names will change.
	 * 
	 * @param files list of files in the source directory.
	 * @param mask  regex mask that is used to filter from source directory.
	 * @param rest  name generator of new files.
	 * @param env   environment of the command.
	 * @throws IllegalArgumentException if these is an error with the name
	 *                                  generator.
	 */
	private void show(List<FilterResult> files, String mask, String rest, Environment env) {
		if (rest.isBlank()) {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}

		NameBuilderParser parser;
		try {
			parser = new NameBuilderParser(rest);
		} catch (NameBuilderParserException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		NameBuilder builder = parser.getNameBuilder();
		for (FilterResult file : files) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			String newName = sb.toString();

			String oldName = file.toString();
			env.writeln(oldName + " => " + newName);
		}

	}

	/**
	 * Executes the execute command. Moves the files with their new names.
	 * 
	 * @param files list of files in the source directory.
	 * @param mask  regex mask that is used to filter from source directory.
	 * @param rest  name generator of new files.
	 * @param env   environment of the command.
	 * @throws IllegalArgumentException if these is an error with the name
	 *                                  generator.
	 */
	private void execute(List<FilterResult> files, Path destinationPath, String mask, String rest, Environment env) {
		if (rest.isBlank()) {
			throw new IllegalArgumentException("Invalid number of arguments!");
		}

		NameBuilderParser parser;
		try {
			parser = new NameBuilderParser(rest);
		} catch (NameBuilderParserException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		NameBuilder builder = parser.getNameBuilder();
		for (FilterResult file : files) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			String newName = sb.toString();

			try {
				Files.move(file.getFile(), destinationPath.resolve(newName));
			} catch (IOException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}

	}

	/**
	 * Returns the list of all files that match the given pattern.
	 * 
	 * @param dir     directory we filter from.
	 * @param pattern pattern we are matching the file names against.
	 * @return list of results of the filter.
	 * @throws IOException              if there is a problem with the given
	 *                                  directory.
	 * @throws IllegalArgumentException if there is a problem with the given patter.
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> result = new ArrayList<>();
		Pattern mask;
		try {
			mask = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		} catch (PatternSyntaxException e) {
			throw new IllegalArgumentException("Given pattern is invalid: " + e.getMessage());
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

			for (Path path : stream) {
				Matcher matcher = mask.matcher(path.getFileName().toString());
				if (matcher.matches()) {
					result.add(new FilterResult(path, matcher));
				}

			}
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}

		return result;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(new String[] { "Massively renames and moves files to new directory.",
				"It supports 4 subcommands:", "filter - shows the filtering result of the mask",
				"groups - shows groups that are made with the filtering results",
				"show - shows the file names after the renaming", "execute - renames and moves the files" });
	}

}
