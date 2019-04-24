/**
 * 
 */
package hr.fer.zemris.java.hw07.observer2;

/**
 * Example class that is used to test our observers.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ObserverExample {

	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		test1();
		System.out.println("--------------");
		test2();
	}
	
	
	/**
	 * First test
	 */
	private static void test1() {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		
//		Output:
		
//		Provided new value: 5, square is 25
//		Provided new value: 2, square is 4
//		Provided new value: 25, square is 625
//		Number of value changes since tracking: 1
//		Double value: 26
//		Number of value changes since tracking: 2
//		Double value: 44
//		Number of value changes since tracking: 3
//		Double value: 30
	}
	
	/**
	 * Second test
	 */
	private static void test2() {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		
		// Throws exception
	}
	
	

}
