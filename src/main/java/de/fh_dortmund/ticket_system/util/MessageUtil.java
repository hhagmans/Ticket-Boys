package de.fh_dortmund.ticket_system.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageUtil
{
	protected MessageUtil()
	{
	}

	public static void show(String msg, Severity severity)
	{
		FacesMessage fmsg = new FacesMessage("msg", "ERROR MSG");
		fmsg.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

	}
}
