package stablepartition;

/**
 * Skeleton for implementing {@link IArray}.
 *
 * @param <E> Type of the elements in this array
 */
public abstract class ArrayAdapter<E> implements IArray<E> {

	private int readCounter;
	private int writeCounter;
	protected final int length;
	
	public ArrayAdapter(int length) {
		this.length = length;
	}

	@Override
	public int length() {
		return length;
	}
	
	@Override
	public E read(int index) {
		checkBounds(index);
		readCounter++;
		return readIntern(index);
	}

	protected abstract E readIntern(int index);

	@Override
	public void write(int index, E value) {
		checkBounds(index);
		writeCounter++;
		writeIntern(index, value);
	}

	protected abstract void writeIntern(int index, E value);

	private void checkBounds(int index) {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException(index);
		}
	}
	
	@Override
	public int readCounter() {
		return readCounter;
	}

	@Override
	public int writeCounter() {
		return writeCounter;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		String delimiter = "";
		for (int i = 0; i < length; ++i) {
			sb.append(delimiter).append(readIntern(i));
			delimiter = ", ";
		}
		return sb.toString();
	}
}
