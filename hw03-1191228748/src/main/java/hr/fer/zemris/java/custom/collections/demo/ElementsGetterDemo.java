/**
 * 
 */
package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.collections.Tester;

/**
 * @author Zvonimir Šimunović
 *
 */
public class ElementsGetterDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new LinkedListIndexedCollection();
		Demo1(col1);
		Demo1(col2);
		clear(col1, col2);

		Demo2(col1);
		Demo2(col2);
		clear(col1, col2);

		Demo3(col1);
		Demo3(col2);
		clear(col1, col2);

		Demo4(col1);
		Demo4(col2);
		clear(col1, col2);

		Collection col11 = new ArrayIndexedCollection();
		Collection col12 = new ArrayIndexedCollection();
		Demo5(col11, col12);

		Collection col21 = new LinkedListIndexedCollection();
		Collection col22 = new LinkedListIndexedCollection();
		Demo5(col21, col22);

		Demo6(col1);
		Demo6(col2);
		clear(col1, col2);

		Demo7(col1);
		Demo7(col2);
		clear(col1, col2);

		DemoTester();
		
		DemoAddAllSatisying();
		
		DemoListInterface();
	}

	private static void clear(Collection col1, Collection col2) {
		col1.clear();
		col2.clear();
	}

	/**
	 * Demo from page 2 of hw03
	 */
	public static void Demo1(Collection col) {
		// Prints
		// true Ivo true Ana true Jasna false 'Throws NoSuchElementException'
		System.out.println("\nDemo1");
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		try {
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (NoSuchElementException e) {
			System.out.println("Throws NoSuchElementException");
		}

	}

	/**
	 * Demo from page 2 of hw03
	 */
	public static void Demo2(Collection col) {
		// prints
		// true true true true true Ivo true true Ana true true true Jasna false false
		System.out.println("\nDemo2");
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
	}

	/**
	 * Demo from page 3 of hw03
	 */
	public static void Demo3(Collection col) {
		// prints
		// Ivo Ana Jasna "Throws NoSuchElementException"
		System.out.println("\nDemo3");
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (NoSuchElementException e) {
			System.out.println("Throws NoSuchElementException");
		}
	}

	/**
	 * Demo from page 3 of hw03
	 */
	public static void Demo4(Collection col) {
		// prints
		// Ivo, Ana, Ivo, Jasna, Ana
		System.out.println("\nDemo4");
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
	}

	/**
	 * Demo from page 4 of hw03
	 */
	public static void Demo5(Collection col1, Collection col2) {
		// Ivo, Ana, Ivo, Jasmina, Štefanija.
		System.out.println("\nDemo5");
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
	}

	/**
	 * Demo from page 5 of hw03
	 */
	public static void Demo6(Collection col) {
		// Ivo Ana 'Throws ConcurrentModificationException'
		System.out.println("\nDemo8");
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (ConcurrentModificationException e) {
			System.out.println("Throws ConcurrentModificationException");
		}

	}

	/**
	 * Demo from page 5 of hw03
	 */
	public static void Demo7(Collection col) {
		// Ana Jasna
		System.out.println("\nDemo7");
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}

	static class EvenIntegerTester implements Tester {
		public boolean test(Object obj) {
			if (!(obj instanceof Integer))
				return false;
			Integer i = (Integer) obj;
			return i % 2 == 0;
		}
	}

	/**
	 * Demost {@link Tester} class from page 6
	 */
	public static void DemoTester() {
		// false true false
		System.out.println("\nDemoTester");
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}

	/**
	 * addAllSatisying demo from page 6 of hw03
	 */
	public static void DemoAddAllSatisying() {
		// 12, 2, 4, 6
		System.out.println("\nDemo");
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
	}
	
	
	/**
	 * Demos {@link List} interface
	 */
	public static void DemoListInterface() {
		System.out.println("\nDemoListInterface");
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");
		Collection col3 = col1;
		Collection col4 = col2;
		col1.get(0);
		col2.get(0);
//		col3.get(0); // neće se prevesti! Razumijete li zašto?
//		col4.get(0); // neće se prevesti! Razumijete li zašto?
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
	}
	/**
	 * 
	 */
	public static void Demo() {
		//
		System.out.println("\nDemo");
	}
//	
//	/**
//	 * 
//	 */
//	public static void Demo() {
//		//
//		System.out.println("\nDemo");
//	}
}
