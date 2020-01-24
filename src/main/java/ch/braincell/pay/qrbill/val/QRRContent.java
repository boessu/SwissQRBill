package ch.braincell.pay.qrbill.val;

import ch.braincell.pay.val.ReferenceVal;

/**
 * A class which includes the regular expression strings for checking the lines
 * of a QR bill code for correctness according to
 * <a href="https://www.paymentstandards.ch">Swiss payment standards</a>. This
 * can be useful for quality assurance test of the QR content, user entry checks
 * in a GUI and so on.
 * 
 * @author Boessu
 *
 */
public class QRRContent {

	/**
	 * The whole content of the QR should not exceed 997 characters. This also
	 * includes the newline-chars (CR + LF or LF)!
	 */
	public static final int MAX_CONTENT_SIZE = 997;
	/**
	 * Includes: {@link Remittance#UNSTRUCTURED} +
	 * {@link BillInformation#STRUCT_BOOK_INFO}
	 */
	public static final int MAX_REMITTANCE_AND_INFO_SIZE = 140;
	/** all lines up to and with {@link #TRAILER} line */
	public static final int MIN_LINES = 31;
	/**
	 * all lines including lines {@link BillInformation#STRUCT_BOOK_INFO} and
	 * {@link BillInformation#ALTERNATE_PARAMETERS}
	 */
	public static final int MAX_LINES = 34;

	/** The coding is UTF-8 but only latin chars are allowed. */
	public static final String LATIN_CHARS = "([a-zA-Z0-9\\.,;:'\\+\\-/\\(\\)?\\*\\[\\]\\{\\}\\\\`´~ ]|[!&quot;#%&amp;&lt;&gt;÷=@_$£]|[àáâäçèéêëìíîïñòóôöùúûüýßÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜÑ])";
	/** Helper Regex to filter all non-latin chars. */
	public static final String NON_LATIN_CHARS = "([^a-zA-Z0-9\\.,;:'\\+\\-/\\(\\)?\\*\\[\\]\\{\\}\\\\`´~ ]|[^!&quot;#%&amp;&lt;&gt;÷=@_$£]|[^àáâäçèéêëìíîïñòóôöùúûüýßÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜÑ])";

	/**
	 * Helper method to replace all non-latin characters with a ".". According to
	 * the swiss implementation guidelines for ISO20022 payment orders (pain.001,
	 * pain.008), if a bank doesn't support a latin character and if there is no
	 * adequate replacement, the character is often replaced with a dot. So it's at
	 * least adequate if we replace all characters outside of the latin charset
	 * generally with a dot.
	 * 
	 * @param value the string to replace non-latin characters.
	 * @return a string with all non-latin characters replaced with a ".".
	 */
	public static final String replaceNonLatinChars(String value) {
		if (value != null)
			return value.replaceAll(NON_LATIN_CHARS, ".");
		return null;
	}

	/**
	 * Header of the QR content.
	 * 
	 * @author Boessu
	 *
	 */
	public static final class Header {
		/** Mandatory. Identifies the QRR standard. */
		public static final String QR_TYPE = "SPC";
		/** Mandatory. Version of the QRR standard. */
		public static final String VERSION = "0200";
		/** Mandatory. Used character encoding. Actually only "1" (for UTF-8) allowed */
		public static final String CODING = "1"; // Mandatory
	}

	/** Mandatory. Only Swiss or Liechtenstein IBAN are allowed in QRR. */
	public static final String IBAN_CHLI = "(CH|LI){1}[0-9]{7}[A-Z0-9]{12}";

	/**
	 * Address<br/>
	 * Creditor: Mandatory<br/>
	 * Ultimate Creditor: at the moment not allowed.<br/>
	 * Ultimate Debtor: Optional. If it is not in the code, a blank field should be
	 * there for a hand written address.<br/>
	 * 
	 * @author Boessu
	 */
	public static final class Address {
		/**
		 * Mandatory.<br/>
		 * S - structured address<br/>
		 * K - combined address
		 */
		public static final String TYPE = "^(S|K){1}$";
		/** Mandatory. */
		public static final String NAME = LATIN_CHARS + "{0,70}";
		/** Optional. Only structured address. */
		public static final String STREET = LATIN_CHARS + "{0,70}";
		/** Optional. Only structured address. */
		public static final String BUILDING_NUMBER = LATIN_CHARS + "{0,16}";
		/** Optional. Only structured address. */
		public static final String POSTAL_CODE = LATIN_CHARS + "{0,16}";
		/** Optional. Only structured address. */
		public static final String TOWN = LATIN_CHARS + "{0,35}";
		/** Optional. Only combined address. */
		public static final String ADDRESS_LINE = LATIN_CHARS + "{0,70}";
		/** Mandatory. Country code according to ISO 3166-1. */
		public static final String COUNTRY = "[A-Z]{2,2}";

