package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testFromHomework() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		assertEquals(Double.valueOf(200.51), multistack.peek("price").getValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(Integer.valueOf(1900), multistack.peek("year").getValue());
		multistack.peek("year").setValue(((Integer) multistack.peek("year").getValue()).intValue() + 50);
		assertEquals(Integer.valueOf(1950), multistack.peek("year").getValue());
		multistack.pop("year");
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		multistack.peek("year").add("5");
		assertEquals(Integer.valueOf(2005), multistack.peek("year").getValue());
		multistack.peek("year").add(5);
		assertEquals(Integer.valueOf(2010), multistack.peek("year").getValue());
		multistack.peek("year").add(5.0);
		assertEquals(Double.valueOf(2015), multistack.peek("year").getValue());
	}

	@Test
	void testIsEmptyTrue() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertTrue(multistack.isEmpty("NonExistent"));
	}

	@Test
	void testIsEmptyFalse() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		assertFalse(multistack.isEmpty("Hello"));
	}

	@Test
	void testPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		assertEquals("Hello world!", multistack.peek("Hello").getValue());
	}

	@Test
	void testPush2() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		assertEquals("Hello world!", multistack.peek("Hello").getValue());
		multistack.push("Hello", new ValueWrapper("Hello world again!"));
		assertEquals("Hello world again!", multistack.peek("Hello").getValue());
	}

	@Test
	void testPush3() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		multistack.push("Bye", new ValueWrapper("Bye world!"));
		assertEquals("Hello world!", multistack.peek("Hello").getValue());
		assertEquals("Bye world!", multistack.peek("Bye").getValue());
	}

	@Test
	void testPushThrows1() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(NullPointerException.class, () -> {
			multistack.push("Hello", null);
		});
	}

	@Test
	void testPushThrows2() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(NullPointerException.class, () -> {
			multistack.push(null, new ValueWrapper("Hello world!"));
		});
	}

	@Test
	void testPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("True", new ValueWrapper(Boolean.valueOf(true)));
		assertEquals(Boolean.valueOf(true), multistack.peek("True").getValue());
	}
	
	@Test
	void testPeek2() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello there!"));
		assertEquals("Hello there!", multistack.peek("Hello").getValue());
		
		multistack.push("Hello", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(Integer.valueOf(5), multistack.peek("Hello").getValue());
	}
	
	@Test
	void testPeek3() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Double", new ValueWrapper(Double.valueOf(5.5)));
		assertEquals(Double.valueOf(5.5), multistack.peek("Double").getValue());
		
		multistack.push("Integer", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(Integer.valueOf(5), multistack.peek("Integer").getValue());
	}
	
	@Test
	void testPeekThrows() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(EmptyStackException.class, () -> {
			multistack.peek("No element!");
		});
	}


	@Test
	void testPop1() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		multistack.push("Hello", new ValueWrapper("Hello world again!"));
		assertEquals("Hello world again!", multistack.pop("Hello").getValue());
		assertFalse(multistack.isEmpty("Hello"));
	}

	@Test
	void testPop2() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		multistack.push("Hello", new ValueWrapper("Hello world again!"));
		multistack.pop("Hello");
		multistack.pop("Hello");
		assertTrue(multistack.isEmpty("Hello"));
	}
	
	@Test
	void testPop3() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		multistack.push("Hello", new ValueWrapper("Hello world again!"));
		multistack.push("Hello", new ValueWrapper("Hello world third time!"));
		multistack.pop("Hello");
		assertEquals("Hello world again!", multistack.pop("Hello").getValue());
	}
	
	@Test
	void testPopThrows1() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(EmptyStackException.class, () -> {
			multistack.pop("No element!");
		});
	}

	@Test
	void testPopThrows2() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("Hello", new ValueWrapper("Hello world!"));
		multistack.pop("Hello");
		assertThrows(EmptyStackException.class, () -> {
			multistack.pop("Hello");
		});
	}

}
