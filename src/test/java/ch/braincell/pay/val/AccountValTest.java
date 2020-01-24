package ch.braincell.pay.val;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AccountValTest {

	@Test
	public void testCheckPFAccountChecksum() throws Exception {
		assertEquals(true, AccountVal.checkPFAccountChecksum("99-2345-2"));
		assertEquals(true, AccountVal.checkPFAccountChecksum("990023452"));
	}

	@Test
	public void testFormatPFAccountToDigit() throws Exception {
		assertEquals("990023452", AccountVal.formatPFAccountToDigit("99-2345-2")); 
	}

	@Test
	public void testFormatPFAccountToVisual() throws Exception {
		assertEquals("99-2345-2", AccountVal.formatPFAccountToVisual("990023452"));
	}

	@Test
	public void testCheckIBANChecksum() throws Exception {
		// IBAN Examples from https://www.paymentstandards.ch/dam/downloads/style-guide-qr-rechnung.zip
		assertEquals(true, AccountVal.checkIBANChecksum("CH5800791123000889012"));
		assertEquals(true, AccountVal.checkIBANChecksum("CH58 0079 1123 0008 8901 2"));
		assertEquals(true, AccountVal.checkIBANChecksum("CH52 0483 5012 3456 7100 0"));
		assertEquals(true, AccountVal.checkIBANChecksum("CH44 3199 9123 0008 8901 2"));
	}

	@Test
	public void testGetIBANChecksum() throws Exception {
		assertEquals(58, AccountVal.getIBANChecksum("CH5800791123000889012"));
		assertEquals(58, AccountVal.getIBANChecksum("CH58 0079 1123 0008 8901 2"));
	}

	@Test
	public void testFormatIBANToVisual() throws Exception {
		assertEquals("CH58 0079 1123 0008 8901 2", AccountVal.formatIBANToVisual("CH5800791123000889012"));
	}

}
