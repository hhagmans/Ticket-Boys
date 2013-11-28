package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import de.fh_dortmund.ticket_system.business.Conflikt.KW;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore
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
	public void getEmployeesKWSize()
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

	@Test
	public void getKW()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2013, 11, 27);
		Date startDate = cal.getTime();
		
		int kw = conflikt.getKW(startDate);
		
		assertEquals(48, kw);
	}

	@Test
	public void getEmployeesKW()
	{
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		// 1. Event hinzufügen: 12.08.11 bis 13.04.12
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2013, 11, 27);
		Date startDate = cal.getTime();

		cal.set(2013, 11, 27);
		Date endDate = cal.getTime();

		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate);
		employee.addEvent(event);

		Set<Conflikt.KW> count = conflikt.getEmployeesKW(employee);
		Conflikt.KW kw = new Conflikt.KW(2013, 48);
		assertTrue(count.contains(kw));
	}

	@Test
	public void getShiftKW()
	{
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		shifts.add(new Shift(2012, 12, employee1, employee2));
		shifts.add(new Shift(2012, 16, employee1, employee2));
		shifts.add(new Shift(2012, 19, employee1, employee2));
		shifts.add(new Shift(2013, 20, employee1, employee2));
		shifts.add(new Shift(2011, 10, employee1, employee2));
		shifts.add(new Shift(2012, 12, employee2, employee1));
		Set<KW> count = conflikt.getShiftKW(shifts);

		assertEquals(shifts.size(), count.size());
	}

	@Test
	public void checkForConfliktsPositive()
	{
		// Shifts with KW 48
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		shifts.add(new Shift(2013, 47, employee1, employee2));
		shifts.add(new Shift(2013, 48, employee1, employee2));
		shifts.add(new Shift(2013, 49, employee1, employee2));
		Set<KW> set1 = conflikt.getShiftKW(shifts);

		// Employees with KW 48
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		Calendar cal = new GregorianCalendar();
		cal.set(2013, 11, 27);
		Date startDate = cal.getTime();

		cal.set(2013, 11, 27);
		Date endDate = cal.getTime();
		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate);
		employee.addEvent(event);

		Set<Conflikt.KW> set2 = conflikt.getEmployeesKW(employee);

		assertTrue(conflikt.checkForNoConflicts(set1, set2));
	}

	@Test
	public void checkForConfliktsNegative()
	{
		// Shifts with KW 52
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		shifts.add(new Shift(2013, 52, employee1, employee2));
		Set<KW> set1 = conflikt.getShiftKW(shifts);

		// Employees with KW 48
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		Calendar cal = new GregorianCalendar();
		cal.set(2013, 11, 27);
		Date startDate = cal.getTime();

		cal.set(2013, 11, 27);
		Date endDate = cal.getTime();
		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate);
		employee.addEvent(event);

		Set<Conflikt.KW> set2 = conflikt.getEmployeesKW(employee);

		assertFalse(conflikt.checkForNoConflicts(set1, set2));
	}
}
