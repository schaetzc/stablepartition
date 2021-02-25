package stablepartition;

/**
 * Stores fixed-sized unsigned integers by packing them densely into words.
 * For example, 9 7-bit integers only occupy 2 32-bit words (here we use Java's {@code int} as words).
 * Integers with 1 to 32 bits are supported.
 */
public class PackedIntArray extends ArrayAdapter<Integer> {

	private final int bitsPerInt;

	/**
	 * Integers are stored in little endian, that is, bits in this array are numbered as follows:
	 * <pre>
	 * array[0]   array[1]    array[2]    ...
	 * [31 ... 0] [63 ... 32] [95 ... 64] ...
	 * </pre>
	 * The LSB of packedArray[0] is the LSB of the first int (index 0).
	 * The MSB of packedArray[length()-1] is either the MSB of the last int (index length()-1) or unused.
	 */
	private final int[] packedArray;
	private final int maxInt ;
	private static final int WORDSIZE = 32;
	
	public PackedIntArray(int bitsPerInt, int numberOfInts) {
		super(numberOfInts);
		if (bitsPerInt < 1 || bitsPerInt > WORDSIZE) {
			throw new IllegalArgumentException("Unsupported cell size: " + bitsPerInt + " bits");
		}
		this.bitsPerInt = bitsPerInt;
		this.maxInt = ~(-1 << bitsPerInt);
		packedArray = new int[MathUtils.ceilDiv(bitsPerInt * numberOfInts, WORDSIZE)];
	}

	@Override
	protected Integer readIntern(int index) {
		BitAddr lsb = lsb(index);
		int lower = (packedArray[lsb.wordIndex] >>> lsb.bitOffsetInWord) & maxInt;
		int lowerSize = lowerSize(lsb.bitOffsetInWord);
		if (lowerSize == bitsPerInt) {
			return lower;
		}
		int upper = packedArray[lsb.wordIndex + 1] & (maxInt >>> lowerSize);
		return (upper << lowerSize) | lower;
	}
	
	@Override
	protected void writeIntern(int index, Integer value) {
		value &= maxInt;
		BitAddr lsb = lsb(index);
		packedArray[lsb.wordIndex] &= ~(maxInt << lsb.bitOffsetInWord);
		packedArray[lsb.wordIndex] |= value << lsb.bitOffsetInWord;
		int lowerSize = lowerSize(lsb.bitOffsetInWord);
		if (lowerSize == bitsPerInt) {
			return;
		}
		packedArray[lsb.wordIndex + 1] &= ~(maxInt >>> lowerSize);
		packedArray[lsb.wordIndex + 1] |= value >>> lowerSize;
	}

	private int lowerSize(int lsbOffset) {
		return Math.min(bitsPerInt, WORDSIZE - lsbOffset);
	}
	
	/**
	 * Computes the address of the least significant bit (LSB) of a packed int.
	 * @param packedIntIndex 0-based index of a packed int in this array
	 * @return Address of the LSB of the given packed int
	 */
	private BitAddr lsb(int packedIntIndex) {
		return new BitAddr(packedIntIndex * bitsPerInt);
	}

	/**
	 * Addresses a bit B by ...
	 * <li>the index of the word W containing B
	 * <li>and B's offset inside W.
	 */
	private class BitAddr {
		/** Word index in {@link PackedIntArray#packedArray}. */
		public final int wordIndex;
		/** Bit offset in {@link PackedIntArray#packedArray}[{@link #wordIndex}]. */
		public final int bitOffsetInWord;

		private BitAddr(int bitIndex) {
			this.wordIndex = bitIndex / WORDSIZE;
			this.bitOffsetInWord = bitIndex % WORDSIZE;
		}
	}

}
