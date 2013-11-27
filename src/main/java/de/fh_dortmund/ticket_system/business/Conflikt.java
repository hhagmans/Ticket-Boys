package de.fh_dortmund.ticket_system.business;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.entity.Employee;

@ManagedBean
@ApplicationScoped
public class Conflikt
{
	@ManagedProperty("#shiftData")
	ShiftData		shiftData;

	@ManagedProperty("#employeeData")
	EmployeeData	employeeData;

	@ManagedProperty("#vacationData")
	VacationData	vacationData;

	public boolean checkEmployee(Employee empployee)
	{

		return false;
	}
}
