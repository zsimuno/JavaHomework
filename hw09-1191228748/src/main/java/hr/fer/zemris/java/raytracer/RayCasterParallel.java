package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * RayCaster program that renders a predefined scene using threads.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class RayCasterParallel {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0), 
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 
				20, 20);
	}

	/**
	 * Method that constructs and returns the {@link IRayTracerProducer} object
	 * needed to draw the scene.
	 * 
	 * @return {@link IRayTracerProducer} object needed to draw the scene.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D vUp = viewUp.normalize();

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = vUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();

				pool.invoke(new ComputingJob(red, green, blue, xAxis, yAxis, scene, eye, screenCorner, width, height, 0,
						height - 1, horizontal, vertical));

				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};

	}

	/**
	 * Class that works out ray-casting using multiple threads.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class ComputingJob extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		/** Red values of pixels. */
		private short[] red;
		/** Green values of pixels. */
		private short[] green;
		/** Blue values of pixels. */
		private short[] blue;
		/** Unit vector that represents the x axis. */
		private Point3D xAxis;
		/** Unit vector that represents the y axis. */
		private Point3D yAxis;
		/** Scene which we draw. */
		private Scene scene;
		/** Position of the eye of the viewer. */
		private Point3D eye;
		/** Position of the corner of the screen. */
		private Point3D screenCorner;
		/** Width of the screen in pixels. */
		private int width;
		/** Height of the screen in pixels. */
		private int height;
		/** Horizontal length. */
		private double horizontal;
		/** Vertical length. */
		private double vertical;
		/** Minimal value for y. */
		private int yMin;
		/** Maximal value for y. */
		private int yMax;

		/** Threshold for stopping the recursive calling. */
		static final int treshold = 8 * Runtime.getRuntime().availableProcessors();

		/**
		 * Constructor.
		 *
		 * @param red          Red values of pixels.
		 * @param green        Green values of pixels.
		 * @param blue         Blue values of pixels.
		 * @param xAxis        Unit vector that represents the x axis.
		 * @param yAxis        Unit vector that represents the y axis.
		 * @param scene        Scene which we draw.
		 * @param eye          Position of the eye of the viewer.
		 * @param screenCorner Position of the corner of the screen.
		 * @param width        Width of the screen in pixels.
		 * @param height       Height of the screen in pixels.
		 * @param yMin         Minimal value for y.
		 * @param yMax         Maximal value for y.
		 * @param horizontal   Vertical length.
		 * @param vertical     Horizontal length.
		 */
		public ComputingJob(short[] red, short[] green, short[] blue, Point3D xAxis, Point3D yAxis, Scene scene,
				Point3D eye, Point3D screenCorner, int width, int height, int yMin, int yMax, double horizontal,
				double vertical) {
			super();
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.scene = scene;
			this.eye = eye;
			this.screenCorner = screenCorner;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		protected void compute() {

			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new ComputingJob(red, green, blue, xAxis, yAxis, scene, eye, screenCorner, width, height, yMin,
							yMin + (yMax - yMin) / 2, horizontal, vertical),
					new ComputingJob(red, green, blue, xAxis, yAxis, scene, eye, screenCorner, width, height,
							yMin + (yMax - yMin) / 2, yMax, horizontal, vertical));

		}

		/**
		 * Computation that is done when recursion reaches it's threshold.
		 */
		private void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D p1 = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)));
					Point3D p2 = yAxis.scalarMultiply(vertical * y / (height - 1));
					Point3D screenPoint = p1.sub(p2);
					Ray ray = Ray.fromPoints(eye, screenPoint);

					RayCaster.tracer(scene, ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}
		}
	}

}
