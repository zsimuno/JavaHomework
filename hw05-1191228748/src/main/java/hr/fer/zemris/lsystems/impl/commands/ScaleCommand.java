/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Scales the turtle movement with a given scale.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Factor with which to scale the movement
	 */
	private double factor;

	/**
	 * Constructs a {@link ScaleCommand} object with the given factor of the scaling
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		// TODO ScaleCommand execute

	}

}
