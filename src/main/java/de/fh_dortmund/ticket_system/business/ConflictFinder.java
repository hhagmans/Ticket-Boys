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

import org.apache.commons.mail.EmailException;

import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;
import de.fh_dortmund.ticket_system.util.DateUtil;
import de.fh_dortmund.ticket_system.util.EmailUtil;

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
		if ((events == null) || (events.size() == 0)) {
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
		try {
			EmailUtil
					.sendConfEmail(
							"Hallo "
									+ employee.getFullName()
									+ ",\n\nBei der Planung "
									+ "\""
									+ event.getTitle()
									+ "\""
									+ " vom "
									+ event.getStartDate()
									+ " bis "
									+ event.getEndDate()
									+ " ist ein Konflikt aufgetreten. Bitte beheben Sie diesen.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem",
							employee.getEmail());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		generateConflictFor(employee, eventWeeks);
	}

	public void generateConflictFor(Employee employee, Week week) {
		HashSet<Week> weeks = new HashSet<Week>();
		weeks.add(week);

		generateConflictFor(employee, weeks);

		try {
			EmailUtil
					.sendConfEmail(
							"Hallo "
									+ employee.getFullName()
									+ ",\n\nBei der Einsatzplanung ist in KW "
									+ week.getWeekNumber()
									+ " ein Konflikt aufgetreten. Bitte beheben Sie diesen.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem",
							employee.getEmail());
		} catch (EmailException e) {
			e.printStackTrace();
		}

	}

	public boolean isConflictInList(Conflict conflict, List<Conflict> conflicts) {
		for (Conflict conflict2 : conflicts) {
			if (conflict.getEmployee().equals(conflict2.getEmployee())
					&& conflict.getWeek().equals(conflict2.getWeek())) {
				return true;
			}
		}
		return false;
	}

	public int getIndexOfConflict(Conflict conflict, List<Conflict> conflicts) {
		for (int i = 0; i < conflicts.size(); i++) {
			if (conflict.getEmployee().equals(conflicts.get(i).getEmployee())
					&& conflict.getWeek().equals(conflicts.get(i).getWeek())) {
				return i;
			}
		}
		return -1;
	}

	public void generateConflictFor(Employee employee, Set<Week> weeks) {
		Set<Week> employeeWeeks = getEmployeesWeek(employee);

		List<Conflict> conflicts = getAllConflicts(employee, employeeWeeks,
				weeks);

		// Filter conflict doubles

		List<Conflict> allConflicts = getConflictData().findAll();

		List<Conflict> filteredConflicts = new ArrayList<Conflict>();

		for (Conflict conflict : conflicts) {
			if (isConflictInList(conflict, allConflicts)) {
				int index = getIndexOfConflict(conflict, allConflicts);
				if (index >= 0 && allConflicts.get(index).getSolved()) {
					filteredConflicts.add(conflict);
				}
			} else {
				filteredConflicts.add(conflict);
			}
		}

		if (filteredConflicts.size() > 0)
			getConflictData().add(filteredConflicts);
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
				Conflict conflict = new Conflict(employee, kw);

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