package de.fh_dortmund.ticket_system.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.business.ShiftCalculator;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

@ManagedBean
@ApplicationScoped
public class ShiftDAOTestImpl implements ShiftDAO
{

	@Override
	public List<Shift> findAllShifts()
	{
		List<Employee> empList = null;

		empList = getEmployeeList();

		// Verteilen der Employees auf die 52 "Shifts"
		if (empList != null)
		{

			ShiftCalculator sh = new ShiftCalculator();

			return sh.generateDispatcherList(empList);
		}

		return null;

	}

	private List<Employee> getEmployeeList()
	{
		List<Employee> empList;
		// Auslesen der json File
		InputStream is = getClass().getResourceAsStream("/test/UserList.json");
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
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

	@Override
	public void updateShift(Shift shift)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteShift(Shift shift)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void addShift(Shift newShift)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Shift findShiftById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}