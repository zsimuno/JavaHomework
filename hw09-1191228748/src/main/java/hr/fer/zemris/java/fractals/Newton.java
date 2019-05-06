package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
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

	private static ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE,
			roots.toArray(new Complex[roots.size()]));
	private static ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
	private static ComplexPolynomial derived = polynomial.derive();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer");
//		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
//		Scanner sc = new Scanner(System.in);
//		for (int i = 0;; i++) {
//			System.out.printf("Root %d> ", i + 1);
//			String input = sc.nextLine();
//			if (input.equals("done"))
//				break;
//
//			try {
//				roots.add(Complex.parse(input));
//			} catch (IllegalArgumentException e) {
//				System.out.println(e.getMessage());
//				System.out.println("Input again!");
//				i--;
//			}
//		}
//		System.out.println("Image of fractal will appear shortly. Thank you.");
//		sc.close();

		rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[roots.size()]));
		polynomial = rootedPolynomial.toComplexPolynom();
		derived = polynomial.derive();

		roots.add(Complex.ONE);
		roots.add(Complex.ONE_NEG);
		roots.add(Complex.IM);
		roots.add(Complex.IM_NEG);

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
		private static final int numberOfIterations = 60; // TODO koliko?

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

			int availableProcessors = Runtime.getRuntime().availableProcessors();
			int trackNumber = (8 * availableProcessors);
			int range = height / trackNumber;

			ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
			List<Future<Void>> results = new ArrayList<>();

			short[] data = new short[width * height];

			for (int i = 0; i < trackNumber; i++) {
				int yMin = i * range;
				int yMax = (i + 1) * range - 1;
				if (i == trackNumber - 1) {
					yMax = height - 1;
				}
				CalculatingJob posao = new CalculatingJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
				results.add(pool.submit(posao));
			}
			for (Future<Void> posao : results) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			pool.shutdown();

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}

	public static class CalculatingJob implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		short[] data;
		/**
		 * 
		 */
		private static final int numberOfIterations = 60; // TODO koliko?

		/**
		 * 
		 */
		private static final double convergenceThreshold = 1e-3;

		/**
		 * 
		 */
		private static final double rootThreshold = 2e-3;

		public static CalculatingJob NO_JOB = new CalculatingJob();

		private CalculatingJob() {
		}

		public CalculatingJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data) {
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
		}

		@Override
		public void run() {
			int xMin = 0, xMax = width; // TODO zasto nam treba min?
			int offset = 0;
			for (int y = yMin; y < yMax; y++) {
				for (int x = xMin; x < xMax; x++) {
					Complex zn = mapToComplexPlain(x, y, yMin, yMax, xMin, xMax, reMin, reMax, imMin, imMax);
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

					data[yMin + offset++] = (short) (rootedPolynomial.indexOfClosestRootFor(zn, rootThreshold) + 1);
				}
			}

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
	 * @author Zvonimir Šimunović
	 *
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return null;

		}

	}

}
