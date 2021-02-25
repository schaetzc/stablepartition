package stablepartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PartitionTest {

	private long seed = 1;

	@Test
	void algoARandom() {
		Random rand = new Random(seed);
		for (int size = 0; size < 100; size = Math.max(size + 1, (int) (size * 1.2))) {
			for (int run = 0; run < 5; ++run) {
				test(new TestCase(size, rand));
			}
		}
	}
	
	@Test
	void algoAExtremes() {
		Random rand = new Random(seed);
		for (int size = 0; size < 100; size = Math.max(size + 1, (int) (size * 1.7345))) {
			for (int run = 0; run < 2; ++run) {
				test(new TestCase(size, run * size, rand));
			}
		}
	}	

	private void test(TestCase testCase) {
		Partition<Integer> partition = new Partition<>(testCase::predicate);
		testCase.applyAndAssert(partition::algorithmA);
	}
	
	private static class TestCase {
		private int zeroes;
		protected IArray<Integer> array;
		protected Integer[] expectedResult;
		protected Integer[] actual;
		
		public TestCase(int size, Random rand) {
			this(size, rand.nextInt(size + 1), rand);
		}
		
		public TestCase(int size, int zeroes, Random rand) {
			expectedResult = new Integer[size];
			actual = new Integer[size];
			// update IDs by incrementing
			int nextZeroId = -zeroes;
			int nextOneId = 1;
			for (int i = 0; i < zeroes; ++i) {
				actual[i] = 0;
				expectedResult[i] = nextZeroId + i;
			}
			for (int i = zeroes; i < size; ++i) {
				actual[i] = 1;
				expectedResult[i] = nextOneId + i - zeroes;
			}
			Collections.shuffle(Arrays.asList(actual), rand);
			for (int i = 0; i < size; ++i) {
				actual[i] = actual[i] == 0 ? nextZeroId++ : nextOneId++;
			}
			this.array = new Array<>(actual);
			this.zeroes = zeroes;
		}

		public void applyAndAssert(Consumer<IArray<Integer>> stableParitionAlgorithm) {
			String input = str(actual);
			stableParitionAlgorithm.accept(array);
			try {
				Assert.assertArrayEquals(expectedResult, actual);
			} catch (AssertionError e) {
				System.err.format("input:    %s%nexpected: %s%nactual:   %s%n%n",
						input, str(expectedResult), str(actual));
				throw e;
			}
		}
		
		public boolean predicate(int i) {
			return i > 0;
		}

		private static String str(Object[] array) {
			return Arrays.toString(array);
		}
	}
	
	
}
