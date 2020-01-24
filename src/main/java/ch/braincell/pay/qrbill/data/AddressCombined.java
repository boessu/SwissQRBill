package ch.braincell.pay.qrbill.data;

import ch.braincell.pay.qrbill.val.QRRContent;

public class AddressCombined extends Address {

	private String adressLine1 = "";
	private String adressLine2 = "";

	public AddressCombined(String name, String country) throws QRBillException {
		super(name, country, PatternEnum.COMBINED);
	}

	public String getAdressLine1() {
		return adressLine1;
	}

	public AddressCombined setAdressLine(String adressLine) throws QRBillException {
		if (adressLine == null || adressLine.isEmpty())
			this.adressLine1 = "";
		else if (adressLine.matches(QRRContent.Address.ADDRESS_LINE))
			this.adressLine1 = adressLine;
		else
			throw new QRBillException("Invalid adressline 1", adressLine, QRRContent.Address.ADDRESS_LINE);
		adressLine2 = "";
		return this;
	}

	public AddressCombined setAdressLine(String adressLine1, String adressLine2) throws QRBillException {
		setAdressLine(adressLine1);
		if (adressLine2 == null || adressLine2.isEmpty())
			this.adressLine2 = "";
		else if (adressLine2.matches(QRRContent.Address.ADDRESS_LINE))
			this.adressLine2 = adressLine2;
		else
			throw new QRBillException("Invalid adressline 2", adressLine2, QRRContent.Address.ADDRESS_LINE);
		return this;
	}

	public String getAdressLine2() {
		return adressLine2;
	}

	@Override
	protected String getToStringAddressLines() {
		return 	getAdressLine1() + '\n' + 
				getAdressLine2() + '\n'	+ 
				'\n' + 
				'\n';
	}

}
