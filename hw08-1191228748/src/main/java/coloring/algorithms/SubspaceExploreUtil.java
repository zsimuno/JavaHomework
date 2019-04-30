package coloring.algorithms;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Zvonimir Šimunović
 *
 */
public class SubspaceExploreUtil {

	/**
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		// TODO bfs method
	}

	/**
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		// TODO dfs method
	}

	/**
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		// TODO bfsv method
	}
}
