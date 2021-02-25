package stablepartition;

public class TypedBlockArray<E> implements IArray<E> {

	private IArray<E> array;
	private int blockSize;
	private int length;
	
	public TypedBlockArray(IArray<E> array, int blockSize) {
		length = array.length() / blockSize;
		if (length * blockSize < array.length()) {
			throw new IllegalArgumentException("Array length not divisible by block size.");
		}
		this.array = array;
	}
	
	@Override
	public int length() {
		return length;
	}

	@Override
	public E read(int index) {
		// return Subarray.byStartLength(array, index * blockSize, blockSize);
		return array.read(index * blockSize);
	}

	@Override
	public void write(int index, E value) {
		throw new UnsupportedOperationException("Use swap method instead");
	}
	
	@Override
	public void swap(int index1, int index2) {
		int block1 = index1 * blockSize;
		int block2 = index2 * blockSize;
		for (int i = 0; i < blockSize; ++i) {
			array.swap(block1 + i, block2 + i);
		}
	}

	@Override
	public int readCounter() {
		return array.readCounter();
	}

	@Override
	public int writeCounter() {
		return array.writeCounter();
	}
}
