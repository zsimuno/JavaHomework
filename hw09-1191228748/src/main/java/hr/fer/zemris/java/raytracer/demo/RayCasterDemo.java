package hr.fer.zemris.java.raytracer.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.RayCaster;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
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
public class RayCasterDemo {

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
				System.out.println("Parametri koje je dobila metoda");
				System.out.println("===============================");
				System.out.println("eye: " + pointToString(eye));
				System.out.println("view:  " + pointToString(view));
				System.out.println("viewUp:  " + pointToString(viewUp));
				System.out.println("width:  " + width);
				System.out.println("height: " + +height);
				System.out.println("horizontal: " + +horizontal);
				System.out.println("vertical: " + vertical);

				Point3D vUp = viewUp.normalize();

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = vUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));
				
				System.out.println("Izračunato");
				System.out.println("===============================");
				System.out.println("X-vektor:: " + pointToString(xAxis));
				System.out.println("Y-vektor::  " + pointToString(yAxis));
				System.out.println("Z-vektor::  " + pointToString(zAxis));
				System.out.println("Screen-corner:  " + pointToString(screenCorner));

				Scene scene = RayTracerViewer.createPredefinedScene();

				List<Integer> xValues = Arrays.asList(0, width / 3, width / 2, 2 * width / 3, width - 1);
				List<Integer> yValues = Arrays.asList(0, height / 3, height / 2, 2 * height / 3, height - 1);

				short[] rgb = new short[3];
				for (int y : yValues) {
					for (int x : xValues) {
						Point3D p1 = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)));
						Point3D p2 = yAxis.scalarMultiply(vertical * y / (height - 1));
						Point3D screenPoint = p1.sub(p2);
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						System.out.println();
						System.out.println("Informacije za točku x=" + x + ", y=" + y);
						System.out.println("Screen-point:" + pointToString(screenPoint));
						System.out.printf("Ray: start=" + pointToString(ray.start));
						System.out.println(",direction=" + pointToString(ray.direction));
						System.out.println("RGB =" + Arrays.toString(rgb));
						System.out.println();

					}
				}

			}

		};

	}

	/**
	 * @param scene
	 * @param ray
	 * @param rgb
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection closest = RayCaster.findClosestIntersection(scene, ray);
		if (closest == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			return;
		}
		short[] newRgb = RayCaster.determineColorFor(closest, scene, ray);
		rgb[0] = newRgb[0];
		rgb[1] = newRgb[1];
		rgb[2] = newRgb[2];

	}

	private static String pointToString(Point3D point) {
		return String.format("(%.6f, %.6f, %.6f)", point.x, point.y, point.z);
	}

}
