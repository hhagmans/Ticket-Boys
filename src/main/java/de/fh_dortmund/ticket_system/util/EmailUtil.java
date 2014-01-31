package de.fh_dortmund.ticket_system.util;

import javax.faces.bean.ManagedBean;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Class to create and sent emails
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
public class EmailUtil {
	public static final String HOST = "smtp.evonik.internal";
	public static final Integer SMTP_PORT = 25;
	public static final String FROM = "tinnef@evonik.com";
//	public static final String USERNAME = "ticketboys1337@gmail.com";
//	public static final String PASSWORD = "ticketboysevonik";
	public static final String SUBJECT = "Tinnef: Ihr Dispatcher-Einsatz steht bevor";
	public static final String CONFLICTSUBJECT = "Tinnef: Bei der Einsatzplanung ist ein Konflikt aufgetreten";

	public static Email sendConfEmail(String msg, String... to)
			throws EmailException {
		return sendEmailWithSubject(CONFLICTSUBJECT, msg, to);
	}

	public static Email sendEmail(String msg, String... to)
			throws EmailException {
		return sendEmailWithSubject(SUBJECT, msg, to);
	}

	public static Email sendEmailWithSubject(String subject, String msg,
			String... to) throws EmailException {
		Email email = createEmail(msg);
		email.setSubject(subject);

		return send(email, to);
	}

	public static Email createEmail(String msg) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOST);
		email.setSmtpPort(SMTP_PORT);
//		email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
//		email.setSSL(true);
//		email.setTLS(true);
//		email.setSSLOnConnect(true);
		email.setFrom(FROM, "Tinnef Application");
		email.setMsg(msg);

		return email;
	}

	private static Email send(Email email, String... to) throws EmailException {

		email.addTo(to);

		Thread t1 = new Thread(new EmailSender(email));
		t1.start();

		return email;
	}
}
