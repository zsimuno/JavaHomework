package searching.algorithms;

/**
 * Represents a pair of (state, cost).
 * 
 * @author Zvonimir Šimunović
 *
 * @param <S> type of the state.
 */
public class Transition<S> {

	/**
	 * State in the pair.
	 */
	private S state;
	/**
	 * Cost in the pair.
	 */
	private double cost;

	/**
	 * Constructs a new {@code Transition} from given parameters.
	 * 
	 * @param state state of the pair.
	 * @param cost cost of the pair.
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * @return the state of the pair.
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return the cost of the pair.
	 */
	public double getCost() {
		return cost;
	}

}
