package ch.braincell.pay.val;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReferenceValTest {

	@Test
	public void testGetCreditorReferenceChecksum() throws Exception {
		assertEquals(45, ReferenceVal.getCreditorReferenceChecksum("RF45 G72U UR"));
		assertEquals(65, ReferenceVal.getCreditorReferenceChecksum("RF6518K5"));
		assertEquals(51, ReferenceVal.getCreditorReferenceChecksum("RF35C4"));
		assertEquals(68, ReferenceVal.getCreditorReferenceChecksum("RF214377"));
	}

	@Test
	public void testCheckCreditorReferenceChecksum() throws Exception {
		// SCOR Reference from https://www.paymentstandards.ch/dam/downloads/style-guide-qr-rechnung.zip
		assertEquals(true, ReferenceVal.checkCreditorReferenceChecksum("RF18 5390 0754 7034"));
		// https://www.mobilefish.com/services/creditor_reference/creditor_reference.php
		assertEquals(true, ReferenceVal.checkCreditorReferenceChecksum("RF45 G72U UR"));
		assertEquals(true, ReferenceVal.checkCreditorReferenceChecksum("RF6518K5"));
		assertEquals(false, ReferenceVal.checkCreditorReferenceChecksum("RF35C4"));
		assertEquals(false, ReferenceVal.checkCreditorReferenceChecksum("RF214377"));
	}

	@Test
	public void testFormatCreditorReference() throws Exception {
		assertEquals("RF45 G72U UR", ReferenceVal.formatCreditorReference("RF45G72UUR"));
		assertEquals("2100 0000 0003 1394 7143 0009 017", ReferenceVal.formatCreditorReference("210000000003139471430009017"));
	}

	@Test
	public void testGetQRReferenceChecksum() throws Exception {
		assertEquals(7, ReferenceVal.getQRReferenceChecksum("21000 000000313947143000901"));
	}

	@Test
	public void testCheckQRReferenceChecksum() throws Exception {
		// QRR Reference from https://www.paymentstandards.ch/dam/downloads/style-guide-qr-rechnung.zip
		assertEquals(true, ReferenceVal.checkQRReferenceChecksum("210000000003139471430009017"));
	}

	@Test
	public void testFormatQRReference() throws Exception {
		assertEquals("21 00000 00003 13947 14300 09017", ReferenceVal.formatQRReference("210000000003139471430009017"));
	}

}
