package de.fh_dortmund.ticket_system.business;

import static de.fh_dortmund.ticket_system.util.DateUtil.createDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.EventType;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;

public class ConfliktTest {
	ConflictFinder conflikt;

	@Before
	public void setUp() throws Exception {
		conflikt = new ConflictFinder();
	}

	@Test
	public void getEmployeesWeekSize() {
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<Event>());

		// 1. Event hinzufügen: 12.08.11 bis 13.04.12
		Date startDate = createDate(2011, 8, 12);
		Date endDate = createDate(2012, 4, 13);

		Event event = new Event("Abwesend", startDate, endDate,
				EventType.vacation);
		employee.addEvent(event);

		// 1. Event hinzufügen: 27.11.13 bis 27.11.13
		startDate = createDate(2013, 11, 27);
		endDate = createDate(2013, 11, 27);
		event = new Event("Abwesend", startDate, endDate, EventType.vacation);
		employee.addEvent(event);

		Set<Week> count = conflikt.getEmployeesWeek(employee);

		// 12 August 2011 -> Week 35
		// 13 April 2012 -> Week 15
		// (52-35+1) + 15 = 35
		// Einschließlich Week 35 -> +1
		// +1Week vom zweiten Event
		if (new GregorianCalendar().getFirstDayOfWeek() == 2) {
			assertEquals(37, count.size());
		} else {
			assertEquals(36, count.size());
		}
	}

	@Test
	public void getEmployeesWeek() {
		Employee employee = new Employee();
		employee.setMyEvents(new HashSet<Event>());

		// 1. Event hinzufügen: 12.08.11 bis 13.04.12
		Date startDate = createDate(2013, 11, 27);
		Date endDate = createDate(2013, 11, 27);

		Event event = new Event("Abwesend", startDate, endDate,
				EventType.vacation);
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
		employee.setMyEvents(new HashSet<Event>());

		Date startDate = createDate(2013, 11, 27);
		Date endDate = createDate(2013, 11, 27);

		Event event = new Event("Abwesend", startDate, endDate,
				EventType.vacation);
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
		employee.setMyEvents(new HashSet<Event>());

		Date startDate = createDate(2013, 11, 27);
		Date endDate = createDate(2013, 11, 27);
		Event event = new Event("Abwesend", startDate, endDate,
				EventType.vacation);
		employee.addEvent(event);

		Set<Week> set2 = conflikt.getEmployeesWeek(employee);

		assertTrue(conflikt.checkForNoConflicts(set1, set2));
	}

	@Test
	public void checkSwitchShifts() {
		List<Shift> shifts = new ArrayList<Shift>();
		Employee employee1 = new Employee();
		Employee employee2 = new Employee();
		Employee employee3 = new Employee();
		Shift shift0 = new Shift(2013, 51, employee2, employee3);
		Shift shift1 = new Shift(2013, 52, employee3, employee1);
		shifts.add(new Shift(2013, 50, employee1, employee2));
		shifts.add(shift0);
		shifts.add(shift1);

		Date startDate = createDate(2013, 12, 27);
		Date endDate = createDate(2013, 12, 29);
		Event event = new Event("Abwesend", startDate, endDate,
				EventType.vacation);
		employee2.addEvent(event);

		Shift tempShift0 = new Shift(shift0.getWeek(), shift0.getDispatcher(),
				shift0.getSubstitutioner());

		shift0.setDispatcher(shift1.getDispatcher());
		shift1.setDispatcher(tempShift0.getDispatcher());

		Set<Week> set1 = conflikt.getShiftsWeeks(shifts);

		Set<Week> set2 = conflikt.getEmployeesWeek(employee2);

		Set<Week> set3 = conflikt.getEmployeesWeek(employee3);

		System.out.println("Set1:");
		for (Week week : set1) {
			System.out.println(week.getWeekNumber());
		}

		System.out.println("Set2:");
		for (Week week : set2) {
			System.out.println(week.getWeekNumber());
		}

		assertFalse(conflikt.checkForNoConflicts(set1, set2));
		assertTrue(conflikt.checkForNoConflicts(set1, set3));

	}
}
