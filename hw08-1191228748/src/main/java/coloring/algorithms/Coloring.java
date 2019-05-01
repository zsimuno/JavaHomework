package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * TODO Javadoc
 * 
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
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == refColor;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		LinkedList<Pixel> list = new LinkedList<>();

		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(Math.abs(i) == Math.abs(j))
					continue;
				list.add(new Pixel(t.x + i, t.y + j));
			}
		}
		
		return list;
	}
	

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}

}
