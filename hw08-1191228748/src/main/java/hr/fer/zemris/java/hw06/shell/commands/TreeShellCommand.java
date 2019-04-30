package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

/**
 * Represents the {@code tree} command for shell. Command prints a tree of the
 * given directory.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TreeShellCommand implements ShellCommand {

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

		Path directory;
		try {
			directory = Utility.resolveDir(Paths.get(args[0]), env);

		} catch (Exception e) {
			env.writeln("Problem with given path!");
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(directory)) {
			env.writeln("Argument is not a directory!");
			return ShellStatus.CONTINUE;
		}

		FileVisitor<Path> printTree = new FileVisitor<Path>() {
			private int level;

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				env.writeln(" ".repeat(2 * level) + dir.getFileName());
				level++;
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				env.writeln(" ".repeat(2 * level) + file.getFileName());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				level--;
				return FileVisitResult.CONTINUE;
			}
		};

		try {
			Files.walkFileTree(directory, printTree);

		} catch (Exception e) {
			env.writeln("Eror while drawing tree: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.turnToUnmodifiableList(
				new String[] { "Expects a single argument: directory name and prints a directory tree." });
	}

}
