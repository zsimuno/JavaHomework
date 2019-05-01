package searching.algorithms;

/**
 * Represents one node in a tree of states.
 * 
 * @author Zvonimir Šimunović
 *
 * @param <S> type of the state contained in the node.
 */
public class Node<S> {

	/**
	 * State of the node.
	 */
	private S state;
	/**
	 * Cost of this node.
	 */
	private double cost;
	/**
	 * Parent of this node.
	 * 
	 */
	private Node<S> parent;

	/**
	 * Constructs a new {@code Node} from given parameters.
	 * 
	 * @param state state of the node.
	 * @param cost cost of this node.
	 * @param parent parent of this node.
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.state = state;
		this.cost = cost;
		this.parent = parent;
	}

	/**
	 * @return the state of this node
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return the cost of this node.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return the parent of this node.
	 */
	public Node<S> getParent() {
		return parent;
	}

}
