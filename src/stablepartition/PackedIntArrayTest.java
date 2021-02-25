package stablepartition;

import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PackedIntArrayTest {

	@Test
	void test() {
		Random rand = new Random(1);
		
		test(1, 0, rand);
		test(1, 1, rand);
		test(1, 32, rand);
		test(1, 33, rand);
		test(1, 64, rand);

		test(31, 0, rand);
		test(31, 1, rand);
		test(31, 2, rand);
		test(31, 31 * 32, rand);

		test(13, 500, rand);
	}
	
	private void test(int bitsPerCell, int numberOfCells, Random rand) {
		IArray<Integer> bitArray = new PackedIntArray(bitsPerCell, numberOfCells);
		int mask = ~(-1 << bitsPerCell);
		int[] expected = rand.ints(numberOfCells).map(i -> i & mask).toArray();
		for (int i = 0; i < numberOfCells; ++i) {
			bitArray.write(i, expected[i]);
		}
		for (int i = 0; i < numberOfCells; ++i) {
			Assert.assertEquals(expected[i], (int) bitArray.read(i));
		}
	}

}
