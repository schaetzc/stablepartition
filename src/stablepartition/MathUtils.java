package stablepartition;

public class MathUtils {

	public static void main(String[] args) {
		int lastLogStar = -1;
		for (int pow = 1; pow > 0; pow *= 2) {
			for (int x = pow - 1; x <= pow + 1; ++x) {
				if (logstarFloat(x) > lastLogStar) {
					lastLogStar = logstarFloat(x);
					System.out.format("log*(%d) = %d%n", x, lastLogStar);
					break;
				}
//				System.out.format("%32s %8d → %d %d (%d)   %n",
//						Integer.toBinaryString(x), x, logstarFloat(x), logstarRec(x), logstarInt(x));
			}
		}
		// conclusion (just guesses, no proofs):
		// log* x is in o(log x)
		// log* can be considered near-constant in real situations
		// (similar to the inverse ackerman function \alpha)
		//
		// log* x is not directly related to the binary representation of x.
		// => Bit-twiddling implementation is not possible, even with loops.
	}
	
	private static final double LN_OF_2 = Math.log(2);

	public static double log2(double x) {
		return Math.log(x) / LN_OF_2;
	}
	
	public static int log2Floored(int x) {
		return 31 - Integer.numberOfLeadingZeros(x);
	}
	
	public static int log2Ceiled(int x) {
		return 32 - Integer.numberOfLeadingZeros(x - 1);
	}
	
	/**
	 * Returns the smallest power of 2 greater than or equal to log2 x.
	 * This function is defined in the paper "Stable Minimum Space Partitioning In Linear Time".
	 * @param x A positive natural number
	 * @return Bits needed to encode the result of log2 x
	 */
	public static int lg(int x) {
		return 1 << (int) Math.ceil(log2(log2(x)));
	}
	
	public static int logstarFloat(int x) {
		int n = 0;
		double logAppliedNTimes = x;
		while (logAppliedNTimes > 0) {
			++n;
			logAppliedNTimes = log2(logAppliedNTimes);
		}
		return n;
	}
	
	// 1-off by definition
	public static int logstarRec(double x) {
		if (x > 2)
			return 1 + logstarRec(log2(x));
		return 1;
	}
	
	public static int nextPowerOf2LessOrEqual(int i) {
		return Integer.highestOneBit(i);
	}

	public static boolean isPowerOf2(int i) {
		return Integer.bitCount(i) == 1;
	}

	public static int ceilDiv(int dividend, double divisor) {
		return (int) Math.ceil(dividend / divisor);
	}
	
}
