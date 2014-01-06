package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.ConflictData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

//@ManagedBean
//@ViewScoped
public class ConflictView implements Serializable
{

	private static final long serialVersionUID = -6264092603228857048L;

	@ManagedProperty("#{conflictData}")
	private ConflictData conflictData;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	private List<Conflict> selectedConflicts;

	private List<Conflict> conflicts;

	public List<Conflict> getConflictsForCurrentUser()
	{
		Employee currentUser = auth.getEmployee();
		List<Conflict> conflicts = conflictData.getDao().findByUser(currentUser);

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

	public void solveConflicts()
	{
		if (selectedConflicts.size() == 0)
		{
			return;
		}

		for (Conflict c : selectedConflicts)
		{
			c.setSolved(true);
		}
	}

	public Authentication getAuth()
	{
		return auth;
	}

	public void setAuth(Authentication auth)
	{
		this.auth = auth;
	}

	public List<Conflict> getSelectedConflicts()
	{
		return selectedConflicts;
	}

	public void setSelectedConflicts(List<Conflict> selectedConflicts)
	{
		this.selectedConflicts = selectedConflicts;
	}

	public List<Conflict> getConflicts()
	{
		return getConflictsForCurrentUser();
	}

	public void setConflicts(List<Conflict> conflicts)
	{
		this.conflicts = conflicts;
	}
}
