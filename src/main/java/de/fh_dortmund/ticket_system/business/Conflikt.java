package de.fh_dortmund.ticket_system.business;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.base.Week;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.util.DateUtil;

@ManagedBean
@ApplicationScoped
public class Conflikt {
	@ManagedProperty("#shiftData")
	ShiftData shiftData;

	@ManagedProperty("#employeeData")
	EmployeeData employeeData;

	@ManagedProperty("#vacationData")
	VacationData vacationData;

	public Conflikt() {
	}

	public boolean checkEmployee(Employee employee) {
		// Get all Weeks from employee
		Set<Week> employeesWeeks = getEmployeesWeek(employee);

		// Get all Weeks from employees shifts
		List<Shift> shifts = shiftData.findShiftByEmployee(employee);
		Set<Week> shiftWeeks = getShiftsWeeks(shifts);

		return checkForNoConflicts(employeesWeeks, shiftWeeks);
	}

	public boolean checkForNoConflicts(Set<Week> set1, Set<Week> set2) {
		// Check for conflikts
		boolean ok = true;
		for (Week kw : set1) {
			if (set2.contains(kw)) {
				ok = false;
				break;
			}
		}

		return ok;
	}

	public boolean checkAllEmployees() {
		for (Employee employee : employeeData.findAll()) {
			if (checkEmployee(employee))
				return false;
		}

		return true;
	}

	public boolean checkVacation(VacationEvent vacationEvent) {
		// Get all weeks of event
		Date startDate = vacationEvent.getStartDate();
		Date endDate = vacationEvent.getEndDate();
		Set<Week> eventWeeks = DateUtil.getWeeksFromDates(startDate, endDate);

		// Get all Weeks from employees shifts
		Employee employee = vacationEvent.getEmployee();
		List<Shift> shifts = shiftData.findShiftByEmployee(employee);
		Set<Week> shiftWeeks = getShiftsWeeks(shifts);

		boolean result = checkForNoConflicts(eventWeeks, shiftWeeks);

		return result;
	}

	public boolean checkAllVacations() {
		for (VacationEvent event : vacationData.findAll()) {
			if (checkVacation(event))
				return false;
		}
		return true;
	}

	public boolean checkShift(Shift shift) {
		// Get all shiftweeks
		Week shiftWeek =shift.getWeek();
		Set<Week> shiftWeeks = new HashSet<Week>();
		shiftWeeks.add(shiftWeek);

		// Get shifts employee events
		Set<Week> eventWeeks = getEmployeesWeek(shift.getDispatcher());

		boolean result = checkForNoConflicts(shiftWeeks, eventWeeks);

		return result;
	}

	public boolean checkAllShifts() {
		for (Shift shift : shiftData.findAll()) {
			if (checkShift(shift))
				return false;
		}

		return true;
	}

	public Set<Week> getShiftsWeeks(List<Shift> shifts) {
		Set<Week> kws = new HashSet<Week>();
		for (Shift shift : shifts) {
			kws.add(shift.getWeek());
		}
		return kws;
	}

	public Set<Week> getEmployeesWeek(Employee employee) {
		Set<Week> employeesWeeks = new HashSet<Week>();
		Set<VacationEvent> vacs = employee.getMyEvents();
		for (VacationEvent vacationEvent : vacs) {
			Date startDate = vacationEvent.getStartDate();
			Date endDate = vacationEvent.getEndDate();
			employeesWeeks.addAll(DateUtil
					.getWeeksFromDates(startDate, endDate));
		}

		return employeesWeeks;
	}
}
