package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.persistence.ShiftDAO;
import de.fh_dortmund.ticket_system.persistence.ShiftDAOsqlLite;

/**
 * Die View zur Dispatcherliste
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@SessionScoped
public class DispatcherView implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{auth}")
	Authentication authentication;

	@ManagedProperty("#{shiftData}")
	ShiftData shiftData;

	@ManagedProperty("#{employeeData}")
	EmployeeData employeeData;

	private List<Shift> selectedShifts;

	private ShiftDAO shiftDAO = new ShiftDAOsqlLite();

	public void switchShifts()
	{
		if (getSelectedShifts().size() > 2)
		{
			showMessage("Verweigert", "Sie haben mehr als 2 Schichten ausgewählt!");
			return;
		}
		else if (getSelectedShifts().size() < 2)
		{
			showMessage("Verweigert", "Sie haben weniger als 2 Schichten ausgewählt!");
			return;
		}

		Shift shift0 = getSelectedShifts().get(0);
		Shift shift1 = getSelectedShifts().get(1);

		if ((shift0 == null) || (shift1 == null))
		{
			showMessage("Verweigert", "Sie haben nicht (mehr) vorhandene Schichten zum tauschen gewählt!");
			return;
		}

		if (!userIsAllowedToSwitchShifts(shift0, shift1))
		{
			showMessage("Verweigert", "Sie haben nicht die Berechtigung diese Schichten zu tauschen!");
			return;
		}

		Shift tempShift0 = new Shift(shift0.getYear(), shift0.getWeekNumber(), shift0.getDispatcher(),
			shift0.getSubstitutioner());

		shift0.setDispatcher(shift1.getDispatcher());
		shift1.setDispatcher(tempShift0.getDispatcher());

		updateShifts(shift0);
		updateShifts(shift1);

		showMessage("Erfolg!", "Die Dispatcher der KW " + shift1.getWeekNumber() + " & " + shift0.getWeekNumber()
			+ " wurden getauscht!");

	}

	private void updateShifts(Shift shift0)
	{
		shiftDAO.updateShift(shift0);
	}

	public boolean userIsAllowedToSwitchShifts(Shift shift1, Shift shift2)
	{
		Employee currentUser = authentication.getEmployee();

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

	public List<Shift> getSelectedShifts()
	{
		return selectedShifts;
	}

	public void setSelectedShifts(List<Shift> selectedShifts)
	{
		this.selectedShifts = selectedShifts;
	}

	public ShiftData getShiftData()
	{
		return shiftData;
	}

	public void setShiftData(ShiftData shiftData)
	{
		this.shiftData = shiftData;
	}

	public EmployeeData getEmployeeData()
	{
		return employeeData;
	}

	public void setEmployeeData(EmployeeData employeeData)
	{
		this.employeeData = employeeData;
	}

	public Authentication getAuthentication()
	{
		return authentication;
	}

	public void setAuthentication(Authentication authentication)
	{
		this.authentication = authentication;
	}

	public void showMessage(String summary, String detail)
	{
		FacesMessage msg = new FacesMessage(summary, detail);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
