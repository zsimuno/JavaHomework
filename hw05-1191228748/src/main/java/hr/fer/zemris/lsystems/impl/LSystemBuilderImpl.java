/**
 * 
 */
package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.TurtleState.Context;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * An implementation of {@link LSystemBuilder} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Contains all registered productions.
	 */
	private Dictionary<Character, String> registeredProductions = new Dictionary<>();

	/**
	 * Contains all registered commands.
	 */
	private Dictionary<Character, Command> registeredCommands = new Dictionary<>();

	/**
	 * Represents how long in one movement of the turtle.
	 */
	private double unitLength = 0.1;
	/**
	 * Scales the unit length so that the dimensions of the given fractal would be
	 * more or less constant.
	 */
	private double unitLengthDegreeScaler = 1;
	/**
	 * Point from where the turtle will move.
	 */
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Angle of the turtle (0 degrees means it's looking to the right and 90 means
	 * it's looking up).
	 */
	private double angle = 0;
	/**
	 * Starting string that is used to build the system. Can be one or more
	 * characters.
	 */
	private String axiom = "";

	/**
	 * 
	 */
	@Override
	public LSystem build() {
		LSystem localLSystem = new LSystem() {


			/**
			 * 
			 */
			@Override
			public String generate(int level) {
				if(level == 0)
					return axiom;
				
				char[] previous = generate(level - 1).toCharArray();
				
				StringBuilder current = new StringBuilder();
				
				for (char c : previous) {
					String production = registeredProductions.get(c);
					
					current.append((production == null) ? c : production);
				}
				
				return current.toString();
			}

			/**
			 * Draws in the given level with a given painter
			 * @param arg0 given level
			 */
			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();

				// TODO ima li ovi state smisla?
				context.pushState(new TurtleState(origin, (new Vector2D(1, 0)).rotated(angle * (Math.PI / 180)),
						Color.black, unitLength * Math.pow(unitLengthDegreeScaler, level)));
				
				char[] finalGenerate = this.generate(level).toCharArray();

				for (char c : finalGenerate) {
					Command command = registeredCommands.get(c);
					
					if(command == null) 
						continue;
					
					command.execute(context, painter);
				}
			}
		};

		return localLSystem;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		// TODO configureFromText

		for (String line : lines) {
			String[] currentLine = line.split(" ");
		}
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		registeredCommands.put(symbol, parseCommand(action));
		return this;
	}
	
	/**
	 * Gets the command in string form and returns it as a {@link Command} object
	 * 
	 * @param string
	 * @throws
	 * @return
	 */
	private Command parseCommand(String string) {
		Command value = null;
		
		String[] command = string.split(" ");
		// TODO mozda provjeriti ako nije dobro napisano?
		switch(command[0]) {
		case "draw":
			return new DrawCommand(parseNumber(command));
		case "skip":
			return new SkipCommand(parseNumber(command));
		case "scale":
			return new ScaleCommand(parseNumber(command));
		case "rotate":
			return new RotateCommand(parseNumber(command));
		case "push":
			return new PushCommand();
		case "pop":
			return new PopCommand();
		case "color":
			try {
				return new ColorCommand(Color.decode("#" + command[1]));
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Invalid command input!");
			}	
		default:
			throw new IllegalArgumentException("Invalid command!");
		}

	}
	
	/**
	 * @param string
	 * @return
	 */
	private Double parseNumber(String[] string) {
		try {
			return Double.parseDouble(string[1]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid command input!");
		} 
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		registeredProductions.put(Character.valueOf(symbol), production);
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * 
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
