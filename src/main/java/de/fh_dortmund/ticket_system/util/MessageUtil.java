package de.fh_dortmund.ticket_system.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageUtil
{
	protected MessageUtil()
	{
	}

	public static void showW(String msg)
	{
		show(msg, FacesMessage.SEVERITY_WARN);
	}

	public static void showI(String msg)
	{
		show(msg, FacesMessage.SEVERITY_INFO);
	}

	public static void showE(String msg)
	{
		show(msg, FacesMessage.SEVERITY_ERROR);
	}

	public static void showF(String msg)
	{
		show(msg, FacesMessage.SEVERITY_FATAL);
	}

	public static void show(String msg, Severity severity)
	{
		FacesMessage fmsg = new FacesMessage(msg, "ERROR MSG");
		fmsg.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}
}
