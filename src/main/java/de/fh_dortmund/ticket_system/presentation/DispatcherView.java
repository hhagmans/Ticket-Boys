package de.fh_dortmund.ticket_system.presentation;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Shift;

@ManagedBean
@SessionScoped
public class DispatcherView
{
	@ManagedProperty("#{shiftData}")
	ShiftData	shiftData;

	@ManagedProperty("#{employeeData}")
	EmployeeData			employeeData;

	private List<Shift>	selectedShifts;

	public void switchShifts()
	{
		if (getSelectedShifts().size() != 2)
			// TODO Exceptionhandling
			return;

		Shift shift0 = getSelectedShifts().get(0);
		Shift shift1 = getSelectedShifts().get(1);

		if (shift0 == null || shift1 == null)
			// TODO Exceptionhandling
			return;

		Shift tempShift0 = new Shift(shift0.getYearWeekCombi(), shift0.getWeekNumber(), shift0.getDispatcherName(),
				shift0.getSubstituteName());

		shift0.setDispatcherName(shift1.getDispatcherName());
		shift1.setDispatcherName(tempShift0.getDispatcherName());
	}


	public List<Shift> getSelectedShifts()
	{
		return selectedShifts;
	}

	public void setSelectedShifts(List<Shift> selectedShifts)
	{
		this.selectedShifts = selectedShifts;
	}


	public ShiftData getShiftData() {
		return shiftData;
	}


	public void setShiftData(ShiftData shiftData) {
		this.shiftData = shiftData;
	}


	public EmployeeData getEmployeeData() {
		return employeeData;
	}


	public void setEmployeeData(EmployeeData employeeData) {
		this.employeeData = employeeData;
	}
	
	

}
