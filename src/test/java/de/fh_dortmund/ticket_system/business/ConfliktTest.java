package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.ticket_system.base.Week;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import static de.fh_dortmund.ticket_system.util.DateUtil.createDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfliktTest {
	Conflict conflikt;

	@Before
	public void setUp() throws Exception {
		conflikt = new Conflict();
	}

	@Test
	public void getEmployeesWeekSize() {
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		// 1. Event hinzufügen: 12.08.11 bis 13.04.12
		Date startDate = createDate(2011, 8, 12);
		Date endDate = createDate(2012, 4, 13);

		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate,
				true);
		employee.addEvent(event);

		// 1. Event hinzufügen: 27.11.13 bis 27.11.13
		startDate = createDate(2013, 11, 27);
		endDate = createDate(2013, 11, 27);
		event = new VacationEvent("Abwesend", startDate, endDate, true);
		employee.addEvent(event);

		Set<Week> count = conflikt.getEmployeesWeek(employee);

		// 12 August 2011 -> Week 35
		// 13 April 2012 -> Week 15
		// (52-35+1) + 15 = 35
		// Einschließlich Week 35 -> +1
		// +1Week vom zweiten Event
		assertEquals(37, count.size());
	}

	@Test
	public void getEmployeesWeek() {
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		// 1. Event hinzufügen: 12.08.11 bis 13.04.12
		Date startDate = createDate(2013, 11, 27);
		Date endDate = createDate(2013, 11, 27);

		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate,
				true);
		employee.addEvent(event);

		Set<Week> count = conflikt.getEmployeesWeek(employee);
		Week kw = new Week(2013, 48);
		boolean contains = count.contains(kw);
		assertTrue(contains);
	}

	@Test
	public void getShiftWeek() {
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		shifts.add(new Shift(2012, 12, employee1, employee2));
		shifts.add(new Shift(2012, 12, employee1, employee2));
		shifts.add(new Shift(2012, 16, employee1, employee2));
		shifts.add(new Shift(2012, 19, employee1, employee2));
		shifts.add(new Shift(2013, 20, employee1, employee2));
		shifts.add(new Shift(2011, 10, employee1, employee2));

		Set<Week> count = conflikt.getShiftsWeeks(shifts);

		assertEquals(5, count.size());
	}

	@Test
	public void checkForConfliktsTrue() {
		// Shifts with Week 48
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		shifts.add(new Shift(2013, 48, employee1, employee2));
		Set<Week> set1 = conflikt.getShiftsWeeks(shifts);

		// Employees with Week 48
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		Date startDate = createDate(2013, 11, 27);
		Date endDate = createDate(2013, 11, 27);

		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate,
				true);
		employee.addEvent(event);

		Set<Week> set2 = conflikt.getEmployeesWeek(employee);

		assertFalse(conflikt.checkForNoConflicts(set1, set2));
	}

	@Test
	public void checkForConfliktsFalse() {
		// Shifts with Week 52
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		shifts.add(new Shift(2013, 52, employee1, employee2));
		Set<Week> set1 = conflikt.getShiftsWeeks(shifts);

		// Employees with Week 48
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<VacationEvent>());

		Date startDate = createDate(2013, 11, 27);
		Date endDate = createDate(2013, 11, 27);
		VacationEvent event = new VacationEvent("Abwesend", startDate, endDate,
				true);
		employee.addEvent(event);

		Set<Week> set2 = conflikt.getEmployeesWeek(employee);

		assertTrue(conflikt.checkForNoConflicts(set1, set2));
	}
}
