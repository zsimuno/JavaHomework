package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * 
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SlagalicaMain {

	public static void main(String[] args) {
		if (args.length != 1 || args[0].length() != 9) {
			System.out.println("Only one argument with nine numbers is allowed!");
			return;
		}

		char[] charArray = args[0].toCharArray();
		int[] arr = new int[9];
		for (int i = 0; i < charArray.length; i++) {
			try {
				arr[i] = Integer.parseInt(Character.toString(charArray[i]));
			} catch (Exception e) {
				System.out.println("Only numbers are allowed!");
				return;
			}
		}

		Slagalica slagalica;
		try {
			slagalica = new Slagalica(new KonfiguracijaSlagalice(arr));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

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
			SlagalicaViewer.display(rješenje);
		}
	}
}
