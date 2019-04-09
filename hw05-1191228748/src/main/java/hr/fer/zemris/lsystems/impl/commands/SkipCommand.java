/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Moves the turtle a given step but doesn't draw anything.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SkipCommand implements Command {
	
	/**
	 * Represents the length of the step of the skip.
	 */
	private double step;
	
	/**
	 * Constructs a {@link SkipCommand} object with the given step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		// TODO Skip execute

	}


}
