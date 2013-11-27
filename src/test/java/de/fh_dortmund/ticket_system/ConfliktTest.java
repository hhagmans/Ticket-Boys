package de.fh_dortmund.ticket_system;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.ticket_system.business.Conflikt;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

public class ConfliktTest
{
	Conflikt	conflikt;

	@Before
	public void setUp() throws Exception
	{
		conflikt = new Conflikt();
	}

	@Test
	public void checkGetKwFromDateWithCorrectDates()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2011, 8, 12);
		Date startDate = cal.getTime();

		cal.set(2012, 4, 13);
		Date endDate = cal.getTime();

		Set<Conflikt.KW> count = conflikt.getKwFromDates(startDate, endDate);

		// 12 August 2011 -> KW 35
		// 13 April 2012 -> KW 15
		// (52-35) + 15 = 35
		assertEquals(35, count.size());
	}

	@Test
	public void checkGetKwFromDateWithNotCorrectDates()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2012, 8, 12);
		Date startDate = cal.getTime();

		cal.set(2011, 4, 13);
		Date endDate = cal.getTime();

		Set<Conflikt.KW> count = conflikt.getKwFromDates(startDate, endDate);

		assertEquals(0, count.size());
	}

	@Test
	public void checkGetKwFromDateSameDates()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2012, 8, 12);
		Date startDate = cal.getTime();

		cal.set(2012, 8, 12);
		Date endDate = cal.getTime();

		Set<Conflikt.KW> count = conflikt.getKwFromDates(startDate, endDate);

		assertEquals(1, count.size());
	}

	@Test
	public void getEmployeesKW()
	{
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		// 1. Event hinzufügen: 12.08.11 bis 13.04.12
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2011, 8, 12);
		Date startDate = cal.getTime();

		cal.set(2012, 4, 13);
		Date endDate = cal.getTime();

		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate);
		employee.addEvent(event);

		// 1. Event hinzufügen: 27.11.13 bis 27.11.13
		cal.set(2013, 11, 27);
		startDate = cal.getTime();

		cal.set(2013, 11, 27);
		endDate = cal.getTime();
		event = new VacationEvent("Abwesend", startDate, endDate);
		employee.addEvent(event);

		Set<Conflikt.KW> count = conflikt.getEmployeesKW(employee);

		// 12 August 2011 -> KW 35
		// 13 April 2012 -> KW 15
		// (52-35) + 15 = 35
		// +1KW vom zweiten Event
		assertEquals(36, count.size());
	}
}
