/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Moves the turtle a given step and draws a line.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DrawCommand implements Command {

	/**
	 * Represents the length of the step of the draw.
	 */
	private double step;
	
	/**
	 *  Constructs a {@link DrawCommand} object with the given step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		// TODO Draw execute

	}

}
