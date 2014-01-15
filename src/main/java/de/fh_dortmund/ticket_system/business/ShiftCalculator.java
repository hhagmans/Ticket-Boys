package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;

/**
 * Meant to be the Dispatcher-List generating Class.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ApplicationScoped
public class ShiftCalculator implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{conflict}")
	private ConflictFinder conflict;

	@ManagedProperty("#{shiftData}")
	private ShiftData shiftData;

	@ManagedProperty("#{employeeData}")
	private EmployeeData employeeData;

	private static final int CYCLES_TO_BE_GENERATED = 3;

	private static final int WEEKS_IN_A_YEAR = 52;

	private Calendar cal;

	/**
	 * Generates and returns a {@link List} of {@link Shift}s from the given
	 * list of dispatchers ( {@link Employee}s where role =
	 * {@link Role#dispatcher}).
	 * 
	 * @param dispatchers
	 *            list of employees where role = dispatcher
	 * @return generated list of shifts
	 */
	public List<Shift> generateShiftList(List<Employee> testDispatchers) {
		cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		// TODO: Switch input from test to production
		// List<Employee> dispatchers =
		// employeeData.findAllEmployeesByRole(Role.dispatcher);
		List<Employee> dispatchers = testDispatchers;

		/*
		 * Compute number of shifts to calculate: for the current week (+1), for
		 * the upcoming weeks this year and the next year.
		 */
		int nshifts = CYCLES_TO_BE_GENERATED * dispatchers.size();

		List<Shift> shifts = new ArrayList<Shift>(nshifts);

		int size = dispatchers.size();
		Shift shift = null;
		for (int i = 0; i < nshifts; i++) {
			Employee lastDispatcher;
			if (shift == null) {
				lastDispatcher = dispatchers.get(0);
			} else {
				lastDispatcher = shift.getDispatcher();
			}

			Employee representative = dispatchers
					.get(getIndexForRepresentative(i, size));

			shift = new Shift(year, week, lastDispatcher, representative);

			shift = getNextShift(dispatchers, shift, lastDispatcher);

			shifts.add(shift);

			if (++week > WEEKS_IN_A_YEAR) {
				year++;
				week = 1;
			}
		}

		return shifts;
	}

	public ShiftCalculator() {
	}

	public ConflictFinder getConflict() {
		return conflict;
	}

	public void setConflict(ConflictFinder conflict) {
		this.conflict = conflict;
	}

	/**
	 * Returns the next dispatcher ({@link Employee}) given a list of all
	 * dispatchers ( {@link Employee}s considering the actual score
	 * 
	 * @param dispatcherList
	 *            list of employees
	 * 
	 * @param shift
	 *            current shift
	 * 
	 * @param lastDispatcher
	 *            last Dispatcher
	 * 
	 * @return next shift
	 */
	public Shift getNextShift(List<Employee> dispatcherList, Shift shift,
			Employee lastDispatcher) {
		int startIndex;

		List<Employee> tempList = new ArrayList<Employee>(dispatcherList);

		tempList = removeAllConflictingEmployees(tempList, shift);

		if (tempList.indexOf(lastDispatcher) == tempList.size() - 1) {
			startIndex = 0;
		} else if (tempList.indexOf(lastDispatcher) >= 0) {
			startIndex = tempList.indexOf(lastDispatcher) + 1;
		} else {
			startIndex = 0;
		}

		shift.setDispatcher(tempList.get(startIndex));

		for (int i = startIndex; i < tempList.size(); i++) {
			shift = checkScoreAndSetNewDispatcher(tempList, shift, i);
		}

		for (int i = 0; i < startIndex; i++) {
			shift = checkScoreAndSetNewDispatcher(tempList, shift, i);
		}

		return shift;
	}

	/**
	 * Returns a list of all dispatchers ( {@link Employee}s without conflicts
	 * given a list of all dispatchers ( {@link Employee}s
	 * 
	 * @param empList
	 *            list of dispatchers
	 * 
	 * @param shift
	 *            current shift
	 * 
	 * @return List of Employees without conflicts
	 */
	public List<Employee> removeAllConflictingEmployees(List<Employee> empList,
			Shift shift) {
		Iterator<Employee> iterator = empList.iterator();
		List<Employee> newList = new ArrayList<Employee>();
		while (iterator.hasNext()) {
			Employee employee = iterator.next();
			shift.setDispatcher(employee);
			if (conflict.checkShift(shift)) {
				newList.add(employee);
			}
		}

		return newList;
	}

	/**
	 * Returns the shift (@link Shift) with the given dispatcher (
	 * {@link Employee}) if his score is lower than the previously set
	 * dispatcher. Else it will return the last shift.
	 * 
	 * @param dispatcherList
	 *            list of employees
	 * 
	 * @param shift
	 *            current shift
	 * 
	 * @param index
	 *            index of curretn Dispatcher in list
	 * 
	 * @returns hift (@link Shift) with the given dispatcher ({@link Employee})
	 *          if his score is lower than the previously set dispatcher. Else
	 *          it will return the last shift.
	 */
	public Shift checkScoreAndSetNewDispatcher(List<Employee> dispatcherList,
			Shift shift, int index) {
		if (dispatcherList.get(index).getScore() < shift.getDispatcher()
				.getScore()) {
			shift.setDispatcher(dispatcherList.get(index));
		}
		return shift;
	}

	/**
	 * Returns the next dispatcher ({@link Employee}) given a list of all
	 * dispatchers ( {@link Employee} without considering the actual score.
	 * 
	 * 
	 * @return next shift
	 */
	public Shift generateNextShift() {

		List<Employee> dispatchers = employeeData
				.findAllEmployeesByRole(Role.dispatcher);

		Shift latestShift = shiftData.findLatestShift();
		Week week = latestShift.getWeek();

		int size = dispatchers.size();
		int indexOfLastDispatcher = dispatchers.indexOf(latestShift
				.getDispatcher());
		indexOfLastDispatcher++;
		if ((indexOfLastDispatcher < 0) || (indexOfLastDispatcher >= size)) {
			indexOfLastDispatcher = 0;
		}

		int year = week.getYear();
		int weekNumber = week.getWeekNumber();

		weekNumber++;
		if (weekNumber > WEEKS_IN_A_YEAR) {
			weekNumber = 0;
			year++;
		}

		Week nextWeek = new Week(year, weekNumber);

		Shift shift = new Shift(nextWeek,
				dispatchers.get(indexOfLastDispatcher),
				dispatchers.get(getIndexForRepresentative(
						indexOfLastDispatcher, size)));

		return shift;
	}

	/**
	 * Returns the index for the representative
	 * 
	 * 
	 * @return index of representative
	 */
	public int getIndexForRepresentative(int indexOfDispatcher,
			int amountOfDispatchers) {
		return ((indexOfDispatcher + (amountOfDispatchers / 2)) % amountOfDispatchers);
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
