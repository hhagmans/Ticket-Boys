package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.ConflictData;
import de.fh_dortmund.ticket_system.business.ConflictModel;
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

	private List<Conflict> selectedConflicts;

	private List<Conflict> conflicts;

	private ConflictModel conflictModel;

	public List<Conflict> getUnsolvedConflictsForCurrentUser() {
		Employee currentUser = auth.getEmployee();
		List<Conflict> conflicts = conflictData.findByUser(currentUser);
		List<Conflict> unsolvedConflicts = conflictData.findByUser(currentUser);

		return unsolvedConflicts;
	}

	public ConflictData getConflictData() {
		return conflictData;
	}

	public void setConflictData(ConflictData conflictData) {
		this.conflictData = conflictData;
	}

	public void solveConflicts() {

		List<Conflict> conflicts = getSelectedConflicts();

		for (Conflict conflict : conflicts) {
			if (conflict.getSolved()) {
				return;

			} else {
				conflict.setSolved(true);
				conflict.setName("GELÖST: " + conflict.getName());
				conflictData.update(conflict);
			}
		}

	}

	public Authentication getAuth() {
		return auth;
	}

	public void setAuth(Authentication auth) {
		this.auth = auth;
	}

	public List<Conflict> getSelectedConflicts() {
		return selectedConflicts;
	}

	public void setSelectedConflicts(List<Conflict> selectedConflicts) {
		this.selectedConflicts = selectedConflicts;
	}

	public List<Conflict> getConflicts() {
		return getUnsolvedConflictsForCurrentUser();
	}

	public void setConflicts(List<Conflict> conflicts) {
		this.conflicts = conflicts;
	}

	public ConflictModel getConflictModel() {
		if (conflictModel == null)
			setConflictModel(new ConflictModel(
					getUnsolvedConflictsForCurrentUser()));

		return conflictModel;
	}

	public void setConflictModel(ConflictModel conflictModel) {
		this.conflictModel = conflictModel;
	}

}
