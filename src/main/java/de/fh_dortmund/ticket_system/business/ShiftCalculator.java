package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;

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

	private static final int CYCLES_TO_BE_GENERATED = 3;

	private static final int WEEKS_IN_A_YEAR = 52;

	/**
	 * Generates and returns a {@link List} of {@link Shift}s from the given list of dispatchers (
	 * {@link Employee}s where role = {@link Role#dispatcher}).
	 * 
	 * @param dispatchers list of employees where role = dispatcher
	 * @return generated list of shifts
	 */
	public List<Shift> generateShiftList(List<Employee> dispatchers)
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

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

			Employee representative = i == 0 ? dispatchers.get(size - 1) : dispatchers.get((i - 1) % size);

			Shift shift = new Shift(year, week, dispatcher, representative);
			if (conflict.checkShift(shift))
			{
				shifts.add(shift);

				cal.clear();
				cal.set(Calendar.WEEK_OF_YEAR, week);
				cal.set(Calendar.YEAR, year);
				Date startDate = cal.getTime();
				cal.set(Calendar.WEEK_OF_YEAR, week + 1);
				cal.add(Calendar.DAY_OF_MONTH, -1);
				Date endDate = cal.getTime();

				//dispatcher.addEvent(new Event("Dispatcher-Schicht", startDate, endDate, EventType.dispatcher));
			}

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
}
