package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.ConflictData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

@ManagedBean
@ViewScoped
public class ConflictView implements Serializable
{

	@ManagedProperty("#{conflictData}")
	private ConflictData conflictData;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	public List<Conflict> getConflictsForCurrentUser()
	{
		Employee currentUser = auth.getEmployee();
		List<Conflict> conflicts = conflictData.findByEmployee(currentUser);

		return conflicts;
	}

	public ConflictData getConflictData()
	{
		return conflictData;
	}

	public void setConflictData(ConflictData conflictData)
	{
		this.conflictData = conflictData;
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
