package de.fh_dortmund.ticket_system.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.business.ShiftCalculator;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.persistence.EmployeeDao;
import de.fh_dortmund.ticket_system.persistence.ShiftDao;

public class TestdataProvider
{
	/**
	 * Fetches the list of all employees and filters them by role. Returns all {@link Role#dispatcher}s.
	 * 
	 * @return all employees where role = {@link Role#dispatcher}
	 * 
	 * @see #getEmployeeList()
	 */
	public List<Employee> getDispatchingEmployees()
	{
		List<Employee> dispatchers = new ArrayList<Employee>();

		for (Employee e : getEmployeeList())
			if (e.getRole().equals(Role.dispatcher))
				dispatchers.add(e);

		return dispatchers;
	}

	/**
	 * Reads the list of employees from the file /test/UserList.json.
	 * 
	 * @return list of employees
	 */
	private static List<Employee> getEmployeeList()
	{
		List<Employee> empList;

		// Auslesen der json File
		InputStream is = new TestdataProvider().getClass().getResourceAsStream("/test/UserList.json");
		// XXX
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		String json = s.hasNext() ? s.next() : "";

		try
		{
			is.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// Serialisieren in eine Liste von Employees
		Type type = new TypeToken<List<Employee>>()
		{
		}.getType();
		empList = new Gson().fromJson(json, type);

		return empList;
	}

	public static void fillEmployees(EmployeeDao dao)
	{
		List<Employee> allEmployees = getEmployeeList();

		for (Employee employee : allEmployees)
		{
			if (!employee.equals(dao.findById(employee.getKonzernID())))
				dao.add(employee);
		}

		System.out.println("Employees added " + allEmployees.size());
	}

	public static void fillShift(ShiftDao dao)
	{
		TestdataProvider dataProvider = new TestdataProvider();
		List<Employee> dispatchers = dataProvider.getDispatchingEmployees();
		List<Shift> allshifts = new ArrayList<Shift>();
		if (dispatchers != null)
		{
			ShiftCalculator sh = new ShiftCalculator();
			allshifts = sh.generateShiftList(dispatchers);
		}

		if (dao.findAll().isEmpty())
		{
			for (Shift shift : allshifts)
			{
				if (!shift.equals(dao.findById(shift.getUniqueRowKey())))
					dao.add(shift);
			}
		}
	}
}
