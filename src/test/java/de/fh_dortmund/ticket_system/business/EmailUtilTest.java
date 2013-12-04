package de.fh_dortmund.ticket_system.business;



/**
public class EmailUtilTest
{
	private static final String	TEST_EMAIL1		= "test@test.de";
	private static final String	TEST_EMAIL2		= "test2@test.de";
	private static final String	TEST_MSG		= "Test";
	private static final String	TEST_SUBJECT	= "TestSubject";

	EmailUtil					util;
	Email						email;

	@Before
	public void setUp()
	{
		util = new EmailUtil();
	}

	@Test
	public void nullTest()
	{
		try
		{
			email = util.createEmail(TEST_MSG);
		}
		catch (EmailException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(email);
	}

	@Test
	public void createEmail()
	{
		try
		{
			email = util.createEmail(TEST_MSG);
		}
		catch (EmailException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(email.getHostName(), EmailUtil.HOST);
		assertEquals(email.getSmtpPort(), EmailUtil.SMTP_PORT.toString());
		assertEquals(email.getFromAddress().toString(), EmailUtil.FROM);
	}

	@Test
	public void sendEmailToOneAdress()
	{
		try
		{
			email = util.sendEmailWithSubject(TEST_SUBJECT, TEST_MSG, TEST_EMAIL1);
		}
		catch (EmailException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(email.getSubject(), TEST_SUBJECT);
		assertContains(email.getToAddresses(), TEST_EMAIL1);
	}

	@Test
	public void sendEmailToMultipleAdresses()
	{
		try
		{
			email = util.sendEmailWithSubject(TEST_SUBJECT, TEST_MSG, TEST_EMAIL1, TEST_EMAIL2);
		}
		catch (EmailException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(email.getSubject(), TEST_SUBJECT);
		assertContains(email.getToAddresses(), TEST_EMAIL1, TEST_EMAIL2);
	}

	private void assertContains(List<InternetAddress> toAddresses, String... testEmails)
	{
		List<String> adresses = new ArrayList<String>();
		for (InternetAddress internetAddress : toAddresses)
		{
			adresses.add(internetAddress.getAddress());
		}

		for (String email : testEmails)
		{
			boolean b = adresses.contains(email);
			Assert.assertTrue(b);
		}
	}
}
*/