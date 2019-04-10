/**
 * 
 */
package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.*;

/**
 * All demos for database classes
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DatabaseDemos {

	/**
	 * Method that starts the demo
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		
		comparisonOperator();
		
		fieldValueGetters();
		
		conditionalExpression();
		
		queryParser();
		
		queryFilter();
	}

	/**
	 * Demo method for {@link ComparisonOperators}
	 */
	private static void comparisonOperator() {
		System.out.println("\nComparisonOperators demo:");
		IComparisonOperator oper = ComparisonOperators.LESS;
		System.out.println(oper.satisfied("Ana", "Jasna")); // true, since Ana < Jasna

		oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true
		
	}
	
	/**
	 * Demo method for {@link FieldValueGetters}
	 */
	private static void fieldValueGetters() {
		System.out.println("\nFieldValueGetters demo:");
		StudentRecord record = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
		
	}
	
	/**
	 * Demo method for {@link ConditionalExpression}
	 */
	private static void conditionalExpression() {
		System.out.println("\nConditionalExpression demo:");
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bos*",
				ComparisonOperators.LIKE
				);
				StudentRecord record = new StudentRecord("0000000057", "Širanović", "Hrvoje", "2");
				boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), // returns lastName from given record
				expr.getStringLiteral() // returns "Bos*"
				);
				
				System.out.println(recordSatisfies); // false
		
	}
	

	/**
	 * Demo method for {@link QueryParser}
	 */
	private static void queryParser() {
		System.out.println("\nQueryParser demo:");
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		// System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2
	}
	
	/**
	 * Demo method for {@link }
	 */
	private static void queryFilter() {
		System.out.println("\nQueryFilter demo:");
		String[] students = new String[] { 
				"0000000050	Sikirica	Alen	3",
				"0000000051	Skočir	Andro	4",
				"0000000052	Slijepčević	Josip	5",
				"0000000053	Srdarević	Dario	2",
				"0000000054	Šamija	Pavle	3",
				"0000000055	Šimunov	Ivan	4",
				"0000000056	Šimunović	Veljko	5",
				"0000000057	Širanović	Hrvoje	2" };

		StudentDatabase db = new StudentDatabase(students);
		
		QueryParser parser = new QueryParser("jmbag		 = \"0000000051\"");
		if (parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			System.out.println(r.toString());
			
		} 
		
		parser = new QueryParser("jmbag>\"0000000053\"");
		System.out.println(parser.isDirectQuery()); // false
		for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			System.out.println(r.toString());
		}
	}

}
