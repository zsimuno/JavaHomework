/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * @author Zvonimir Šimunović
 *
 */
public class ObjectStack {

	/**
	 * TODO fill and is this kind if initialization ok?
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
	 * Pushes given value on the stack.
	 * 
	 * @param value value to be pushed on the stack
	 */
	public void push(Object value) {
		// TODO null value must not be allowed to be placed on stack.
		stack.add(value);
	}

	/**
	 * removes last value pushed on stack from stack and returns it.
	 * 
	 * @return object that was popped
	 */
	public Object pop() {
		// TODO If the stack is
//		 * empty when method pop is called, the method should throw EmptyStackException
//		 * . This exception is not part of JRE libraries; you should provide an
//		 * implementation of EmptyStackException class (put the class in the same
//		 * package as all of collections you implemented and let it inherit from
//		 * RuntimeException ).
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
		// TODO Handle an empty stack as described in pop method.
		return stack.get(stack.size() - 1);
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		stack.clear();

	}

}
