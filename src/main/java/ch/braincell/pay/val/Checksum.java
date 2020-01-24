package ch.braincell.pay.val;

import java.math.BigInteger;

/**
 * Checksums of relevance in the financial industry of Switzerland.
 * 
 * @author boessu
 *
 */
public class Checksum {

	// Makes calculating Modulo 10 recursive a bit easier.
	private static final int[] numberList = { 0, 9, 4, 6, 8, 2, 7, 1, 3, 5 };

	/**
	 * Calculates the Modulo 10 recursive checksum out of an numeric string.
	 * 
	 * @param numeric the numeric string to get the checksum out of.
	 * @return the one-digit Modulo 10 recursive checksum, if it is numeric. It
	 *         returns -1 if it is not a numeric String.
	 */
	public static int getModulo10(String numeric) {
		numeric = numeric.replaceAll("\\s", "");
		if (numeric.matches("[0-9]+")) {
			int forward = 0;
			for (char _char : numeric.toCharArray()) {
				forward = numberList[(forward + Integer.valueOf(String.valueOf(_char))) % 10];
			}
			return (10 - forward) % 10;
		}
		return -1;
	}

	/**
	 * Checks the Modulo 10 recursive checksum at the end of an numeric string.
	 * 
	 * @param numeric the numeric string to check.
	 * @return true if it is valid, false otherwise.
	 */
	public static boolean checkModulo10(String numeric) {
		return 0 == getModulo10(numeric);
	}

	/**
	 * Calculates the Modulo 97 checksum out of an alphanumeric string.
	 * 
	 * @param alphanumeric the alphanumeric string to get the checksum.
	 * @return the Modulo 97 checksum, if it is alphanumeric. It returns -1 if it is
	 *         not an alphanumeric String.
	 */
	public static long getModulo97(String alphanumeric) {
		if (alphanumeric.matches("[A-Za-z0-9]+")) {
			alphanumeric = createNumber(alphanumeric);
			BigInteger longer = new BigInteger(alphanumeric + "00");
			return 98 - longer.mod(BigInteger.valueOf(97)).longValue();
		}
		return -1;
	}

	/**
	 * Checks if the Modulo 97 checksum at the end of an alphanumeric string is
	 * valid.
	 * 
	 * @param alphanumeric the alphanumeric string to check.
	 * @return true if it is valid, false otherwise.
	 */
	public static boolean checkModulo97(String alphanumeric) {
		if (alphanumeric.matches("[A-Za-z0-9]+")) {
			alphanumeric = createNumber(alphanumeric);
			BigInteger longer = new BigInteger(alphanumeric);
			return 1 == longer.mod(BigInteger.valueOf(97)).longValue();
		}
		return false;
	}

	/**
	 * Converts a string in a pure numeric string with {@link Character#MAX_RADIX}.
	 * Useful if you would like to calculate a checksum.
	 * 
	 * @param alphanumeric the alphanumeric string to convert.
	 * @return numeric string
	 */
	private static String createNumber(String alphanumeric) {
		String numeric = "";
		for (char c : alphanumeric.toCharArray()) {
			numeric += Long.parseLong(String.valueOf(c), Character.MAX_RADIX);
		}
		return numeric;
	}
}
