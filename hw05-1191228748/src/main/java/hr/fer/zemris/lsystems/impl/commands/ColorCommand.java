/**
 * 
 */
package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;

/**
 * Class that writes the given color in the current {@link TurtleState}
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ColorCommand implements Command {

	/**
	 * Color that will be written in the current turtle state
	 */
	private Color color;

	/**
	 * Constructs a {@link ColorCommand} object with the given color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setCurrentColor(color);

	}

}
