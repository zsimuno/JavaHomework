package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
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
		// TODO LsShellCommand executeCommand
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = Paths.get("d:/tmp/javaPrimjeri/readme.txt");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		System.out.println(formattedDateTime);
		return null;
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
				"writable ( w ) and executable ( x ). Second column contains object size in bytes." ,
				"Follows file creation date/time and finally file name."});
	}

}
