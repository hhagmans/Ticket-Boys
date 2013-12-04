package de.fh_dortmund.ticket_system.base;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class BaseView
{
	protected void addMessage(FacesMessage message)
	{
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	protected void addMessage(String summary, String detail)
	{
		FacesMessage msg = new FacesMessage(summary, detail);

		addMessage(msg);
	}

	protected void addMessage(String summary)
	{
		addMessage(summary, summary);
	}
}
