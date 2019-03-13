/**
 * 
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that creates a sorted binary tree based on user input. Tree has no
 * repeating numbers
 * 
 * @author Zvonimir Šimunović
 *
 */
public class UniqueNumbers {

	/**
	 * Class that is used to represent the tree and its nodes
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	static class TreeNode {
		TreeNode left, right;
		int value;

	}

	/**
	 * Adds a new node to the tree
	 * 
	 * @param node     root of the tree that we are adding the new node to
	 * @param newValue value that we are looking to add to the tree
	 * @return newly generated tree
	 */
	public static TreeNode addNode(TreeNode node, int newValue) {
		if (node == null) {
			node = new TreeNode();
			node.value = newValue;
		} else if (newValue < node.value) {
			node.left = addNode(node.left, newValue);
		} else if (newValue > node.value) {
			node.right = addNode(node.right, newValue);
		}

		return node;
	}

	/**
	 * Checks if a tree contains a value
	 * 
	 * @param node  tree that we are checking if it contains the value
	 * @param value value that we are looking if the tree contains
	 * @return <code>true</code> if tree contains the value <code>false</code> if it
	 *         doesn't
	 */
	public static boolean containsValue(TreeNode node, int value) {
		if (node == null) {
			return false;
		}

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
	 * Returns the size of the tree, that is how many nodes does the tree have
	 * 
	 * @param node tree that we are checking
	 * @return size of the tree
	 */
	public static int treeSize(TreeNode node) {
		return (node == null) ? 0 : 1 + treeSize(node.left) + treeSize(node.right);
	}

	/**
	 * Prints the node values in ascending order
	 * 
	 * @param node tree that we print the nodes of
	 */
	public static void printSortedAscending(TreeNode node) {
		if (treeSize(node) == 02) {
			return;
		}
		printSortedAscending(node.left);

		System.out.printf(" %d", node.value);

		printSortedAscending(node.right);
	}

	/**
	 * Prints the node values in descending order
	 * 
	 * @param node tree that we print the nodes of
	 */
	public static void printSortedDescending(TreeNode node) {
		if (treeSize(node) == 0) {
			return;
		}
		printSortedDescending(node.right);

		System.out.printf(" %d", node.value);

		printSortedDescending(node.left);
	}

	/**
	 * Method that reads user input a makes a binary tree out of those numbers
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		TreeNode stablo = null;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("Unesite broj > ");
			if (sc.hasNextInt()) {
				int inputNumber = sc.nextInt();
				if (containsValue(stablo, inputNumber)) {
					System.out.println("Broj već postoji. Preskačem.");
					continue;
				}
				stablo = addNode(stablo, inputNumber);
				System.out.println("Dodano.");

			} else {
				String input = sc.next();
				if (input.equals("kraj")) {
					if (treeSize(stablo) != 0) {
						System.out.printf("%nIspis od najmanjeg:");
						printSortedAscending(stablo);
						System.out.printf("%nIspis od najvećeg:");
						printSortedDescending(stablo);
					}
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", input);
			}
		}

		sc.close();

	}

}
