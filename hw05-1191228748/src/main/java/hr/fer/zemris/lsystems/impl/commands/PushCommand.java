/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Class that is used to copy the state from the top of the stack and puts the
 * copy on the stack
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());

	}

}
