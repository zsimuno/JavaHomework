package searching.slagalica;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Represents one puzzle element;
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * Initial configuration of the puzzle.
	 */
	private KonfiguracijaSlagalice initialConfiguration;

	/**
	 * Array of integers that we get when the goal is achieved.
	 */
	private static int[] goal = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

	/**
	 * Default price of a change.
	 */
	private static int defaultPrice = 1;

	/**
	 * Constructs a puzzle with a given initial configuration.
	 * 
	 * @param initialConfiguration initial configuration of the puzzle.
	 */
	public Slagalica(KonfiguracijaSlagalice initialConfiguration) {
		this.initialConfiguration = initialConfiguration;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return initialConfiguration;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		int spaceIndex = t.indexOfSpace();
		switch (spaceIndex) {
		case 0:
			return listWithChanged(t, spaceIndex, 1, 3);
		case 1:
			return listWithChanged(t, spaceIndex, 0, 4, 2);
		case 2:
			return listWithChanged(t, spaceIndex, 1, 5);
		case 3:
			return listWithChanged(t, spaceIndex, 0, 4, 6);
		case 4:
			return listWithChanged(t, spaceIndex, 1, 3, 5, 7);
		case 5:
			return listWithChanged(t, spaceIndex, 2, 4, 8);
		case 6:
			return listWithChanged(t, spaceIndex, 3, 7);
		case 7:
			return listWithChanged(t, spaceIndex, 4, 6, 8);
		case 8:
			return listWithChanged(t, spaceIndex, 5, 7);
		default:
			throw new IllegalArgumentException("Configuration must not have 9 elements!");
		}

	}

	/**
	 * Returns a list of new configurations that have the index of the space element
	 * changed with one of the given indexes. They are derived from the given
	 * configuration.
	 * 
	 * @param t          initial configuration to be changed.
	 * @param spaceIndex index of space element in the array.
	 * @param change     indexes of elements that will be moved to the empty space.
	 * @return a list of new configurations that have the index of the space element
	 *         changed with one of the given indexes.
	 */
	private List<Transition<KonfiguracijaSlagalice>> listWithChanged(KonfiguracijaSlagalice t, int spaceIndex,
			int... change) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();

		for (int k = 0; k < change.length; k++) {
			int[] newConfig = t.getPolje();
			int temp = newConfig[spaceIndex];
			newConfig[spaceIndex] = newConfig[change[k]];
			newConfig[change[k]] = temp;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConfig), defaultPrice));

		}

		return list;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(t.getPolje(), goal);
	}

}
