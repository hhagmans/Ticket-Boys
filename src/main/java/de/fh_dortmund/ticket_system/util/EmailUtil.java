package de.fh_dortmund.ticket_system.util;

import javax.faces.bean.ManagedBean;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

@ManagedBean
public class EmailUtil
{
	public static final String	HOST		= "smtp.googlemail.com";
	public static final Integer	SMTP_PORT	= 465;
	public static final String	FROM		= "Ticket-Boys@fh-dortmund.de";
	public static final String	SUBJECT		= "Ticket-Boys";

	public Email sendEmail(String msg, String... to) throws EmailException
	{
		return sendEmailWithSubject(SUBJECT, msg, to);
	}

	public Email sendEmailWithSubject(String subject, String msg, String... to) throws EmailException
	{
		Email email = createEmail(msg);
		email.setSubject(subject);

		return send(email, to);
	}

	public Email createEmail(String msg) throws EmailException
	{
		Email email = new SimpleEmail();
		email.setHostName(HOST);
		email.setSmtpPort(SMTP_PORT);
		email.setSSL(true);
		email.setTLS(true);
		email.setSSLOnConnect(true);
		email.setFrom(FROM);
		email.setMsg(msg);

		return email;
	}

	private Email send(Email email, String... to) throws EmailException
	{
		email.addTo(to);
		// TODO Send wird erst mal ignoriert bis wir einen server haben
		// email.send();

		return email;
	}
}
