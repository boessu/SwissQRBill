package ch.braincell.pay.qrbill.data;

import ch.braincell.pay.qrbill.val.QRRContent;

public class BillInformation {
	private String bookingInfo = "";
	private String alternateParam = "";
	private String alternateParam2 = "";

	public BillInformation setBookingInfo(String info) throws QRBillException {
		if (info == null || info.isEmpty())
			this.bookingInfo = "";
		else if (info.matches(QRRContent.BillInformation.STRUCT_BOOK_INFO))
			this.bookingInfo = info;
		else
			throw new QRBillException("Illegal Booking Infomration", info, QRRContent.BillInformation.STRUCT_BOOK_INFO);
		return this;
	}

	public String getBookingInfo() {
		return bookingInfo;
	}

	public String[] getAlternateParam() {
		String[] result = { alternateParam, alternateParam2 };
		return result;
	}

	public BillInformation setAlternateParam(String param) throws QRBillException {
		if (param == null || param.isEmpty())
			this.alternateParam = "";
		else if (param.matches(QRRContent.BillInformation.ALTERNATE_PARAMETERS))
			this.alternateParam = param;
		else
			throw new QRBillException("Illegal Alternate Parameter (1st line)", param,
					QRRContent.BillInformation.ALTERNATE_PARAMETERS);
		this.alternateParam2 = "";
		return this;
	}

	public BillInformation setAlternateParam(String param1, String param2) throws QRBillException {
		setAlternateParam(param1);
		if (param2 == null || param2.isEmpty()) // if 2nd line is empty, set an empty string.
			this.alternateParam2 = "";
		else if (this.alternateParam.isEmpty()) // if 1st line is empty but 2nd line isn't, throw an exception.
			throw new QRBillException("Alternate Parameter 1st line is empty but 2nd line isn't.", param2);
		else if (param2.matches(QRRContent.BillInformation.ALTERNATE_PARAMETERS)) // formal check 2nd line.
			this.alternateParam2 = param2;
		else	// if formal check fails, throw an exception.
			throw new QRBillException("Illegal Alternate Param (2nd line)", param2,
					QRRContent.BillInformation.ALTERNATE_PARAMETERS);
		return this;
	}

	@Override
	public String toString() {
		if (bookingInfo.isEmpty() && alternateParam.isEmpty())
			return "\n"; // empty line for Optional booking information.

		String result = "";
		if (!bookingInfo.isEmpty())
			result += bookingInfo + '\n';
		else
			result += '\n';
		if (!alternateParam.isEmpty())
			result += alternateParam + '\n';
		else
			return result; // if alternate1 is null, there should not be a second line.
		if (!alternateParam2.isEmpty())
			result += alternateParam2;
		return result;
	}
}
