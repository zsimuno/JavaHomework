package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * 
 * TODO javadoc
 * 
 * @author Zvonimir Šimunović
 *
 */
public class RayCaster {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
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

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D p1 = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)));
						Point3D p2 = yAxis.scalarMultiply(vertical * y / (height - 1));
						Point3D screenPoint = p1.sub(p2);
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}

		};

	}

	/**
	 * @param scene
	 * @param ray
	 * @param rgb
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		rgb[0] = 255;
		rgb[1] = 255;
		rgb[2] = 255;
	}

	/**
	 * Finds the closest intersection between the given ray and objects in the
	 * scene.
	 * 
	 * @param scene scene we are looking at.
	 * @param ray   ray that we look for intersection with.
	 * @return the closest intersection between the given ray and objects in the
	 *         scene or {@code null} if there is no intersection.
	 */
	public static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection result = null;
		double closestDistance = -1;
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection inter = object.findClosestRayIntersection(ray);
			if (inter == null)
				continue;

			if (inter.getDistance() < closestDistance || closestDistance == -1) {
				closestDistance = inter.getDistance();
				result = inter;
			}
		}
		return result;
	}

	/**
	 * Determines color for a given {@code point} in the given {@code scene}.
	 * 
	 * @param point point we determine color from.
	 * @param scene scene in which the point is positioned in.
	 * @return rgb color of the point.
	 */
	public static short[] determineColorFor(Point3D point, Scene scene) {
		short[] rgb = new short[] { 15, 15, 15 };
		for (LightSource ls : scene.getLights()) {
			Ray ray = Ray.fromPoints(ls.getPoint(), point);
			RayIntersection closest = findClosestIntersection(scene, ray);

			if (closest != null && closest.getDistance() < ls.getPoint().sub(point).norm())
				continue;

			rgb[0] += closest.getKdr() + closest.getKrr();
			rgb[1] += closest.getKdg() + closest.getKrg();
			rgb[2] += closest.getKdb() + closest.getKrb();
		}

		return rgb;
	}

}
