package stablepartition;

public class Subarray<E> implements IArray<E> {

	private IArray<E> array;
	private int offset;
	private int length;
	
	private Subarray(IArray<E> array, int offset, int length) {
		if (offset < 0 || length < 0) {
			throw new IllegalArgumentException(String.format(
					"Negative offset (%d) or length (%d)", offset, length));
		} else if (offset + length >= array.length()) {
			throw new IllegalArgumentException(String.format(
					"Region [%d, %d) not in array [0, %d)", offset, offset + length, array.length()));
		}
		while (array instanceof Subarray) {
			Subarray<E> sub = (Subarray<E>) array;
			array = sub.array;
			offset += sub.offset;
		}
		this.array = array;
		this.offset = offset;
		this.length = length;
	}

	public static <E> Subarray<E> byStartEnd(IArray<E> array, int startIncl, int endExcl) {
		return new Subarray<>(array, startIncl, endExcl - startIncl);
	}
	
	public static <E> Subarray<E> byStartLength(IArray<E> array, int startIncl, int length) {
		return new Subarray<>(array, startIncl, length);
	}
	
	@Override
	public int length() {
		return length;
	}

	private void checkBounds(int index) {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException(index);
		}
	}
	
	@Override
	public E read(int index) {
		checkBounds(index);
		return array.read(index + offset);
	}

	@Override
	public void write(int index, E value) {
		checkBounds(index);
		array.write(index + offset, value);
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
