package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program used for drawing fractals with approximating the roots of a
 * polynomial.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Newton {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args Command line arguments. (Not used here)
	 */
	public static void main(String[] args) {

		FractalViewer.show(new MyProducer());

	}

	/**
	 * Fractal producer that produces a fractal based on roots of the given
	 * polynomial and index of those roots.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class MyProducer implements IFractalProducer {

		/**
		 * Polynomial constructed from given roots.
		 */
		private ComplexRootedPolynomial rootedPolynomial;
		/**
		 * Polynomial constructed from the rooted polynomial.
		 */
		private ComplexPolynomial polynomial;
		/**
		 * Polynomial constructed as a derivation of the given polynomial.
		 */
		private ComplexPolynomial derived;

		/**
		 * Constructs a new producer based on roots of the polynomial that user inputs.
		 * 
		 */
		public MyProducer() {
			System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
			Scanner sc = new Scanner(System.in);
			ArrayList<Complex> roots = new ArrayList<>();
			while (true) {
				System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
				

				for (int i = 0;; i++) {
					System.out.printf("Root %d> ", i + 1);
					String input = sc.nextLine();
					if (input.equals("done"))
						break;

					try {
						roots.add(Complex.parse(input));
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
						System.out.println("Input again:");
						i--;
					}
				}

				if (roots.size() < 2) {
					System.out.println("At least two roots must be entered!");
					roots.clear();
					continue;
				}
				System.out.println("Image of fractal will appear shortly. Thank you.");
				break;
			}

			sc.close();

			rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[roots.size()]));
			polynomial = rootedPolynomial.toComplexPolynom();
			derived = polynomial.derive();
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {

			int availableProcessors = Runtime.getRuntime().availableProcessors();
			int trackNumber = (8 * availableProcessors);
			int range = height / trackNumber;

			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
			List<Future<Void>> results = new ArrayList<>();

			short[] data = new short[width * height];

			for (int i = 0; i < trackNumber; i++) {
				int yMin = i * range;
				int yMax = (i + 1) * range;
				if (i == trackNumber - 1) {
					yMax = height - 1;
				}
				CalculatingJob job = new CalculatingJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
						rootedPolynomial, derived);
				results.add(pool.submit(job));
			}

			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			pool.shutdown();

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}

	/**
	 * Represents the calculating job for out threads.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class CalculatingJob implements Callable<Void> {
		/**
		 * Minimal real.
		 */
		double reMin;
		/**
		 * Maximum real.
		 */
		double reMax;
		/**
		 * Minimal Imaginary.
		 */
		double imMin;
		/**
		 * Maximum Imaginary.
		 */
		double imMax;
		/**
		 * Width of the screen.
		 */
		int width;
		/**
		 * Height of the screen.
		 */
		int height;
		/**
		 * Minimal y value.
		 */
		int yMin;
		/**
		 * Maximal y value.
		 */
		int yMax;
		/**
		 * Data to be written in.
		 */
		short[] data;
		/**
		 * Rooted polynomial we approximate zeros of.
		 */
		private ComplexRootedPolynomial rootedPolynomial;
		/**
		 * Derivation of the given rooted polynomial.
		 */
		private ComplexPolynomial derived;
		/**
		 * Max number of iterations.
		 */
		private static final int numberOfIterations = 60; // TODO koliko?

		/**
		 * Threshold for convergence.
		 */
		private static final double convergenceThreshold = 1e-3;

		/**
		 * Threshold for closest root.
		 */
		private static final double rootThreshold = 2e-3;

		/**
		 * Constructs a new {@code CalculatingJob} from given parameters.
		 *
		 * @param reMin            minimum value of real part of the complex number.
		 * @param reMax            maximum value of real part of the complex number.
		 * @param imMin            minimum value of imaginary part of the complex
		 *                         number.
		 * @param imMax            maximum value of imaginary part of the complex
		 *                         number.
		 * @param width            width of the screen.
		 * @param height           height of the screen.
		 * @param yMin             minimum value of y.
		 * @param yMax             maximum value of y.
		 * @param data             data to be filled with indexes of closest roots.
		 * @param derived          Derivation of the given rooted polynomial.
		 * @param rootedPolynomial Rooted polynomial we approximate zeros of.
		 */
		public CalculatingJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data, ComplexRootedPolynomial rootedPolynomial, ComplexPolynomial derived) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.rootedPolynomial = rootedPolynomial;
			this.derived = derived;
		}

		@Override
		public Void call() throws Exception {
			int xMin = 0, xMax = width; // TODO zasto nam treba min?
			int offset = yMin * xMax;
			for (int y = yMin; y < yMax; y++) {
				for (int x = xMin; x < xMax; x++) {
					Complex zn = mapToComplexPlain(x, y, 0, width, xMin, xMax, reMin, reMax, imMin, imMax);
					int iter = 0;
					double module;
					do {
						Complex numerator = rootedPolynomial.apply(zn);
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
			return null;

		}

		/**
		 * Maps a pixel dot to complex plane.
		 * 
		 * @param x     x of the pixel.
		 * @param y     y of the pixel.
		 * @param yMin  minimum value of y.
		 * @param yMax  maximum value of y.
		 * @param xMin  minimum value of y.
		 * @param xMax  maximum value of y.
		 * @param reMin minimum value of real part of the complex number.
		 * @param reMax maximum value of real part of the complex number.
		 * @param imMin minimum value of imaginary part of the complex number.
		 * @param imMax maximum value of imaginary part of the complex number.
		 * @return given pixel mapped to the complex plane.
		 */
		private Complex mapToComplexPlain(int x, int y, int yMin, int yMax, int xMin, int xMax, double reMin,
				double reMax, double imMin, double imMax) {
			double re = x / (xMax - 1.0) * (reMax - reMin) + reMin;
			double im = (yMax - 1.0 - y) / (yMax - 1) * (imMax - imMin) + imMin;
			return new Complex(re, im);
		}

	}

	/**
	 * Thread factory that sets all threads as daemon.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;

		}

	}

}
