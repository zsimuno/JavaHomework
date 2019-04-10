package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void testSingle() {
		QueryParser qp1 = new QueryParser("   jmbag 	=\"0123456789\" 	");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
		
	}
	
	@Test
	void testMultiple() {
		QueryParser qp2 = new QueryParser("jmbag  	=	\"0123456789\" and  lastName>	\"J\"");
		assertFalse(qp2.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
		assertEquals(2, qp2.getQuery().size());
	}
	
	@Test
	void testSingleNonJmbag() {
		assertThrows(QueryParserException.class, () -> new QueryParser("lastName  	=	\"0123456789\" "));
	}

}
