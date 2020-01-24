package ch.braincell.pay.qrbill.data;

import java.util.Currency;

import ch.braincell.pay.qrbill.val.QRRContent;
import ch.braincell.pay.val.AccountVal;

/**
 * If you try to create a QRR Content: Start with this class. toString() results
 * in a valid QRR content.
 * 
 * @author Boessu
 *
 */
public class QRBill {

	private String creditorIBAN;
	private Address creditor;
	private Address ultimateCreditor = null;
	private Amount amount;
	private Address ultimateDebtor = null;
	private Remittance remittance = new Remittance();
	private BillInformation billInformation = new BillInformation();

	public QRBill(String creditorIBAN, String creditorName, String creditorCountry, Currency currency)
			throws QRBillException {
		setCreditorIBAN(creditorIBAN);
		setCreditor(new AddressStructured(creditorName, creditorCountry));
		setAmount(new Amount(currency));
	}

	private void setCreditorIBAN(String IBAN) throws QRBillException {
		this.creditorIBAN = null;
		if (IBAN.matches(QRRContent.IBAN_CHLI))
			if (AccountVal.checkIBANChecksum(IBAN))
				this.creditorIBAN = IBAN;
			else
				throw new QRBillException("Creditor IBAN not valid: Checksum failed.", IBAN);
		else
			throw new QRBillException("Only CH or LI IBAN allowed.", IBAN, QRRContent.IBAN_CHLI);
	}

	public String getCreditorIBAN() {
		return creditorIBAN;
	}

	private void setCreditor(Address creditor) {
		this.creditor = creditor;
	}

	public Address getCreditor() {
		return creditor;
	}

	protected Address getUltimateCreditor() {
		return ultimateCreditor;
	}

	protected void setUltimateCreditor(Address ultimateCreditor) {
		this.ultimateCreditor = ultimateCreditor;
	}

	public Amount getAmount() {
		return amount;
	}

	private void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Address getUltimateDebtor() {
		return ultimateDebtor;
	}

	public void setUltimateDebtor(Address ultimateDebtor) {
		this.ultimateDebtor = ultimateDebtor;
	}

	public Remittance getRemittance() {
		return remittance;
	}

	public BillInformation getBillInformation() {
		return billInformation;
	}

	////////////////////////////////////////////////////////////////////////
	// to String and so...
	////////////////////////////////////////////////////////////////////////

	private String getCreditorString() {
		return getCreditor().toString();
	}

	private String getAmountString() {
		return getAmount().toString();
	}

	private String getUltimateCreditorString() {
		if (getUltimateCreditor() == null)
			return Address.getEmptyAddress();
		else
			return getUltimateCreditor().toString();
	}

	private String getUltimateDebtorString() {
		if (getUltimateDebtor() == null)
			return Address.getEmptyAddress();
		else
			return getUltimateDebtor().toString();
	}

	private String getRemittanceString() {
		return getRemittance().toString();
	}

	private String getBillInformationString() {
		return getBillInformation().toString();
	}

	@Override
	public String toString() {
		return "SPC" + '\n' + "0200" + '\n' + "1" + '\n' + getCreditorIBAN() + '\n' + getCreditorString()
				+ getUltimateCreditorString() + getAmountString() + getUltimateDebtorString() + getRemittanceString()
				+ "EPD" + '\n' + getBillInformationString();
	}
}
