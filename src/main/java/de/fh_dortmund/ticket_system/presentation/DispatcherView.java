package de.fh_dortmund.ticket_system.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.entity.Employees;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.ShiftCalculator;

@ManagedBean
@ViewScoped
public class DispatcherView
{
	@ManagedProperty("#{auth}")
	Authentication	auth;

	@ManagedProperty("#{employees}")
	Employees		employees;

	@ManagedProperty("#{shiftCalculator}")
	ShiftCalculator	shifts;

	private Shift	shift1;
	private Shift	shift2;

	public void switchShifts(Shift shift1, Shift shift2)
	{

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
}
