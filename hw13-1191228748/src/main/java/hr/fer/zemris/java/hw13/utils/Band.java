package hr.fer.zemris.java.hw13.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * Class that stores band information (id, name and a link to a song).
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Band {

	/** ID of the band. */
	private final String ID;
	/** Name of the band. */
	private final String name;
	/** YouTube link to one of the songs of the band. */
	private final String songLink;

	/**
	 * Constructor.
	 * 
	 * @param iD       ID of the band.
	 * @param name     name of the band.
	 * @param songLink YouTube link to one of the songs of the band.
	 */
	public Band(String iD, String name, String songLink) {
		ID = iD;
		this.name = name;
		this.songLink = songLink;
	}

	/**
	 * @return the iD of the band.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return the name of the band.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the YouTube link to one of the songs of the band.
	 */
	public String getSongLink() {
		return songLink;
	}

	/**
	 * Returns a list of {@link Band} objects that are read from the file with the
	 * file name "glasanje-definicija.txt" from the given request context.
	 * 
	 * @param request context of the request which we use to get the files path on
	 *                the disk.
	 * @return list of {@link Band} objects that are read from the given file.
	 * @throws IOException if there is an error while reading from the file.
	 */
	public static List<Band> readBands(HttpServletRequest request) throws IOException {
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<Band> bandList = new ArrayList<>();
		for (String line : lines) {
			String[] splitLine = line.split("\t");
			bandList.add(new Band(splitLine[0], splitLine[1], splitLine[2]));
		}
		return bandList;
	}

	/**
	 * Returns a map of id's and results of the voting for bands that are read from
	 * the file with the file name "glasanje-rezultati.txt" from the given request
	 * context. If the result file doesn't exist it creates a new one with band id's
	 * and voting results being zero.
	 * 
	 * @param request context of the request which we use to get the files path on
	 *                the disk.
	 * @return map of id's and results of the voting for bands
	 * @throws IOException if there is an error while reading from the file or
	 *                     writing to it.
	 */
	public static Map<String, Integer> readResults(HttpServletRequest request) throws IOException {
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Map<String, Integer> votingResults = new HashMap<>();

		Path file = Paths.get(fileName);
		if (!Files.exists(file)) {
			List<Band> bands = Band.readBands(request);
			List<String> result = new ArrayList<>();

			for (Band band : bands) {
				result.add(band.getID() + "\t" + 0);
				votingResults.put(band.getID(), 0);
			}

			Files.write(file, result, StandardCharsets.UTF_8);
		} else {

			List<String> lines = Files.readAllLines(Paths.get(fileName));
			for (String line : lines) {
				String[] splitLine = line.split("\t");
				votingResults.put(splitLine[0], Integer.parseInt(splitLine[1]));
			}
		}

		return votingResults;
	}

	/**
	 * Returns map with {@link Band} object as keys and band vote count as values
	 * sorted by vote count.
	 * 
	 * @param request context of the request which we use to get the files path on
	 *                the disk.
	 * @return map of {@link Band} objects and results of the voting for bands
	 *         sorted by vote count.
	 * @throws IOException if there is an error while reading from the file or
	 *                     writing to it.
	 */
	public static Map<Band, Integer> readResultsWithBands(HttpServletRequest request) throws IOException {
		List<Band> bands = Band.readBands(request);
		Map<String, Integer> results = Band.readResults(request);
		Map<Band, Integer> resultsWithBands = new HashMap<>();
		for (Band band : bands) {
			resultsWithBands.put(band, results.get(band.getID()));
		}

		Map<Band, Integer> resultsWithBands2 = resultsWithBands.entrySet().stream()
			    .sorted(Entry.comparingByValue((a, b) -> b.compareTo(a)))
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e1, LinkedHashMap::new));
		return  resultsWithBands2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, name, songLink);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Band))
			return false;
		Band other = (Band) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(name, other.name);
	}

}
