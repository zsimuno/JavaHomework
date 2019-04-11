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
	 * Builds an LSystem
	 * 
	 * @return newly built LSystem
	 */
	@Override
	public LSystem build() {
		LSystem localLSystem = new LSystem() {

			/**
			 * Generates string from axiom and productions based on level.
			 * 
			 * @param level level which we want to generate
			 * @return string generated from axiom and productions based on level.
			 */
			@Override
			public String generate(int level) {
				if (level == 0)
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
			 * 
			 * @param level   given level
			 * @param painter given painter
			 */
			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();

				Vector2D angleVector = new Vector2D(1, 0);
				angleVector.rotate(angle * (Math.PI / 180.0));
				double move = unitLength * Math.pow(unitLengthDegreeScaler, level);

				context.pushState(new TurtleState(origin, angleVector, Color.black, move));

				char[] finalGenerate = this.generate(level).toCharArray();

				for (char c : finalGenerate) {
					Command command = registeredCommands.get(c);

					if (command == null)
						continue;

					command.execute(context, painter);
				}
			}
		};

		return localLSystem;
	}

	/**
	 * Configures an LSystem from text..
	 * 
	 * @param lines lines of text with which we build the builder
	 * @return LSystem builder made from given lines of text
	 * @throws IllegalArgumentException if the text is invalid
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {

		for (String line : lines) {

			if (line.isEmpty()) {
				continue;
			}

			String[] currentLine = line.split("\\s+\\/{0,1}\\s*|\\s*\\/{0,1}\\s+|\\/");

			// Check which directive is in the current line
			switch (currentLine[0]) {

			case "origin":
				origin = new Vector2D(parseNumber(currentLine, 1), parseNumber(currentLine, 2));
				break;

			case "angle":
				angle = parseNumber(currentLine, 1);
				break;

			case "unitLength":
				unitLength = parseNumber(currentLine, 1);
				break;

			case "unitLengthDegreeScaler":
				unitLengthDegreeScaler = parseNumber(currentLine, 1) / parseNumber(currentLine, 2);
				break;

			case "command":
				if (currentLine.length == 1)
					throw new IllegalArgumentException("Invalid command input " + line + "!");
				
				// Symbol must be one letter
				if(currentLine[1].length() != 1) 
					throw new IllegalArgumentException("Invalid command input " + line + "!");

				// Checks if it's a one word command or two word command
				Command command = parseCommand(
						(currentLine.length == 3) ? currentLine[2] : currentLine[2] + " " + currentLine[3]);
				
				registeredCommands.put(currentLine[1].charAt(0), command);
				break;

			case "axiom":
				if (currentLine.length < 2)
					throw new IllegalArgumentException("Invalid command input " + line + "!");

				axiom = currentLine[1];
				break;

			case "production":
				if (currentLine.length == 1)
					throw new IllegalArgumentException("Invalid command input " + line + "!");
				
				// Symbol must be one letter
				if(currentLine[1].length() != 1) 
					throw new IllegalArgumentException("Invalid command input " + line + "!");
				
				registeredProductions.put(currentLine[1].charAt(0), currentLine[2]);
				break;

			default:
				throw new IllegalArgumentException("Invalid command " + line + "!");
			}
		}
		return this;
	}

	/**
	 * Registers one command
	 * 
	 * @param symbol the symbol of command
	 * @param action action of the command
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		registeredCommands.put(symbol, parseCommand(action));
		return this;
	}

	/**
	 * Gets the command in string form and returns it as a {@link Command} object
	 * 
	 * @param string command as string
	 * @return given command as {@link Command} object
	 * @throws IllegalArgumentException if the command is invalid
	 */
	private Command parseCommand(String string) {
		String[] command = string.split("\\s+");

		switch (command[0]) {
		case "draw":
			checkLength(command, 2);
			return new DrawCommand(parseNumber(command, 1));

		case "skip":
			checkLength(command, 2);
			return new SkipCommand(parseNumber(command, 1));

		case "scale":
			checkLength(command, 2);
			return new ScaleCommand(parseNumber(command, 1));

		case "rotate":
			checkLength(command, 2);
			return new RotateCommand(parseNumber(command, 1));

		case "push":
			checkLength(command, 1);
			return new PushCommand();

		case "pop":
			checkLength(command, 1);
			return new PopCommand();

		case "color":
			checkLength(command, 2);
			try {
				return new ColorCommand(Color.decode("#" + command[1]));
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Invalid command input " + string + "!");
			}

		default:
			throw new IllegalArgumentException("Invalid command!");
		}

	}

	/**
	 * Checks if the array is expected size and throws exception if it's not
	 * 
	 * @param array          array we check
	 * @param expectedLength expected length of the array
	 * @throws IllegalArgumentException if there are wrong number of elements
	 */
	private void checkLength(String[] array, int expectedLength) {
		if (array.length != expectedLength)
			throw new IllegalArgumentException("Invalid command!");
	}

	/**
	 * Parses a double from a string array at given index.
	 * 
	 * @param string string array
	 * @param index  index of element we wan to parse to double
	 * @return given number as {@link Double} object
	 */
	private Double parseNumber(String[] string, int index) {
		try {
			return Double.parseDouble(string[index]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid command input " + String.join(" ", string) + "!");
		}
	}

	/**
	 * Registers one production
	 * 
	 * @param symbol     the symbol of production
	 * @param production the production
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		registeredProductions.put(Character.valueOf(symbol), production);
		return this;
	}

	/**
	 * Sets the angle
	 * 
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	/**
	 * Sets the axiom
	 * 
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets the origin
	 * 
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets the unit length
	 * 
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the unit length degree scaler
	 * 
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
