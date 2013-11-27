package de.fh_dortmund.ticket_system.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.business.ShiftCalculator;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.entity.Shift;

public class ShiftDaoTestdataProvider
{
	
	/**
	 * Fetches the list of dispatchers (employees with role =
	 * {@link Role#dispatcher}). Calculates the shifts for those dispatchers
	 * using {@link ShiftCalculator}.
	 * 
	 * @return a (generated) {@link List} of all {@link Shift}s, or
	 *         <code>null</code> on error
	 */
	public List<Shift> findAllShifts()
	{
		List<Employee> dispatchers = getDispatchingEmployees();

		// Verteilen der Employees auf die 52 "Shifts"
		if (dispatchers != null)
		{
			ShiftCalculator sh = new ShiftCalculator();
			return sh.generateShiftList(dispatchers);
		}

		return null;
	}

	/**
	 * Fetches the list of all employees and filters them by role. Returns all {@link Role#dispatcher}s.
	 * 
	 * @return all employees where role = {@link Role#dispatcher}
	 * 
	 * @see #getEmployeeList()
	 */
	private List<Employee> getDispatchingEmployees()
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
	private List<Employee> getEmployeeList()
	{
		List<Employee> empList;

		// Auslesen der json File
		InputStream is = getClass().getResourceAsStream("/test/UserList.json");
		@SuppressWarnings("resource") // XXX
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
		Type type = new TypeToken<List<Employee>>(){}.getType();
		empList = new Gson().fromJson(json, type);
		
		return empList;
	}

}
