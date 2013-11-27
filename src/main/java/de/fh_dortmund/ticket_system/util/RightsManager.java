package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;

@ManagedBean
@SessionScoped
public class RightsManager implements Serializable
{
	private static final long	serialVersionUID	= 3813512294586726838L;

	@ManagedProperty("#{auth}")
	private Authentication		auth;

	public boolean userIsAllowedToSwitchShifts(Shift shift1, Shift shift2)
	{
		Employee currentUser = getAuth().getEmployee();

		if (currentUser.getRole() != Role.admin)
		{
			if (currentUser.equals(shift1.getDispatcher()) || currentUser.equals(shift2.getDispatcher()))
			{
				return true;
			}
		}
		else
		{
			return true;
		}

		return false;
	}
	
	public Authentication getAuth()
	{
		return auth;
	}

	public void setAuth(Authentication auth)
	{
		this.auth = auth;
	}
}
