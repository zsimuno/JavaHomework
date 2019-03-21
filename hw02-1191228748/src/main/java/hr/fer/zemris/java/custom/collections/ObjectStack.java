/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class that represents a stack of objects. Usual stack methods are provided.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ObjectStack {
	/**
	 * {@link ArrayIndexedCollection} object that we use to represent the stack.
	 */
	private ArrayIndexedCollection stack = new ArrayIndexedCollection();

	/**
	 * Returns {@code true} if stack is empty and {@code false} if it's not.
	 * 
	 * @return {@code true} if stack is empty and {@code false} if it's not
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns number of elements in the stack.
	 * 
	 * @return number of elements in the stack
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Pushes given {@code value} on the stack.
	 * 
	 * @param value value to be pushed on the stack
	 * @throws NullPointerException if {@code value} is null
	 */
	public void push(Object value) {
		stack.add(Objects.requireNonNull(value));
	}

	/**
	 * removes last value pushed on stack from stack and returns it.
	 * 
	 * @return object that was popped
	 */
	public Object pop() {
		if(stack.isEmpty()) {
			throw new EmptyStackException();
		}
		Object top = stack.get(stack.size() - 1);
		stack.remove(stack.size() - 1);
		return top;

	}

	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * 
	 * @return last element placed on stack
	 */
	public Object peek() {
		if(stack.isEmpty()) {
			throw new EmptyStackException();
		}
		return stack.get(stack.size() - 1);
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		stack.clear();

	}

}
