package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.*;

/**
 * @author Zvonimir Šimunović
 *
 */
class UniqueNumbersTest {

	@Test
	void treeWithTwoLeftNodes() {
		TreeNode root = null;
		root = addNode(root, 42);
		root = addNode(root, 24);
		root = addNode(root, 4);

		assertEquals(4, root.left.left.value);
	}

	@Test
	void treeWithOneRightNode() {
		TreeNode root = null;
		root = addNode(root, 15);
		root = addNode(root, 24);

		assertEquals(24, root.right.value);
	}

	@Test
	void treeWithOneLeftRightNode() {
		TreeNode root = null;
		root = addNode(root, 15);
		root = addNode(root, 4);
		root = addNode(root, 10);

		assertEquals(10, root.left.right.value);
	}

	@Test
	void treeWithRootOnly() {
		TreeNode root = null;
		root = addNode(root, 15);

		assertNotNull(root);
		assertNull(root.left);
		assertNull(root.right);
	}

	@Test
	void treeSizeFour() {
		TreeNode root = null;
		root = addNode(root, 42);
		root = addNode(root, 76);
		root = addNode(root, 21);
		root = addNode(root, 76);
		root = addNode(root, 35);

		assertEquals(4, treeSize(root));
	}

	@Test
	void emptyTree() {
		TreeNode root = null;
		assertEquals(0, treeSize(root));
	}

	@Test
	void containWhenTrue() {
		TreeNode root = null;
		root = addNode(root, 42);
		root = addNode(root, 76);
		root = addNode(root, 21);

		assertTrue(containsValue(root, 21));
	}

	@Test
	void containWhenFalse() {
		TreeNode root = null;
		root = addNode(root, 42);

		assertFalse(containsValue(root, 2));
	}

	@Test
	void containOnEmptyTree() {
		TreeNode root = null;

		assertFalse(containsValue(root, 5));
	}

}
