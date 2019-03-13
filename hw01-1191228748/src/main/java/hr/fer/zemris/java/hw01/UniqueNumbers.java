/**
 * 
 */
package hr.fer.zemris.java.hw01;

/**
 * @author Zvonimir Šimunović
 *
 */
public class UniqueNumbers {

	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	static class TreeNode {
		TreeNode left, right;
		int value;

	}

	/**
	 * @param node
	 * @param newValue
	 * @return
	 */
	public static TreeNode addNode(TreeNode node, int newValue) {
		if (node == null) {
			node = new TreeNode();
			node.value = newValue;
			return node;
		}

		if (newValue == node.value) {
			System.out.println("Broj već postoji. Preskačem.");
			return node;
		}

		checkNode((newValue < node.value) ? node.left : node.right, newValue);

		return node;
	}

	/**
	 * @param node
	 * @param newValue
	 * @return
	 */
	public static TreeNode checkNode(TreeNode node, int newValue) {
		if (node == null) {
			node = new TreeNode();
			node.value = newValue;
			System.out.println("Dodano.");
		} else {
			addNode(node, newValue);
		}
		return node;
	}

	/**
	 * @param node
	 * @param value
	 * @return
	 */
	public static boolean containsValue(TreeNode node, int value) {
		if (value == node.value) {
			return true;
		}

		if (value < node.value && node.left != null) {
			return containsValue(node.left, value);
		} else if (value > node.value && node.right != null) {
			return containsValue(node.right, value);
		}

		return false;
	}

	public static int treeSize(TreeNode node) {
		return (node == null) ? 0 : 1 + treeSize(node.left) + treeSize(node.right);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);

		if (containsValue(glava, 76)) {
			System.out.println("da");
		}

		System.out.println(treeSize(glava));

	}

}
