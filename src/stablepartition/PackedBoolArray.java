package stablepartition;

public class PackedBoolArray implements IArray<Boolean> {

	private IArray<Integer> array;
	
	public PackedBoolArray(int length) {
		int bitsPerBool = 1;
		array = new PackedIntArray(bitsPerBool, length);
	}
	
	@Override
	public int length() {
		return array.length();
	}

	@Override
	public Boolean read(int index) {
		return array.read(index) == 1;
	}

	@Override
	public void write(int index, Boolean value) {
		array.write(index, value ? 1 : 0);
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
