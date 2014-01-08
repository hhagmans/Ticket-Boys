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
public class ConflictView implements Serializable {

	private static final long serialVersionUID = -6264092603228857048L;

	@ManagedProperty("#{conflictData}")
	private ConflictData conflictData;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	private Conflict selectedConflict;

	private List<Conflict> conflicts;

	public List<Conflict> getUnsolvedConflictsForCurrentUser() {
		Employee currentUser = auth.getEmployee();
		List<Conflict> conflicts = conflictData.findByUser(currentUser);
		List<Conflict> unsolvedConflicts = conflictData.findByUser(currentUser);
		for (Conflict c : conflicts) {
			if (!c.getSolved()) {
				unsolvedConflicts.add(c);
			}
		}

		return unsolvedConflicts;
	}

	public ConflictData getConflictData() {
		return conflictData;
	}

	public void setConflictData(ConflictData conflictData) {
		this.conflictData = conflictData;
	}

	public void solveConflicts() {

		Conflict conflict = getSelectedConflict();
		if (conflict == null) {
			return;
		}

		if (conflict.getSolved()) {
			return;
		} else {
			System.out.println("Conflict Name: " + conflict.getName()
					+ " solved: " + conflict.getSolved());
			conflict.setSolved(true);
			conflict.setName("GELÖST: " + conflict.getName());
			conflictData.update(conflict);
		}

	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public List<Conflict> getConflicts() {
		return getUnsolvedConflictsForCurrentUser();
	}

	public void setConflicts(List<Conflict> conflicts) {
		this.conflicts = conflicts;
	}

	public Conflict getSelectedConflict() {
		return selectedConflict;
	}

	public void setSelectedConflict(Conflict selectedConflict) {
		this.selectedConflict = selectedConflict;
	}

}
