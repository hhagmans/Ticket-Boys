package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.ConflictData;
import de.fh_dortmund.ticket_system.business.ConflictModel;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

@ManagedBean
@ViewScoped
public class ConflictView implements Serializable, Converter {

	private static final long serialVersionUID = -6264092603228857048L;

	@ManagedProperty("#{conflictData}")
	private ConflictData conflictData;

	@ManagedProperty("#{auth}")
	private Authentication auth;

	private Conflict selectedConflict;

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

	public Conflict getSelectedConflict() {
		return selectedConflict;
	}

	public void setSelectedConflict(Conflict selectedConflict) {
		this.selectedConflict = selectedConflict;
	}

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {
		if (submittedValue.trim().equals("")) {
			return null;
		} else {
			try {
				String name = submittedValue;

				ConflictData conflictDataclass = (ConflictData) facesContext
						.getApplication()
						.getELResolver()
						.getValue(facesContext.getELContext(), null,
								"conflictData");

				for (Conflict c : conflictDataclass.findAll()) {
					if (c.getName() == name) {
						return c;
					}
				}

			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid conflict"));
			}
		}

		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Conflict) value).getName());
		}
	}

}
