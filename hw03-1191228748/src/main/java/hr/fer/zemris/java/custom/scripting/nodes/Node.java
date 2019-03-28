/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Node {
	
	/**
	 * Collection of children
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Adds given {@code child} to an internally managed collection of children.
	 * 
	 * @param child Node that will be added to the children
	 */
	public void addChildNode(Node child) {
		
		if(children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	
	/**
	 * Returns a number of (direct) children.
	 * 
	 * @return number of (direct) children
	 */
	public int numberOfChildren() {
		if(children == null) {
			return 0;
		}
		
		return children.size();
	}
	
	/**
	 * Returns selected child.
	 * 
	 * @param index position of the element to return
	 * @return selected child at position {@code index}
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public Node getChild(int index) {
		if(children == null) {
			throw new IndexOutOfBoundsException();
		}
		
		return (Node)children.get(index);
	}

	@Override
	public int hashCode() {
		return Objects.hash(children);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if(this.numberOfChildren() != other.numberOfChildren())
			return false;
		for (int i = 0; i < this.numberOfChildren(); i++) {
			if(!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		return true;
	}
	
	

}
