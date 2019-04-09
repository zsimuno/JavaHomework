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
	 * Constructs a {@link DrawCommand} object with the given step
	 */
	public DrawCommand(double step) {
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

		// Draw the line
		painter.drawLine(currentPosition.getX(), currentPosition.getY(),
						newX, newY, currentState.getCurrentColor(), 1f);

		currentState.setCurrentPosition(new Vector2D(newX, newY));
	}

}
