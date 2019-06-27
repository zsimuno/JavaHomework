package hr.fer.zemris.gallery.rest;

/**
 * Class represents one picture.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Picture {

	/** Name of the picture. */
	private String name;
	/** Description of the picture. */
	private String description;
	/** Tags of the picture. */
	private String[] tags;

	/**
	 * Contructor.
	 * 
	 * @param name        of the picture.
	 * @param description of the picture.
	 * @param tags        of the picture.
	 */
	public Picture(String name, String description, String[] tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * @return the name of the picture.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description of the picture.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the tags of the picture.
	 */
	public String[] getTags() {
		return tags;
	}

}
