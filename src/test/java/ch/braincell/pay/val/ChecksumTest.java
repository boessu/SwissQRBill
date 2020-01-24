package ch.braincell.pay.val;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ChecksumTest {

	@Test
	public void testGetModulo10() throws Exception {
		assertEquals(2, Checksum.getModulo10("99002345"));
		assertEquals(2, Checksum.getModulo10("30300 100004442946001032054"));
	}

	@Test
	public void testCheckModulo10() throws Exception {
		assertEquals(true, Checksum.checkModulo10("990023452"));
		assertEquals(true, Checksum.checkModulo10("303001000044429460010320542"));
	}

	@Test
	public void testGetModulo97() throws Exception {
		assertEquals(41, Checksum.getModulo97("ABCDEFGabcdefg123"));
		assertEquals(45, Checksum.getModulo97("G72UURRF"));
	}

	@Test
	public void testCheckModulo97() throws Exception {
		assertEquals(true, Checksum.checkModulo97("ABCDEFGabcdefg12341"));
	}

}
