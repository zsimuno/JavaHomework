package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

/**
 * Demo program for the puzzle.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SlagalicaDemo {
	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. (not used in this program)
	 */
	public static void main(String[] args) {
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(new int[] { 2, 3, 0, 1, 4, 6, 7, 5, 8 }));
		solve(slagalica);
		System.out.println("-------------------------------------------------------------");
		slagalica = new Slagalica(new KonfiguracijaSlagalice(new int[] { 1, 6, 4, 5, 0, 2, 8, 7, 3 }));
		solve(slagalica);

	}

	/**
	 * Solves the puzzle if possible.
	 * 
	 * @param slagalica puzzle to be solved.
	 */
	private static void solve(Slagalica slagalica) {
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
		}
	}
}