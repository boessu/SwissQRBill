package ch.braincell.pay.qrbill.data;

import ch.braincell.pay.qrbill.val.QRRContent;

public abstract class Address {

	private String name;
	private String country;
	private PatternEnum pattern;

	public enum PatternEnum {
		STRUCTURED('S'), COMBINED('K');

		private char shortcut;

		PatternEnum(char shortcut) {
			this.shortcut = shortcut;
		}

		public String getShortcut() {
			return String.valueOf(shortcut);
		}
	}

	protected Address(String name, String country, PatternEnum pattern) throws QRBillException {
		this.setName(name);
		this.setCountry(country);
		this.pattern = pattern;
	}

	public String getCountry() {
		return country;
	}

	/**
	 * Sets a country code, which should be 2 characters. Actually it doesn't check
	 * if it is a valid country against the official ISO 3166-1 list.
	 * 
	 * @see https://www.iso.org/obp/ui/#search/code/ for a list of actual country
	 *      codes.
	 * @param country Code of a country, two letters.
	 * @throws QRBillException
	 */
	private void setCountry(String country) throws QRBillException {
		if (country != null &&  country.matches(QRRContent.Address.COUNTRY)) {
			this.country = country;
		} else
			throw new QRBillException("Invalid country code", country, QRRContent.Address.COUNTRY);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) throws QRBillException {
		if (name != null && name.matches(QRRContent.Address.NAME))
			this.name = name;
		else
			throw new QRBillException("Invalid name", name, QRRContent.Address.NAME);
	}

	public PatternEnum getPattern() {
		return pattern;
	}

	protected abstract String getToStringAddressLines();

	@Override
	public String toString() {
		return getPattern().getShortcut() + '\n' + //
				getName() + '\n' + getToStringAddressLines() + getCountry() + '\n';
	}

	public static String getEmptyAddress() {
		return "\n\n\n\n\n\n\n";
	}
}
