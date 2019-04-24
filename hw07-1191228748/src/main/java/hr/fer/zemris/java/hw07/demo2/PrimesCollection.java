/**
 * 
 */
package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Zvonimir Šimunović
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * How many of primes can be listed in this class
	 */
	private int numberOfPrimes;

	public PrimesCollection(int n) {
		numberOfPrimes = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<Integer> {

		/**
		 * Number (index) of the next prime.
		 */
		private int nextPrime = 0;

		@Override
		public boolean hasNext() {
			return nextPrime < numberOfPrimes;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return prime(nextPrime++);
		}

		/**
		 * Calculates and returns the n-th prime number (index starting from 0).
		 * 
		 * @param n index of the prime number.
		 * @return n-th prime number.
		 */
		private int prime(int n) {
			for (int i = 2, counter = 0;; i++) {
				if (isPrime(i)) {
					
					if (counter == n)
						return i;
					counter++;
				}
			}
		}

		/**
		 * Checks if a number is prime.
		 * 
		 * @param x number to be checked.
		 * @return {@code true} if the given number is prime, {@code false} otherwise.
		 */
		private boolean isPrime(int x) {
			for (int i = 2; i * i <= x; i++) {
				if (x % i == 0)
					return false;
			}
			return true;
		}
	}

}
