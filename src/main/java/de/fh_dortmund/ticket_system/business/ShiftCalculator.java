package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.commons.mail.EmailException;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;
import de.fh_dortmund.ticket_system.util.EmailUtil;

/**
 * Meant to be the Dispatcher-List generating Class. Later with score and shit.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ApplicationScoped
public class ShiftCalculator implements Serializable
{
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
	 * Generates and returns a {@link List} of {@link Shift}s from the given list of dispatchers (
	 * {@link Employee}s where role = {@link Role#dispatcher}).
	 * 
	 * @param dispatchers list of employees where role = dispatcher
	 * @return generated list of shifts
	 */
	public List<Shift> generateShiftList(List<Employee> testDispatchers)
	{
		cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		//TODO: Switch input from test to production
		//		List<Employee> dispatchers = employeeData.findAllEmployeesByRole(Role.dispatcher);
		List<Employee> dispatchers = testDispatchers;

		/*
		 * Compute number of shifts to calculate: for the current week (+1), for
		 * the upcoming weeks this year and the next year.
		 */
		int nshifts = CYCLES_TO_BE_GENERATED * dispatchers.size();

		List<Shift> shifts = new ArrayList<Shift>(nshifts);

		int size = dispatchers.size();
		for (int i = 0; i < nshifts; i++)
		{
			Employee dispatcher = dispatchers.get(i % size);

			Employee representative = dispatchers.get(getIndexForRepresentative(i, size));

			Shift shift = new Shift(year, week, dispatcher, representative);

			int o = i;
			while (!conflict.checkShift(shift))
			{

				o++;
				if (o == i)
				{
					shift.setDispatcher(dispatchers.get(i));
					conflict.generateConflictFor(shift.getDispatcher(), shift.getWeek());
					break;
				}
				if (o == dispatchers.size())
				{
					o = 0;
				}
				shift.setDispatcher(dispatchers.get(o));
			}
			shifts.add(shift);

			if (++week > WEEKS_IN_A_YEAR)
			{
				year++;
				week = 1;
			}
		}

		return shifts;
	}

	public ShiftCalculator()
	{
	}

	public ConflictFinder getConflict()
	{
		return conflict;
	}

	public void setConflict(ConflictFinder conflict)
	{
		this.conflict = conflict;
	}

	public Shift generateNextShift()
	{

		List<Employee> dispatchers = employeeData.findAllEmployeesByRole(Role.dispatcher);

		Shift latestShift = shiftData.findLatestShift();
		Week week = latestShift.getWeek();

		int size = dispatchers.size();
		int indexOfLastDispatcher = dispatchers.indexOf(latestShift.getDispatcher());
		indexOfLastDispatcher++;
		if ((indexOfLastDispatcher < 0) || (indexOfLastDispatcher >= size))
		{
			indexOfLastDispatcher = 0;
		}

		int year = week.getYear();
		int weekNumber = week.getWeekNumber();

		weekNumber++;
		if (weekNumber > WEEKS_IN_A_YEAR)
		{
			weekNumber = 0;
			year++;
		}

		Week nextWeek = new Week(year, weekNumber);

		Shift shift = new Shift(nextWeek, dispatchers.get(indexOfLastDispatcher),
			dispatchers.get(getIndexForRepresentative(indexOfLastDispatcher, size)));

		return shift;
	}

	public int getIndexForRepresentative(int indexOfDispatcher, int amountOfDispatchers)
	{
		return ((indexOfDispatcher + (amountOfDispatchers / 2)) % amountOfDispatchers);
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
}
