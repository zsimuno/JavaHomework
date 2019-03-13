/**
 * 
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

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
			System.out.println("Dodano.");
			return node;
		}

		if (newValue == node.value) {
			System.out.println("Broj već postoji. Preskačem.");
			return node;
		}

		if (newValue < node.value) {
			node.left = checkNode(node.left, newValue);
		} else {
			node.right = checkNode(node.right, newValue);
		}

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
			node = addNode(node, newValue);
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

	/**
	 * @param node
	 * @return
	 */
	public static int treeSize(TreeNode node) {
		return (node == null) ? 0 : 1 + treeSize(node.left) + treeSize(node.right);
	}
	
	/**
	 * @param node
	 */
	public static void printSortedFromLowest(TreeNode node) {
		if(node.left != null) {
			printSortedFromLowest(node.left);
		}
		
		System.out.printf(" %d", node.value);
		
		if(node.right != null) {
			printSortedFromLowest(node.right);
		}
	}
	
	/**
	 * @param node
	 */
	public static void printSortedFromHighest(TreeNode node) {
		if(node.right != null) {
			printSortedFromHighest(node.right);
		}
		
		System.out.printf(" %d", node.value);
		
		if(node.left != null) {
			printSortedFromHighest(node.left);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeNode stablo = null;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("Unesite broj > ");
			if (sc.hasNextInt()) {
				int inputNumber = sc.nextInt();
				stablo = addNode(stablo, inputNumber);

			} else {
				String input = sc.next();
				if (input.equals("kraj")) {
					System.out.printf("%nIspis od najmanjeg: ");
					printSortedFromLowest(stablo);
					System.out.printf("%nIspis od najvećeg: ");
					printSortedFromHighest(stablo);
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", input);
			}
		}

		sc.close();

	}

}
