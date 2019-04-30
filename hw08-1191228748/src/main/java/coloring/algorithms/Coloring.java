package coloring.algorithms;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * @author Zvonimir Šimunović
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {
	/**
	 * 
	 */
	private Pixel reference;
	/**
	 * 
	 */
	private Picture picture;
	/**
	 * 
	 */
	private int fillColor;
	/**
	 * 
	 */
	private int refColor;

	/**
	 * @param reference
	 * @param picture
	 * @param fillColor
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		// TODO refColor construction (a sam postavlja refColor na boju koju u slici ima
		// slikovni element čija je lokacija predana kroz reference)
	}

	@Override
	public Pixel get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean test(Pixel t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(Pixel t) {
		// TODO Auto-generated method stub

	}

}
