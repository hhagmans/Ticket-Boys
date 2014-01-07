package de.fh_dortmund.ticket_system.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

public class EmailSender implements Runnable {

	private Email email;

	public EmailSender(Email email) {
		this.email = email;
	}

	@Override
	public void run() {
		try {
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

}