		/** That would be finally a structured address. */
		public static final String[] STRUCTURED_ADDRESS = { "S", NAME, STREET, BUILDING_NUMBER, POSTAL_CODE, TOWN,
				COUNTRY };
		/** That would be finally a combined address. */
		public static final String[] COMBINED_ADDRESS = { "K", NAME, ADDRESS_LINE, ADDRESS_LINE, "", "", COUNTRY };

	}

	/**
	 * At least the {@link #CURRENCY} is mandatory. Only CHF or EUR allowed.
	 * 
	 * @author Boessu
	 *
	 */
	public static final class Amount {
		/**
		 * Optional. If it is not in the code, a blank field should be there for hand
		 * written amount.
		 */
		public static final String AMOUNT = "[0-9'.']{1,12}";
		/** Mandatory. Only Swiss Francs (CHF) or Euros (EUR) allowed. */
		public static final String CURRENCY = "CHF|EUR";
	}

	/**
	 * This is the remittance block. Please note that the fields
	 * {@link #UNSTRUCTURED} + {@link #STRUCT_BOOK_INFO} should be max 140 chars in
	 * size.<br/>
	 * Note that {@link #STRUCT_BOOK_INFO} is not part of the Remittance block (as
	 * you see here) and won't be forwarded to the Creditor along with the payment.
	 * 
	 * @author Boessu
	 *
	 */
	public static final class Remittance {
		/**
		 * Mandatory.<br/>
		 * 
		 * @param QRR  numeric {@link ReferenceVal#REF_QRR QR-Reference}.<br/>
		 * @param SCOR alphanumeric {@link ReferenceVal#REF_SCOR SCOR Reference}.<br/>
		 * @param NON  no refernce at all.
		 */
		public static final String REF_TYPE = "QRR|SCOR|NON"; // Mandatory
		/** That would be finally a QRR reference line sequence. */
		public static final String[] REF_QRR_TYPE = { "QRR", ReferenceVal.REF_QRR };
		/** That would be finally a SCOR reference line sequence. */
		public static final String[] REF_SCOR_TYPE = { "SCOR", ReferenceVal.REF_SCOR };
		/** That would be finally a NON / no reference line sequence. */
		public static final String[] REF_NON_TYPE = { "NON", "" };

		/** Field for the unstructured remittance message in the payment. */
		public static final String UNSTRUCTURED = LATIN_CHARS + "{0,140}"; // Optional
	}

	/** Mandatory. End of Payment data. */
	public static final String TRAILER = "EPD"; // Mandatory

	/**
	 * This block is information about the bill itself. It is not part of the
	 * Payment and will only visible by the (Ultimate) Debtor in special
	 * circumstances. Usually even the (Ultimate) Debtor won't see this information
	 * if he scanned the QR bill directly within his banking app.<br/>
	 * Since it is not part of the payment, you won't see it in the account
	 * statements of the banks and the data won't be forwarded with the payment to
	 * the Creditor! So it's more or less "invisble" for most payment actors in the
	 * payment chain.<br/>
	 * The whole block is optional.
	 * 
	 * @author Boessu
	 *
	 */
	public static final class BillInformation {
		/**
		 * Optional. The Structured Booking Information contains coded information for
		 * automated booking of the payment on the (Ultimate) Debtor's general ledger.
		 * Beside of that, the structure is not part of the QRR standard. SIX will
		 * maintain possible structures outside of the QRR specification. One
		 * possibility of structure is the recommendation of Swico.<br/>
		 */
		public static final String STRUCT_BOOK_INFO = LATIN_CHARS + "{0,140}"; // Optional

		/**
		 * Optional. Alternative scheme parameters. This is not part of the Payment and
		 * will only visible by the (Ultimate) Debtor.<br/>
		 * The first characters define the alternate scheme name, until a separator
		 * character appears. However, the separator is part of the scheme definition
		 * and so it can be any character. There is only a guess it could be a "/", but
		 * it could be something different (defined by the scheme owner).<br/>
		 * The possible schemes is not part of the QRR standard. SIX will maintain
		 * possible schemes outside of the QRR specification.
		 */
		public static final String ALTERNATE_PARAMETERS = LATIN_CHARS + "{0,100}"; // Optional, up to 2 times
	}
}
