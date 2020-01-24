package ch.braincell.pay.qrbill.data;

import java.util.Currency;

import ch.braincell.pay.qrbill.val.QRRContent;

public class Amount {
	private String amount = "";
	String currency;

	public Amount(Currency currency) throws QRBillException {
		setCurrency(currency.getCurrencyCode());
	}

	private Amount setCurrency(String currency) throws QRBillException {
		if (currency.matches(QRRContent.Amount.CURRENCY))
			this.currency = currency;
		else
			throw new QRBillException("Not valid currency", currency, QRRContent.Amount.CURRENCY);
		return this;
	}

	public Currency getCurrency() {
		return Currency.getInstance(currency);
	}

	public String getAmount() {
		return amount;
	}

	public Amount setAmount(String amount) throws QRBillException {
		if (amount == null || amount.isEmpty())
			this.amount = "";
		else if (amount.matches(QRRContent.Amount.AMOUNT))
			this.amount = amount;
		else
			throw new QRBillException("Not valid amount", amount, QRRContent.Amount.AMOUNT);
		return this;
	}
	
	@Override
	public String toString() {
		return getAmount() + '\n' +
				getCurrency() + '\n';
	}
}
