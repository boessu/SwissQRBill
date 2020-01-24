package ch.braincell.pay.val;

/**
 * Validation and visualization of payment creditor references (part of payment
 * remittance).
 * 
 * @author Boessu
 *
 */
public class ReferenceVal {

	/**
	 * QRR Reference (same as ESR/ISR reference). Numeric string with 26 numbers and
	 * one Modulo 10 checksum at the end.
	 */
	public static final String REF_QRR = "[0-9]{2,27}";

	/**
	 * SCOR Creditor Reference. Alphanumeric Creditor Reference according to ISO
	 * 11649:2009.
	 */
	public static final String REF_SCOR = "RF[0-9]{2}[A-Za-z0-9]{1,21}";

	/**
	 * Checks the RF Creditor Reference syntax and calculates the Modulo 97 checksum
	 * if it is an RF Creditor Reference. The checksum is directly after the code
	 * "RF" in the position 3 & 4 of the reference.
	 * 
	 * @param SCOR RF Creditor Reference according to ISO 11649:2009
	 * @return checksum (2 digits) if it is a valid SCOR reference, -1 if it is not
	 *         a SCOR in syntax.
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Creditor_Reference">Wikipedia</a> for
	 *      details.
	 */
	public static long getCreditorReferenceChecksum(String SCOR) {
		SCOR = SCOR.replaceAll("\\s", "");
		if (SCOR.matches(REF_SCOR))
			return Checksum.getModulo97(SCOR.substring(4) + SCOR.substring(0, 2));
		return -1;
	}

	/**
	 * Tests if the Checksum of a RF Creditor Reference ISO 11649 is valid or not.
	 * 
	 * @param SCOR RF Creditor Reference according to ISO 11649:2009
	 * @return true if it is a valid RF Creditor Reference and the checksum is
	 *         correct, false otherwise.
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Creditor_Reference">Wikipedia</a> for
	 *      details.
	 */
	public static boolean checkCreditorReferenceChecksum(String SCOR) {
		SCOR = SCOR.replaceAll("\\s", "");
		if (SCOR.matches(REF_SCOR))
			return Checksum.checkModulo97(SCOR.substring(4) + SCOR.substring(0, 4));
		return false;
	}

	/**
	 * Converts a SCOR RF Creditor reference (ISO 11649:2009) to the official visual
	 * format with spaces (every 5th place).
	 * 
	 * @param reference SCOR RF Creditor Reference according to ISO 11649:2009
	 *                  without spaces.
	 * @return SCOR references with spaces, for human readable, visual purposes.
	 */
	public static String formatCreditorReference(String reference) {
		return Format.format(reference, ' ', 4, true);
	}

	/**
	 * Checks the QRR Reference syntax and calculates the Modulo 10 checksum if it is
	 * an QR Reference.
	 * 
	 * @param QRR Reference without checksum (!)
	 * @return checksum (1 digit) if it is a valid QRR reference, -1 if it is not
	 *         a QRR in syntax.
	 */
	public static long getQRReferenceChecksum(String reference) {
		reference = reference.replaceAll("\\s", "");
		if (reference.matches(REF_QRR)) {
			return Checksum.getModulo10(reference);
		}
		return -1;
	}

	/**
	 * Tests if the Checksum of a QR Reference is valid or not.
	 * 
	 * @param QR Reference.
	 * @return true if it is a valid QR Reference and the checksum is correct, false
	 *         otherwise.
	 */
	public static boolean checkQRReferenceChecksum(String reference) {
		reference = reference.replaceAll("\\s", "");
		if (reference.matches(REF_QRR)) {
			return Checksum.checkModulo10(reference);
		}
		return false;
	}

	/**
	 * Converts a QRR reference to the official visual format with spaces (every 6th
	 * place from reverse).
	 * 
	 * @param reference QRR Reference without spaces.
	 * @return QRR reference with references, for human readable, visual purposes.
	 */
	public static String formatQRReference(String reference) {
		return Format.format(reference, ' ', 5, false);
	}

}
