package de.fh_dortmund.ticket_system.base;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class BaseView {
	/**
	 * Show a message on screen
	 * 
	 * @param message
	 *            FacesMessage
	 */
	protected void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Show a message on screen
	 * 
	 * @param summary
	 *            short Summary
	 * 
	 * @param detail
	 *            more details
	 */
	protected void addMessage(String summary, String detail) {
		FacesMessage msg = new FacesMessage(summary, detail);

		addMessage(msg);
	}

	/**
	 * Show a message on screen
	 * 
	 * @param summary
	 *            short Summary
	 * 
	 */
	protected void addMessage(String summary) {
		addMessage(summary, summary);
	}
}
