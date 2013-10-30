package de.fh_dortmund.ticket_system;

import static org.junit.Assert.*;

import de.fh_dortmund.ticket_system.Login;

import org.junit.Test;

public class BasisTest {

	@Test
	public void testHelloBean() {
		Login hellobean = new Login();
		hellobean.setName("Test");
		assert hellobean.getName() == "Test";
	}

}
