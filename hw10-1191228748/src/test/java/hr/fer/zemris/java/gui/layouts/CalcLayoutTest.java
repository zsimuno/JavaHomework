package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	void test() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void test2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void testFail1() {
		JPanel p = new JPanel(new CalcLayout(2));

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(-2, 0));
		});
	}

	@Test
	void testFail2() {
		JPanel p = new JPanel(new CalcLayout(2));

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(1, 8));
		});
	}

	@Test
	void testInvalidRow() {
		JPanel p = new JPanel(new CalcLayout(2));

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(0, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(-5, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(6, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(10, 2));
		});
	}

	@Test
	void testInvalidColumn() {
		JPanel p = new JPanel(new CalcLayout(2));

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(2, 0));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(2, -5));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(2, 8));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(2, 10));
		});
	}

	@Test
	void testAddFirstPositionInvalid() {
		JPanel p = new JPanel(new CalcLayout(2));

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(1, 2));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(1, 3));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(1, 4));
		});
		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel(""), new RCPosition(1, 5));
		});
	}

	@Test
	void testFailSamePosition() {
		JPanel p = new JPanel(new CalcLayout(2));
		p.add(new JLabel(""), new RCPosition(2, 2));

		assertThrows(CalcLayoutException.class, () -> {
			p.add(new JLabel("new label sam position"), new RCPosition(2, 2));
		});

	}

}
