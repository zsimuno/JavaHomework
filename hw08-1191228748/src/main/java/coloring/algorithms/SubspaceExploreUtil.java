package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Contains static methods for exploring the subspace.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Breadth-First Search algorithm.
	 * 
	 * @param s0         state of the first element.
	 * @param process    processes the element he fulfills needed conditions.
	 * @param succ       returns a list of successors of the element.
	 * @param acceptable checks whether an element is acceptable for processing.
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toVisit = new LinkedList<>();
		toVisit.add(s0.get());
		while (!toVisit.isEmpty()) {
			S current = toVisit.remove(0);
			if (!acceptable.test(current))
				continue;
			process.accept(current);
			toVisit.addAll(succ.apply(current));
		}
	}

	/**
	 * Depth-First Search algorithm.
	 * 
	 * @param s0         state of the first element.
	 * @param process    processes the element he fulfills needed conditions.
	 * @param succ       returns a list of successors of the element.
	 * @param acceptable checks whether an element is acceptable for processing.
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toVisit = new LinkedList<>();
		toVisit.add(s0.get());
		while (!toVisit.isEmpty()) {
			S current = toVisit.remove(0);
			if (!acceptable.test(current))
				continue;
			process.accept(current);
			toVisit.addAll(0, succ.apply(current));
		}
	}

	/**
	 * Breadth-First Search algorithm optimized.
	 * 
	 * @param s0         state of the first element.
	 * @param process    processes the element he fulfills needed conditions.
	 * @param succ       returns a list of successors of the element.
	 * @param acceptable checks whether an element is acceptable for processing.
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> toVisit = new LinkedList<>();
		toVisit.add(s0.get());
		Set<S> visited = new HashSet<>();
		visited.add(s0.get());
		while (!toVisit.isEmpty()) {
			S current = toVisit.remove(0);
			if (!acceptable.test(current))
				continue;
			process.accept(current);

			List<S> children = succ.apply(current);
			for (S child : children) {
				if (visited.contains(child))
					continue;
				toVisit.add(child);
				visited.add(child);
			}

		}
	}
}
