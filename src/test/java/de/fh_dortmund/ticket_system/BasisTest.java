package de.fh_dortmund.ticket_system;

import static org.junit.Assert.*;
import de.fh_dortmund.ticket_system.authentication.Authentication;

import org.junit.Test;

public class BasisTest {

	@Test
	public void testHelloBean() {
		Authentication hellobean = new Authentication();
		hellobean.setName("Test");
		assert hellobean.getName() == "Test";
	}

}
