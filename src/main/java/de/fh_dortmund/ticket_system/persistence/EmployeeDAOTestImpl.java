package de.fh_dortmund.ticket_system.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.entity.Employee;

public class EmployeeDAOTestImpl implements EmployeeDAO
{
	private ArrayList<Employee> database = (ArrayList<Employee>) getDatabase();

	@Override
	public List<Employee> findAllEmployees()
	{

		return database;
	}

	private List<Employee> getDatabase()
	{
		List<Employee> empList = null;

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
	public void updateEmployee(Employee employee)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteEmployee(Employee employee)
	{
		database.remove(employee);

	}

	@Override
	public void addEmployee(Employee employee)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Employee findEmployeeById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
