package hr.fer.zemris.java.raytracer.model;

/**
 * Represents a sphere as a graphical object.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Sphere extends GraphicalObject {

	/** Center of the sphere. */
	private Point3D center;
	/** Radius of the sphere. */
	private double radius;
	/** Coefficient for diffuse component for red color. */
	private double kdr;
	/** Coefficient for diffuse component for green color. */
	private double kdg;
	/** Coefficient for diffuse component for blue color. */
	private double kdb;
	/** Coefficient for reflective component for red color. */
	private double krr;
	/** Coefficient for reflective component for green color. */
	private double krg;
	/** Coefficient for reflective component for blue color. */
	private double krb;
	/** Coefficient n for reflective component. */
	private double krn;

	/**
	 * Constructor.
	 * 
	 * @param center Center of the sphere.
	 * @param radius Radius of the sphere.
	 * @param kdr    Coefficient for diffuse component for red color.
	 * @param kdg    Coefficient for diffuse component for green color.
	 * @param kdb    Coefficient for diffuse component for blue color.
	 * @param krr    Coefficient for reflective component for red color.
	 * @param krg    Coefficient for reflective component for green color.
	 * @param krb    Coefficient for reflective component for blue color.
	 * @param krn    Coefficient n for reflective component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D originCenter = ray.start.sub(center);
		double b = originCenter.scalarProduct(ray.direction);
		double c = originCenter.scalarProduct(originCenter) - radius * radius;
		double discriminant = b * b - c;

		double distance;

		if (discriminant < 0.0) {
			return null;
		} else if (discriminant > 0.0) {

			double distance1 = -b - Math.sqrt(discriminant);
			double distance2 = -b + Math.sqrt(discriminant);

			if (distance1 < 0.0 && distance2 < 0.0) {
				return null;
			}

			if (distance1 > 0.0 && distance1 < distance2) {
				distance = distance1;
			} else {
				distance = distance2;
			}

		} else {
			distance = -b;
			if (distance < 0) {
				return null;
			}
		}
		boolean outer = ray.start.sub(center).norm() > distance;
		Point3D point = ray.start.add(ray.direction.scalarMultiply(distance));
		
		return new RayIntersection(point, distance, outer) {

			@Override
			public Point3D getNormal() {
				return point.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
}