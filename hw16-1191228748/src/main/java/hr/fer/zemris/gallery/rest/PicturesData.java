package hr.fer.zemris.gallery.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class that is used for getting data about pictures. On context initialization
 * it saves the real path of the file that contains the descriptions.
 * 
 * @author Zvonimir Šimunović
 *
 */
@WebListener
public class PicturesData implements ServletContextListener {

	/** File path to the txt file with decriptions. */
	private static String filePath;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		filePath = sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}

	/**
	 * Retrieves all available picture tags.
	 * 
	 * @return array of all tags or {@code null} on error.
	 */
	public static String[] getAllTags() {
		List<String> tags = new ArrayList<>();

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		for (int i = 2; i < lines.size(); i += 3) {
			for (String tag : lines.get(i).split(",")) {
				String trimmed = tag.trim();
				if(!tags.contains(trimmed)) {
					tags.add(trimmed);
				}
				
			}
		}

		return tags.toArray(new String[tags.size()]);
	}

	/**
	 * Returns a list of {@link Picture} objects that represents all available
	 * pictures with the given tag.
	 * 
	 * @param queryTag tag we want pictures to have.
	 * @return list of available pictures with the given tag or {@code null} on
	 *         error.
	 */
	public static List<Picture> getAllPicturesWithTag(String queryTag) {
		String trimmedQuery = queryTag.trim();
		List<Picture> info = new ArrayList<>();

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		for (int i = 0; i < lines.size(); i += 3) {
			boolean hasTag = false;
			List<String> tags = new ArrayList<>();
			for (String tag : lines.get(i + 2).split(",")) {
				String trimmed = tag.trim();
				if (trimmedQuery.equals(trimmed)) {
					hasTag = true;
				}
				tags.add(trimmed);
			}

			if (!hasTag)
				continue;

			String name = lines.get(i);
			String description = lines.get(i + 1);
			info.add(new Picture(name, description, tags.toArray(new String[tags.size()])));
		}

		return info;
	}

	/**
	 * Returns the data of the picture with the given {@code name}.
	 * 
	 * @param name name of the picture of which we want the data of.
	 * @return data of the picture with the given {@code name}.
	 */
	public static Picture getPictureFromName(String name) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		Picture picture = null;
		for (int i = 0; i < lines.size(); i += 3) {
			String pictureName = lines.get(i);
			if(!pictureName.equals(name))
				continue;
			
			String description = lines.get(i + 1);
			List<String> tags = new ArrayList<>();
			for (String tag : lines.get(i + 2).split(",")) {
				String trimmed = tag.trim();
				tags.add(trimmed);
			}

			
			picture = new Picture(name, description, tags.toArray(new String[tags.size()]));
		}

		return picture;
	}

}
