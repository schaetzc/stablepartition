package stablepartition;

import java.util.Arrays;

public class Array<E> extends ArrayAdapter<E> {

	private E[] array;
	
	public Array(E[] array) {
		super(array.length);
		this.array = array;
	}
	
	@Override
	protected E readIntern(int index) {
		return array[index];
	}

	@Override
	protected void writeIntern(int index, E value) {
		array[index] = value;
	}

	@Override
	public String toString() {
		return Arrays.toString(array);
	}
}
