package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

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

	public void switchShifts()
	{
		Shift shift0 = getSelectedShifts().get(0);
		Shift shift1 = getSelectedShifts().get(1);
		if (!userIsAllowedToSwitchShifts(shift0, shift1))
		{
			return;
		}

		if (getSelectedShifts().size() != 2)
		{
			// TODO Exceptionhandling
			return;
		}

		if ((shift0 == null) || (shift1 == null))
		{
			// TODO Exceptionhandling
			return;
		}

		Employee dispatcherTemp = shift0.getDispatcher();

		shift0.setDispatcher(shift1.getDispatcher());
		shift1.setDispatcher(dispatcherTemp);
	}

	public boolean userIsAllowedToSwitchShifts(Shift shift1, Shift shift2)
	{
		Employee currentUser = authentication.getEmployee();

		if (currentUser.equals(shift1.getDispatcher()) || currentUser.equals(shift2.getDispatcher()))
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

}
