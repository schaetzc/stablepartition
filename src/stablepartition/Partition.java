package stablepartition;

import java.util.function.Consumer;
import java.util.function.Predicate;

// Is only O(1) space because we assume word-size >= log2(n). 
// In java we have n <= 2^32 (array indices are int, ints are 32-bit).
// Algorithm D needs
// 1. for each application of Algorithm C on a block of size lg n:
// lg² n + lg n / lg² n bits and as many counters, each requiring at most ceil(log2(lg n + 1)) bits
// = 2 * (lg² n + lg n / lg² n) * ceil(log2(lg n + 1)) bits in total
// To compute an upper bound for this in Java, we use n <= 2^32
// lg n <= lg 2^32 = 32
// lg² n <= lg² 2^32 = lg 32 = 8
// ceil(log2(lg n + 1)) <= ceil(log2(32 + 1)) = 6
// Therefore we need
// 2 * (8 + 32 / 8) * 6 bits = 2 * (8 + 4) * 6 bits = 2 * 12 * 6 bits = 144 bits
// to store the counters and bit flags ==> In Java 5 ints are sufficient!
//
// 2. for sorting the typed blocks using Algorithm B:
// O(1) space and O(lg n * log2 lg n) time which is in O(n) time
//
// Optional: Change predicate to function E → {0,1,...} such that partition can have more than 2 parts
//           This may also simplify some if-else conditions and terms like "zeroes"/"ones".
//           BUT the algorithm has to support such an extension
public class Partition<E> {

	private int predicateCounter;
	private Predicate<E> predicate;

	public Partition(Predicate<E> predicate) {
		this.predicate = element -> { predicateCounter++; return predicate.test(element); };
	}
	
	private boolean isZero(E element) {
		return !isOne(element);
	}

	private boolean isOne(E element) {
		return predicate.test(element);
	}
	
	public void stablepartition(IArray<E> array) {
		int offset = 0;
		int remainingLength;
		while ((remainingLength = array.length() - offset) > 0) {
			int blockLength = MathUtils.nextPowerOf2LessOrEqual(remainingLength);
			IArray<E> block = Subarray.byStartLength(array, offset, blockLength);
			algorithmD(block);
			offset += blockLength;
		}
		// TODO combine partitioned blocks
	}
	
	public void algorithmA(IArray<E> array) {
		// OPTIONAL: This can be generalized to any constant (!) number of parts in a partition
		
		int zeroesTotal = array.length() - countOnes(array);
		// correctIndex[i] is the index of array[i] in array after sorting
		int bitsPerIndex = Math.max(1, MathUtils.log2Ceiled(array.length()));
		IArray<Integer> correctIndex = new PackedIntArray(bitsPerIndex, array.length());
		int zeroesToTheLeft = 0;
		int onesToTheLeft = 0;
		for (int i = 0; i < array.length(); ++i) {
			if (predicate.test(array.read(i))) {
				correctIndex.write(i, zeroesTotal + onesToTheLeft);
				onesToTheLeft++;
			} else {
				correctIndex.write(i, zeroesToTheLeft);
				zeroesToTheLeft++;
			}
		}
		permute(array, correctIndex);
	}

	private void algorithmB(IArray<E> array) {
		// TODO implement
	}

	private void combineAlgorithmsByBlocking(IArray<E> array,
			Consumer<IArray<E>> sortElements, Consumer<IArray<E>> sortTypedBlocks) {
		if (MathUtils.isPowerOf2(array.length())) {
			throw new IllegalArgumentException("Array length is not a power of 2: " + array.length());
		}
		int blockSize = MathUtils.lg(array.length());
		for (int offset = 0; offset < array.length(); offset += blockSize) {
			IArray<E> block = Subarray.byStartLength(array, offset, blockSize);
			sortElements.accept(block);
		}
		sortedToTypedBlocks(array, blockSize);
		IArray<E> blockView = new TypedBlockArray<>(array, blockSize);
		sortTypedBlocks.accept(blockView);
		fixLastBlock(array, blockSize);
	}
	
	private void sortedToTypedBlocks(IArray<E> array, int blockSize) {
		// TODO implement
	}
	
	private void fixLastBlock(IArray<E> array, int blockSize) {
		// TODO implement
	}
	
	private void algorithmC(IArray<E> array) {
		combineAlgorithmsByBlocking(array, this::algorithmA, this::algorithmA);
	}
	
	private void algorithmD(IArray<E> array) {
		combineAlgorithmsByBlocking(array, this::algorithmC, this::algorithmB);
	}
	
	public void permute(IArray<E> array, IArray<Integer> newIndexOf) {
		IArray<Boolean> atFinalPos = new PackedBoolArray(array.length());
		for (int k = 0; k < array.length(); ++k) {
			if (atFinalPos.read(k)) {
				continue;
			}
			int i = k;
			while ((i = newIndexOf.read(i)) != k) {
				array.swap(newIndexOf.read(i), newIndexOf.read(k));
				atFinalPos.write(i, true);
			}
		}
	}
	
	private int countOnes(IArray<E> array) {
		int ones = 0;
		for (int i = 0; i < array.length(); ++i) {
			if (predicate.test(array.read(i))) {
				ones++;
			}
		}
		return ones;
	} 
	
}














