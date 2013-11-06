package de.fh_dortmund.ticket_system.presentation;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.Employees;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.ShiftsData;

@ManagedBean
@ViewScoped
public class DispatcherView
{
	@ManagedProperty("#{shiftCalculator}")
	ShiftsData	shifts;

	@ManagedProperty("#{employees}")
	Employees		employees;

	private Shift	shift1;
	private Shift	shift2;

	private List<Shift> selectedShifts;

	public void switchShifts()
	{
		if(getSelectedShifts().size()!=2)
			//TODO Exceptionhandling
			return;
		
		Shift shift0 = getSelectedShifts().get(0);
		Shift shift1 = getSelectedShifts().get(1);
		
		if(shift0 ==null || shift1==null)
			//TODO Exceptionhandling
			return;
		
		Shift tempShift0 = new Shift(shift0.getYearWeekCombi(), shift0.getWeekNumber(), shift0.getDispatcherName(), shift0.getSubstituteName());
		
		shift0.setDispatcherName(shift1.getDispatcherName());
		shift1.setDispatcherName(tempShift0.getDispatcherName());
	}

	public Shift getShift1()
	{
		return shift1;
	}

	public void setShift1(Shift shift1)
	{
		this.shift1 = shift1;
	}

	public Shift getShift2()
	{
		return shift2;
	}

	public void setShift2(Shift shift2)
	{
		this.shift2 = shift2;
	}

	public List<Shift> getSelectedShifts() {
		return selectedShifts;
	}

	public void setSelectedShifts(List<Shift> selectedShifts) {
		this.selectedShifts = selectedShifts;
	}
}
