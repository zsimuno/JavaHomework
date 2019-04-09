/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;
import hr.fer.zemris.math.Vector2D;

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
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getCurrentPosition();
		Vector2D lookDirection = currentState.getLookDirection();
		double angleX = lookDirection.getX(), angleY = lookDirection.getY();
		double moveLength = currentState.getMoveLenght() * step;
		
		double newX = currentPosition.getX() + moveLength * angleX;

		double newY = currentPosition.getY() + moveLength * angleY;
		
		currentState.setCurrentPosition(new Vector2D(newX, newY));

	}


}
