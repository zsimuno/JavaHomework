package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class for searching algorithms.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SearchUtil {

	/**
	 * Breadth-First Search algorithm.
	 * 
	 * @param s0   state of the first element.
	 * @param succ returns the list of pairs of children of the given element.
	 * @param goal checks if the goal was achieved.
	 * @return returns the node of the node that achieved the goal.
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toVisit = new LinkedList<>();
		toVisit.add(new Node<>(null, s0.get(), 0));
		while (!toVisit.isEmpty()) {
			Node<S> current = toVisit.remove(0);
			if (goal.test(current.getState()))
				return current;

			List<Transition<S>> children = succ.apply(current.getState());
			for (Transition<S> child : children) {
				toVisit.add(new Node<S>(current, child.getState(), current.getCost() + child.getCost()));
			}

		}
		return null;

	}

	/**
	 * Breadth-First Search algorithm newer.
	 * 
	 * @param s0   state of the first element.
	 * @param succ returns the list of pairs of children of the given element.
	 * @param goal checks if the goal was achieved.
	 * @return returns the node of the node that achieved the goal.
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> toVisit = new LinkedList<>();
		toVisit.add(new Node<>(null, s0.get(), 0));
		Set<S> visited = new HashSet<>();
		visited.add(s0.get());
		while (!toVisit.isEmpty()) {
			Node<S> current = toVisit.remove(0);
			if (goal.test(current.getState()))
				return current;

			List<Transition<S>> children = succ.apply(current.getState());
			for (Transition<S> child : children) {
				if (visited.contains(child.getState()))
					continue;
				toVisit.add(new Node<S>(current, child.getState(), current.getCost() + child.getCost()));
				visited.add(child.getState());
			}

		}
		return null;

	}
}
