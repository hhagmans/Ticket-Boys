package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;
import de.fh_dortmund.ticket_system.util.DateUtil;

/**
 * Class for identifying conflicts between employee events and shifts.
 * 
 * @author Alex Hofmann
 * 
 */
@ManagedBean
@ApplicationScoped
public class ConflictFinder implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{shiftData}")
	ShiftData shiftData;

	@ManagedProperty("#{employeeData}")
	EmployeeData employeeData;

	@ManagedProperty("#{eventData}")
	EventData eventData;

	@ManagedProperty("#{conflictData}")
	private ConflictData conflictData;

	public ConflictFinder() {
	}

	/**
	 * Checks a single employee for date conflicts.
	 * 
	 * @param employee
	 * @return true if the employee has no conflicts, else false
	 */
	public boolean checkEmployee(Employee employee) {
		// Get all Weeks from employee
		Set<Week> employeesWeeks = getEmployeesWeek(employee);

		// Get all Weeks from employees shifts
		List<Shift> shifts = getShiftData().findShiftByEmployee(employee);
		Set<Week> shiftWeeks = getShiftsWeeks(shifts);

		return checkForNoConflicts(employeesWeeks, shiftWeeks);
	}

	/**
	 * Checks if two sets have any weeks in common.
	 * 
	 * @param set1
	 * @param set2
	 * @return false if the sets have at least one week in common, else true
	 */
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

	/**
	 * Checks all employees for conflicts
	 * 
	 * @return true if every employee has no conficts, else false
	 */
	public boolean checkAllEmployees() {
		for (Employee employee : getEmployeeData().findAll()) {
			if (!checkEmployee(employee)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks a single VacationEvent for conflicts with its employee.
	 * 
	 * @param eventvent
	 * @return true if the vacation has no conflict, else false
	 */
	public boolean checkVacation(Event eventvent) {
		// Get all weeks of event
		Date startDate = eventvent.getStartDate();
		Date endDate = eventvent.getEndDate();
		Set<Week> eventWeeks = DateUtil.getWeeksFromDates(startDate, endDate);

		// Get all Weeks from employees shifts
		Employee employee = eventvent.getEmployee();
		List<Shift> shifts = getShiftData().findShiftByEmployee(employee);
		Set<Week> shiftWeeks = getShiftsWeeks(shifts);

		boolean result = checkForNoConflicts(eventWeeks, shiftWeeks);

		return result;
	}

	/**
	 * Checks all vacations for conflicts.
	 * 
	 * @return true if every vacation has no conflicts, else false
	 */
	public boolean checkAllVacations() {
		for (Event event : getEventData().findAll()) {
			if (!checkVacation(event)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks a shift for conflicts.
	 * 
	 * @param shift
	 * @return true if the shift has no conflicts, else false
	 */
	public boolean checkShift(Shift shift) {
		// Get all shiftweeks
		Week shiftWeek = shift.getWeek();
		Set<Week> shiftWeeks = new HashSet<Week>();
		shiftWeeks.add(shiftWeek);

		// Get shifts employee events
		Set<Week> eventWeeks = getEmployeesWeek(employeeData.findByID(shift
				.getDispatcher().getKonzernID()));

		System.out.println(shift.getDispatcher().getKonzernID());

		for (Week week : eventWeeks) {
			System.out.println(week.getUniqueRowKey());
		}

		boolean result = checkForNoConflicts(shiftWeeks, eventWeeks);

		return result;
	}

	/**
	 * Checks all the shifts for conflicts.
	 * 
	 * @return true if every shift has no conflicts, else false
	 */
	public boolean checkAllShifts() {
		for (Shift shift : getShiftData().findAll()) {
			if (!checkShift(shift)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a set of weeks of a given shift list.
	 * 
	 * @param shifts
	 * @return a set of weeks
	 */
	protected Set<Week> getShiftsWeeks(List<Shift> shifts) {
		Set<Week> kws = new HashSet<Week>();
		for (Shift shift : shifts) {
			kws.add(shift.getWeek());
		}
		return kws;
	}

	/**
	 * Returns a set of weeks of a given employee.
	 * 
	 * @param employee
	 * @return a set of weeks
	 */
	protected Set<Week> getEmployeesWeek(Employee employee) {
		Set<Week> employeesWeeks = new HashSet<Week>();
		Set<Event> events = employee.getMyEvents();
		if (events == null || events.size() == 0) {
			try {
				events = new HashSet<Event>(getEventData().findByUser(employee));
			} catch (NullPointerException n) {
				return new HashSet<Week>();
			}
			if (events.size() == 0) {
				return new HashSet<Week>();
			}
		}

		for (Event vacationEvent : events) {
			Date startDate = vacationEvent.getStartDate();
			Date endDate = vacationEvent.getEndDate();
			employeesWeeks.addAll(DateUtil
					.getWeeksFromDates(startDate, endDate));
		}

		return employeesWeeks;
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

	public EventData getEventData() {
		return eventData;
	}

	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}

	/**
	 * Generates conflict object for an employee and an event.
	 * 
	 * @param employee
	 * @param event
	 */
	public void generateConflictFor(Employee employee, Event event) {
		Set<Week> eventWeeks = DateUtil.getWeeksFromDates(event.getStartDate(),
				event.getEndDate());
		generateConflictFor(employee, eventWeeks);
	}

	public void generateConflictFor(Employee employee, Week week) {
		HashSet<Week> weeks = new HashSet<Week>();
		weeks.add(week);
		generateConflictFor(employee, weeks);
	}

	public void generateConflictFor(Employee employee, Set<Week> weeks) {
		Set<Event> employeeEvents = employee.getMyEvents();
		Set<Week> employeeWeeks = new HashSet<Week>();
		for (Event e : employeeEvents) {
			employeeWeeks.addAll(DateUtil.getWeeksFromDates(e.getStartDate(),
					e.getEndDate()));
		}

		List<Conflict> conflicts = getAllConflicts(employee, employeeWeeks,
				weeks);

		getConflictData().add(conflicts);
	}

	/**
	 * Compares the two given sets for conflicts and generates a list.
	 * 
	 * @param set1
	 * @param set2
	 * @return returns a list of conflicts between the two given sets.
	 */
	public List<Conflict> getAllConflicts(Employee employee, Set<Week> set1,
			Set<Week> set2) {
		List<Conflict> conflicts = new ArrayList<Conflict>();

		for (Week kw : set1) {
			if (set2.contains(kw)) {
				Conflict conflict = new Conflict();
				conflict.setWeek(kw);
				conflict.setSolved(false);
				conflict.setEmployee(employee);

				conflicts.add(conflict);
			}
		}
		return conflicts;
	}

	public ConflictData getConflictData() {
		return conflictData;
	}

	public void setConflictData(ConflictData conflictData) {
		this.conflictData = conflictData;
	}

}