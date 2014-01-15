package de.fh_dortmund.ticket_system.util;

import org.apache.commons.mail.Email;
import org.apache.log4j.Logger;

/**
 * Class for sending emails to users eith conflicts etc.
 * 
 * @author Ticket-Boys
 * 
 */
public class EmailSender implements Runnable {

	private Email email;
	private static Logger log = Logger.getLogger(EmailSender.class);

	public EmailSender(Email email) {
		this.email = email;
	}

	@Override
	public void run() {
		try {
			email.send();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

}
