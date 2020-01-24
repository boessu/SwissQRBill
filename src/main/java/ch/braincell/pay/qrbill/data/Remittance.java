package ch.braincell.pay.qrbill.data;

import ch.braincell.pay.qrbill.val.QRRContent;
import ch.braincell.pay.val.ReferenceVal;

public class Remittance {

	protected enum ReferenceTypeEnum {
		QRR, SCOR, NON
	}

	private ReferenceTypeEnum referenceType = ReferenceTypeEnum.NON;
	private String reference = "";
	private String message = "";

	public ReferenceTypeEnum getReferenceType() {
		return referenceType;
	}

	public String getReference() {
		return reference;
	}

	public Remittance setReference(String reference) throws QRBillException {
		if (reference == null || reference.isEmpty()) {
			this.reference = "";
			this.referenceType = ReferenceTypeEnum.NON;
		} else if (reference.matches(ReferenceVal.REF_QRR)) {
			this.reference = reference;
			this.referenceType = ReferenceTypeEnum.QRR;
		} else if ((reference.matches(ReferenceVal.REF_SCOR))) {
			this.reference = reference;
			this.referenceType = ReferenceTypeEnum.SCOR;
		} else {
			throw new QRBillException("Reference is neither QRR nor SCOR.", reference);
		}
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Remittance setMessage(String message) throws QRBillException {
		if (message == null || message.isEmpty())
			message = "";
		else if (message.matches(QRRContent.Remittance.UNSTRUCTURED))
			this.message = message;
		else
			throw new QRBillException("Message has invalid content.", message, QRRContent.Remittance.UNSTRUCTURED);
		return this;
	}

	@Override
	public String toString() {
		return getReferenceType().name() + '\n' + getReference() + '\n' + getMessage() + '\n';
	}

}
