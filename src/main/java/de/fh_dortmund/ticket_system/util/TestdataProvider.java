package de.fh_dortmund.ticket_system.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.business.ConflictFinder;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.EventData;
import de.fh_dortmund.ticket_system.business.ShiftCalculator;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.Week;
import de.fh_dortmund.ticket_system.persistence.ConflictDao;
import de.fh_dortmund.ticket_system.persistence.EmployeeDao;
import de.fh_dortmund.ticket_system.persistence.EmployeeDaoSqlite;
import de.fh_dortmund.ticket_system.persistence.EventDao;
import de.fh_dortmund.ticket_system.persistence.EventDaoSqlite;
import de.fh_dortmund.ticket_system.persistence.ShiftDao;
import de.fh_dortmund.ticket_system.persistence.ShiftDaoSqlite;

/**
 * Provides testdata and fills the database. Used in development to have some
 * testdata.
 * 
 * @author Ticket-Boys
 * 
 */
public class TestdataProvider {
	/**
	 * Fetches the list of all employees and filters them by role. Returns all
	 * {@link Role#dispatcher}s.
	 * 
	 * @return all employees where role = {@link Role#dispatcher}
	 * 
	 * @see #getEmployeeList()
	 */
	public List<Employee> getDispatchingEmployees() {
		List<Employee> dispatchers = new ArrayList<Employee>();

		for (Employee e : getEmployeeList()) {
			if (e.getRole().equals(Role.dispatcher)) {
				dispatchers.add(e);
			}
		}

		return dispatchers;
	}

	/**
	 * Reads the list of employees from the file /test/UserList.json.
	 * 
	 * @return list of employees
	 */
	private static List<Employee> getEmployeeList() {
		List<Employee> empList;

		// Auslesen der json File
		InputStream is = new TestdataProvider().getClass().getResourceAsStream(
				"/test/UserList.json");
		// XXX
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		String json = s.hasNext() ? s.next() : "";

		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Serialisieren in eine Liste von Employees
		Type type = new TypeToken<List<Employee>>() {
		}.getType();
		empList = new Gson().fromJson(json, type);

		// Auslesen der json File
		is = new TestdataProvider().getClass().getResourceAsStream(
				"/test/UserExample.json");
		// XXX
		s = new Scanner(is).useDelimiter("\\A");
		json = s.hasNext() ? s.next() : "";

		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		empList.add(LDAPJsonParser.parseEmployee(json));

		return empList;
	}

	/**
	 * Reads the list of events from the file /test/EventList.json.
	 * 
	 * @return list of events
	 */
	private static List<Event> getEventList() {
		List<Event> eventList;

		// Auslesen der json File
		InputStream is = new TestdataProvider().getClass().getResourceAsStream(
				"/test/EventData.json");
		// XXX
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		String json = s.hasNext() ? s.next() : "";

		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Serialisieren in eine Liste von Employees
		Type type = new TypeToken<List<Event>>() {
		}.getType();
		eventList = new Gson().fromJson(json, type);

		return eventList;
	}

	/**
	 * Fills the employeelist with testdata.
	 * 
	 */
	public static void fillEmployees(EmployeeDao dao) {
		List<Employee> allEmployees = getEmployeeList();

		for (Employee employee : allEmployees) {
			if (!employee.equals(dao.findById(employee.getKonzernID()))) {
				dao.add(employee);
			}
		}

		System.out.println("Employees added " + allEmployees.size());
	}

	/**
	 * Fills the conflictlist with testdata.
	 * 
	 */
	public static void fillConflict(ConflictDao dao) {
		dao.add(new Conflict(new Employee("Penis", "Peter", "Enis", "Marl",
				Role.dispatcher, 0, 0), new Week(2014, 3)));
	}

	/**
	 * Fills the shiftlist with testdata.
	 * 
	 */
	public static void fillShift(ShiftDao dao) {
		TestdataProvider dataProvider = new TestdataProvider();
		List<Employee> dispatchers = dataProvider.getDispatchingEmployees();
		List<Shift> allshifts = new ArrayList<Shift>();
		if (dispatchers != null) {
			ShiftCalculator sh = new ShiftCalculator();

			ConflictFinder conflict = new ConflictFinder();
			EmployeeData ed = new EmployeeData();
			ed.setDao(new EmployeeDaoSqlite());
			conflict.setEmployeeData(ed);

			ShiftData sd = new ShiftData();
			sd.setDao(new ShiftDaoSqlite());
			conflict.setShiftData(sd);

			EventData vd = new EventData();
			vd.setDao(new EventDaoSqlite());
			conflict.setEventData(vd);

			sh.setConflict(conflict);

			allshifts = sh.generateShiftList(dispatchers);
		}

		if (dao.findAll().isEmpty()) {
			for (Shift shift : allshifts) {
				if (!shift.equals(dao.findById(shift.getUniqueRowKey()))) {
					dao.add(shift);
				}
			}
		}
	}

	/**
	 * Fills the eventlist with testdata.
	 * 
	 */
	public static void fillEvents(EventDao dao) {
		List<Event> events = TestdataProvider.getEventList();
		if (dao.findAll().isEmpty()) {
			for (Event event : events) {
				dao.add(event);
			}
		}
	}
}
