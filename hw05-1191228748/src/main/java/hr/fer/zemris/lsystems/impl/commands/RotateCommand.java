/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Class that rotates the turtle by a given angle.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class RotateCommand implements Command {
	
	/**
	 * Angle to be rotated by
	 */
	private double angle;

	/**
	 * Constructs a {@link RotateCommand} object with the given angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		// TODO Rotate execute

	}

}
