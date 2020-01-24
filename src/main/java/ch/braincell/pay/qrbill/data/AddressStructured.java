package ch.braincell.pay.qrbill.data;

import ch.braincell.pay.qrbill.val.QRRContent;

public class AddressStructured extends Address {

	private String street;
	private String buildingNumber = "";
	private String postalCode = "";
	private String town = "";

	public AddressStructured(String name, String country) throws QRBillException {
		super(name, country, PatternEnum.STRUCTURED);
	}
	
	public String getStreet() {
		return street;
	}

	public AddressStructured setStreet(String street) throws QRBillException {
		if (street == null || street.isEmpty())
			this.street = "";
		else if (street.matches(QRRContent.Address.STREET))
			this.street = street;
		else
			throw new QRBillException("Invalid street name", street, QRRContent.Address.STREET);
		return this;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public AddressStructured setBuildingNumber(String buildingNumber) throws QRBillException {
		if (buildingNumber == null || buildingNumber.isEmpty())
			this.buildingNumber = "";
		else if (buildingNumber.matches(QRRContent.Address.BUILDING_NUMBER))
			this.buildingNumber = buildingNumber;
		else
			throw new QRBillException("Invalid building number", buildingNumber, QRRContent.Address.BUILDING_NUMBER);
		return this;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public AddressStructured setPostalCode(String postalCode) throws QRBillException {
		if (postalCode == null || postalCode.isEmpty())
			this.postalCode = "";
		else if (postalCode.matches(QRRContent.Address.POSTAL_CODE))
			this.postalCode = postalCode;
		else
			throw new QRBillException("Invalid postal code", postalCode, QRRContent.Address.POSTAL_CODE);
		return this;
	}

	public String getTown() {
		return town;
	}

	public AddressStructured setTown(String town) throws QRBillException {
		if (town == null || town.isEmpty()) 
			this.town = "";
		else if (town.matches(QRRContent.Address.TOWN))
			this.town = town;
		else
			throw new QRBillException("Invalid town", town, QRRContent.Address.TOWN);
		return this;
	}

	@Override
	protected String getToStringAddressLines() {
		return getStreet() + '\n' +
				getBuildingNumber() + '\n' +
				getPostalCode() + '\n' +
				getTown() + '\n';
	}

}
