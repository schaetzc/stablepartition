package stablepartition;

public interface IArray<E> {
	
	/**
	 * @return Number of entries in this array
	 */
	int length();
	
	/**
	 * Reads an entry from this array.
	 * @param index 0-based index
	 * @return Value at the given index
	 */
	E read(int index);
	
	/**
	 * Overwrites an entry in this array.
	 * @param index 0-based index
	 * @param value Value to be written to the given index
	 */
	void write(int index, E value);

	/**
	 * Counts read access to this array.
	 * @return Number of calls of {@link #read(int)} over the lifetime of this object
	 */
	int readCounter();
	
	/**
	 * Counts write acces to this array.
	 * @return Number of calls to {@link #write(int, Object)} over the lifetime of this object
	 */
	int writeCounter();
	
	// Convenience methods -------------------------------------------------
	
	/**
	 * Swaps two entries in this array.
	 */
	default void swap(int index1, int index2) {
		E value1 = read(index1);
		E value2 = read(index2);
		write(index1, value2);
		write(index2, value1);
	}
	
	default void reverseBlock(int startIncl, int endExcl) {
		int flooredHalfBlockSize = (endExcl - startIncl) / 2;
		for (int offset = 0; offset < flooredHalfBlockSize; ++offset) {
			swap(startIncl + offset, endExcl - 1 - offset);
		}
	}
	
	default void swapAdjacentBlocks(int startBlock1Incl, int startBlock2Incl, int endBlock2Excl) {
		reverseBlock(startBlock1Incl, startBlock2Incl - 1);
		reverseBlock(startBlock2Incl, endBlock2Excl);
		// Note for extensions:
		// It is possible to swap non-adjacent blocks. To this end the last reversal has to emulate
		// a continuous section even though it acts on two non-adjacent sections of the array.
		reverseBlock(startBlock1Incl, endBlock2Excl);
	}
	
//	default Stream<E> stream() {
//		return IntStream.range(0, length()).mapToObj(this::read);
//	}
}
