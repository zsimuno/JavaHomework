package hr.fer.zemris.java.raytracer.model;

/**
 * @author Zvonimir Šimunović
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * @param center
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {

	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		return null;
	}
}