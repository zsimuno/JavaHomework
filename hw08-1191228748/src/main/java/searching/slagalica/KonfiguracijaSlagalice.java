package searching.slagalica;

import java.util.Arrays;

/**
 * One configuration of the puzzle.
 * 
 * @author Zvonimir Šimunović
 *
 */
/**
 * @author Zvonimir Šimunović
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * Current configuration of the puzzle.
	 */
	private int[] configuration;

	/**
	 * 
	 * Constructs an {@code KonfiguracijaSlagalice} object with the given
	 * configuration.
	 * 
	 * @param configuration configuration of the puzzle.
	 * @throws IllegalArgumentException if {@code configuration} is not in valid
	 *                                  form.
	 */
	public KonfiguracijaSlagalice(int[] configuration) {
		if (configuration.length != 9)
			throw new IllegalArgumentException("Configuration must have 9 elements!");

		int[] counter = new int[9];
		for (int i = 0; i < configuration.length; i++) {
			if (configuration[i] < 0 || configuration[i] > 9)
				throw new IllegalArgumentException("Configuration elements must be numbers from 0 to 9!");

			if (counter[i] > 0)
				throw new IllegalArgumentException("Configuration elements must appear only once!");

			counter[i]++;
		}

		this.configuration = configuration;
	}

	/**
	 * Returns the clone of the current configuration of the puzzle.
	 * 
	 * @return the copy of the configuration.
	 */
	public int[] getPolje() {
		return configuration.clone();
	}

	/**
	 * Index of the space in the puzzle. That is index of 0 in the configuration
	 * array.
	 * 
	 * @return index of the space in the puzzle.
	 */
	public int indexOfSpace() {
		for (int i = 0; i < configuration.length; i++) {
			if (configuration[i] == 0)
				return i;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < configuration.length; i++) {
			if (configuration[i] == 0) {
				sb.append("*");
			} else {
				sb.append(configuration[i]);
			}
			sb.append(i % 3 == 2 ? "\n" : " ");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(configuration);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(configuration, other.configuration);
	}

}
