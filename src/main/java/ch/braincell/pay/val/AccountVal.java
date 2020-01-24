package ch.braincell.pay.val;

/**
 * Validation and visualization of Account Numbers.
 * 
 * @author Boessu
 *
 */
public class AccountVal {

	/**
	 * Regex for international IBAN.
	 */
	public static final String IBAN = "[A-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}";
	/**
	 * Regex for Swiss PostFinance accounts in the visual form, e.g.
	 * {@code 99-2345-2}.
	 */
	public static final String PFACCOUNT_VISUAL = "[0-9]{2,2}-[0-9]{1,6}-[0-9]{1,1}";
	/**
	 * Regex for Swiss PostFinance accounts in the 9 digit form, e.g.
	 * {@code 990023452}.
	 */
	public static final String PFACCOUNT_DIGIT = "[0-9]{9,9}";

	/**
	 * Checks the Swiss PostFinance account syntax and checksum if it is valid or
	 * not. Both forms are allowed (visual and 9 digits).
	 * 
	 * @param account Swiss PF account, in the format 9 digits or visual.
	 * @return true if format and checksum is valid, false otherwise.
	 */
	public static boolean checkPFAccountChecksum(String account) {
		if (account.matches(AccountVal.PFACCOUNT_DIGIT)) {
			return Checksum.checkModulo10(account);
		} else if (account.matches(AccountVal.PFACCOUNT_VISUAL)) {
			return Checksum.checkModulo10(formatPFAccountToDigit(account));
		}
		return false;
	}

	/**
	 * Formats a Swiss PF account from the visual format (with '-' as separator) to
	 * the 9 digit format (e.g. for the OCRB coding lines of a payment slip).
	 * 
	 * @param accountVisual Swiss PF account in the visual format (e.g.
	 *                      {@code 99-2345-2})
	 * @return 9 digit format (e.g. {@code 990023452}) if it was a valid visual
	 *         format. It returns just accountVisual content if it wasn't a valid
	 *         visual format.
	 */
	public static String formatPFAccountToDigit(String accountVisual) {
		if (accountVisual.matches(AccountVal.PFACCOUNT_VISUAL)) {
			int oc = Integer.valueOf(accountVisual.substring(0, 2));
			int accountnr = Integer
					.valueOf(accountVisual.substring(accountVisual.indexOf('-') + 1, accountVisual.lastIndexOf('-')));
			int checksum = Integer.valueOf(String.valueOf(accountVisual.charAt(accountVisual.length() - 1)));
			return String.format("%1$02d%2$06d%3$01d", oc, accountnr, checksum);
		}
		return accountVisual;
	}

	/**
	 * Formats a Swiss PF account from the techical format (9 digits) to the visual
	 * format (e.g. for the information section of a payment slip).
	 * 
	 * @param accountDigits Swiss PF account in the 9 digit format (e.g.
	 *                      {@code 990023452})
	 * @return the visual format (e.g. {@code 99-2345-2}) if it was a valid 9 digit
	 *         format. It returns just accountDigits content if it wasn't a valid
	 *         digit format.
	 */
	public static String formatPFAccountToVisual(String accountDigits) {
		if (accountDigits.matches(AccountVal.PFACCOUNT_DIGIT)) {
			int oc = Integer.valueOf(accountDigits.substring(0, 2));
			int accountnr = Integer.valueOf(accountDigits.substring(2, 8));
			int checksum = Integer.valueOf(String.valueOf(accountDigits.charAt(8)));
			return String.format("%1$02d-%2$d-%3$d", oc, accountnr, checksum);
		}
		return accountDigits;
	}

	/**
	 * Checks the IBAN syntax and checksum if it is valid or not.
	 * 
	 * @param IBAN IBAN an IBAN in the IBAN syntax (ISO 13616-1:2007, part 1).
	 * @return true if it is a valid IBAN and the checksum is correct, false
	 *         otherwise.
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/International_Bank_Account_Number">Wikipedia</a>
	 *      for details.
	 */
	public static boolean checkIBANChecksum(String IBAN) {
		IBAN = IBAN.replaceAll("\\s", "");
		if (IBAN.matches(AccountVal.IBAN)) {
			return Checksum.checkModulo97(IBAN.substring(4) + IBAN.substring(0, 4));
		}
		return false;
	}

	/**
	 * Checks the IBAN syntax and calculates the Modulo 97 checksum if it is an
	 * IBAN. The checksum is directly after the country code in the position 3 & 4
	 * of an IBAN.
	 * 
	 * @param IBAN an IBAN in the IBAN syntax (ISO 13616-1:2007, part 1).
	 * @return The Modulo 97 checksum of the IBAN, if it is an IBAN. Returns -1 if
	 *         it is not an IBAN in syntax.
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/International_Bank_Account_Number">Wikipedia</a>
	 *      for details.
	 */
	public static long getIBANChecksum(String IBAN) {
		IBAN = IBAN.replaceAll("\\s", "");
		if (IBAN.matches(AccountVal.IBAN))
			return Checksum.getModulo97(IBAN.substring(4) + IBAN.substring(0, 2));
		return -1;
	}

	/**
	 * Formats an IBAN from the technical format (without spaces) to the visual
	 * format (according to ISO 13616-1:2007).
	 * 
	 * @param IBAN IBAN to format, without spaces.
	 * @return IBAN visual format, space in every 5th place.
	 */
	public static String formatIBANToVisual(String IBAN) {
		return Format.format(IBAN, ' ', 4, true);
	}

}
