package ch.braincell.pay.qrbill.data;

public class QRBillException extends Exception {

	private static final long serialVersionUID = 3405382179911034862L;
	
	private final String value;
	private final String pattern;

	QRBillException(String message, String value, String pattern) {
		super(message + ": '" + value + "' Regex: " + pattern);
		this.value = value;
		this.pattern = pattern;
	}

	QRBillException(String message, String value) {
		super(message + ": '" + value + "'");
		this.value = value;
		this.pattern = null;
	}

	public String getValue() {
		return value;
	}

	public String getPattern() {
		return pattern;
	}
}
