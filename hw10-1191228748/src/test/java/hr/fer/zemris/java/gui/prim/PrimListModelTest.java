package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	@Test
	void test1() {
		PrimListModel model = new PrimListModel();
		model.next();
		model.next();
		assertEquals(3, model.getSize());
	}
	
	@Test
	void test2() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
	}
	
	@Test
	void test3() {
		PrimListModel model = new PrimListModel();
		model.next();
		model.next();
		assertEquals(3, model.getElementAt(2));
	}
	
	@Test
	void test4() {
		PrimListModel model = new PrimListModel();
		model.next();
		model.next();
		model.next();
		assertEquals(5, model.getElementAt(3));
	}
	
	@Test
	void test5() {
		List<Integer> list = new ArrayList<>();
		PrimListModel model = new PrimListModel();
		ListDataListener l = new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {				
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				list.add(1);
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {				
			}
		};
		model.addListDataListener(l);
		model.next();
		
		assertFalse(list.isEmpty());
	}

}
