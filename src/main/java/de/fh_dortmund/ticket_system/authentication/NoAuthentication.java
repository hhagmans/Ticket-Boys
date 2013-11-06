package de.fh_dortmund.ticket_system.authentication;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "auth")
@SessionScoped
public class NoAuthentication extends Authentication
{
	private static final long	serialVersionUID	= -1084126377607196446L;

	@Override
	protected boolean authenticate(String name, String passwort)
	{
		return true;
	}

}
