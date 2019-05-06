package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * TODO javadoc
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Newton {

	private static ArrayList<Complex> roots = new ArrayList<>(); // TODO mozda bolje prebacit
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		for (int i = 0;; i++) {
			System.out.printf("Root %d> ", i + 1);
			String input = sc.nextLine();
			if (input.equals("done"))
				break;

			try {
				roots.add(Complex.parse(input));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.out.println("Input again!");
				i--;
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();
		FractalViewer.show(new MyProducer());
	}

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		/**
		 * 
		 */
		private static final int numberOfIterations = 16*16*16; //Puno?

		/**
		 * 
		 */
		private static final double convergenceThreshold = 1e-3;

		/**
		 * 
		 */
		private static final double rootThreshold = 2e-3;
		
		

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			// TODO Auto-generated method stub
			

			ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ZERO,
					roots.toArray(new Complex[roots.size()]));
			ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();

			short[] data = new short[width * height];
			int offset = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x, y, 0, width, 0, height, reMin, reMax, imMin, imMax);
					int iter = 0;
					double module;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while (module > convergenceThreshold && iter < numberOfIterations);
					data[offset++] = (short) (rootedPolynomial.indexOfClosestRootFor(zn, rootThreshold) + 1);
				}
			}
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

		/**
		 * @param x
		 * @param y
		 * @param i
		 * @param width
		 * @param j
		 * @param height
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @return
		 */
		private Complex mapToComplexPlain(int x, int y, int i, int width, int j, int height, double reMin, double reMax,
				double imMin, double imMax) {
			double re = x / (width - 1.0) * (reMax - reMin) + reMin;
			double im = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
			return new Complex(re, im);
		}
	}

}
