package de.fh_dortmund.ticket_system.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;

@Ignore
public class AuthenticationTest
{
	private static final Role ROLE = Role.admin;
	private static final String CITY = "CITY";
	private static final String FIRSTNAME = "FIRSTNAME";
	private static final String LASTNAME = "LASTNAME";
	private static final String PASSWORT = "Blub";
	private static final String USER = "User1";
	Authentication auth;

	@BeforeClass
	public static void setUpClass()
	{
		EmployeeData employeeData = new EmployeeData();
		employeeData.add(new Employee(USER, FIRSTNAME, LASTNAME, CITY, ROLE, 0, 0));
	}

	@Before
	public void setUp() throws Exception
	{
		auth = new NoAuthentication();
		auth.setEmployeeData(new EmployeeData());
	}

	@Test
	public void createSimpleAuthentication()
	{
		auth.setName(USER);
		auth.setPasswort(PASSWORT);

		assertEquals(auth.getName(), USER);
		assertEquals(auth.getPasswort(), PASSWORT);
	}

	@Test
	public void checkEmployeeFound()
	{
		auth.setName(USER);
		auth.setPasswort(PASSWORT);

		auth.login();

		Employee emp = auth.getEmployee();

		assertNotNull(emp);
		assertEquals(emp.getKonzernID(), USER);
		assertEquals(emp.getCity(), CITY);
		assertEquals(emp.getFirstName(), FIRSTNAME);
		assertEquals(emp.getLastName(), LASTNAME);
		assertEquals(emp.getRole(), ROLE);
	}

	@Test
	public void testLogin()
	{
		auth.setName(USER);
		auth.setPasswort(PASSWORT);

		auth.login();

		assertTrue(auth.isLoggedIn());
	}

	@Test
	public void testLogout()
	{
		auth.setName(USER);
		auth.setPasswort(PASSWORT);

		auth.login();
		auth.logout();

		assertFalse(auth.isLoggedIn());
	}
}
