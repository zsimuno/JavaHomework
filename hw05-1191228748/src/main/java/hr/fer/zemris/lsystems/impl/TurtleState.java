/**
 * 
 */
package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents a state that the "turtle" can be in.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TurtleState {

	/**
	 * Current position of the turtle
	 */
	private Vector2D currentPosition;

	/**
	 * Direction that the turtle is looking in
	 */
	private Vector2D lookDirection;

	/**
	 * Current color that the turtle is drawing in.
	 */
	private Color currentColor;

	/**
	 * Current length of the movement.
	 */
	private double moveLenght;

	/**
	 * Constructs a new {@link TurtleState} from given arguments.
	 * 
	 * @param currentPosition Current position of the turtle
	 * @param lookDirection   Direction that the turtle is looking in
	 * @param currentColor    Current color that the turtle is drawing in
	 * @param moveLenght      Current length of the movement
	 */
	public TurtleState(Vector2D currentPosition, Vector2D lookDirection, Color currentColor, double moveLenght) {
		this.currentPosition = currentPosition;
		this.lookDirection = lookDirection;
		this.currentColor = currentColor;
		this.moveLenght = moveLenght;
	}

	/**
	 * Returns the copy of the current turtle state
	 * 
	 * @return the copy of the current turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), lookDirection.copy(), currentColor, moveLenght);
	}
	
	
	/**
	 * 
	 * @return the current position of the turtle
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @return the direction that the turtle is looking in
	 */
	public Vector2D getLookDirection() {
		return lookDirection;
	}

	/**
	 * @return the current color that the turtle is drawing in
	 */
	public Color getCurrentColor() {
		return currentColor;
	}

	/**
	 * @return the current length of the movement
	 */
	public double getMoveLenght() {
		return moveLenght;
	}
	
	


	/**
	 * Sets the current position of the turtle.
	 * 
	 * @param currentPosition the current position of the turtle to set
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Sets the direction that the turtle is looking.
	 * 
	 * @param lookDirection the direction that the turtle is looking in to set
	 */
	public void setLookDirection(Vector2D lookDirection) {
		this.lookDirection = lookDirection;
	}

	/**
	 * Sets the current color that the turtle is drawing in.
	 * 
	 * @param currentColor the current color that the turtle is drawing in to set
	 */
	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	/**
	 * Sets the current length of the movement.
	 * 
	 * @param moveLenght the current length of the movement to set
	 */
	public void setMoveLenght(double moveLenght) {
		this.moveLenght = moveLenght;
	}




	/**
	 * This class is used for executing procedures for displaying the fractals. They
	 * offer a stack to which we can push or pull from turtle states.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	public static class Context {
		
		
		/**
		 * Stack that contains turtle states as {@link TurtleState}.
		 */
		private ObjectStack<TurtleState> states = new ObjectStack<>();

		/**
		 * Returns the state form the top of the stack without removing it.
		 * 
		 * @return the state form the top of the stack
		 */
		public TurtleState getCurrentState() {
			return states.peek();
		}

		/**
		 * Pushes {@code state} at the top of the stack
		 * 
		 * @param state state that will be pushed in the stack
		 */
		public void pushState(TurtleState state) {
			states.push(state);
		}

		/**
		 * Removes the state from the top of the stack
		 */
		public void popState() {
			states.pop();
		}
	}

}
