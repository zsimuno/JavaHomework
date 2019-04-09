/**
 * 
 */
package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Interface for commands for the turtle.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface Command {
	
	/**
	 * Execute the command
	 * 
	 * @param ctx context in which we execute the command
	 * @param painter the painter
	 */
	void execute(Context ctx, Painter painter);

}
