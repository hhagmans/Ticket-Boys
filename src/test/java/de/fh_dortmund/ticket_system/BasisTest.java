package de.fh_dortmund.ticket_system;

import static org.junit.Assert.*;

import de.fh_dortmund.ticket_system.HelloBean;

import org.junit.Test;

public class BasisTest {

	@Test
	public void testHelloBean() {
		HelloBean hellobean = new HelloBean();
		hellobean.setName("Test");
		assert hellobean.getName() == "Test";
	}

}
