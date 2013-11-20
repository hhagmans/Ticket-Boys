package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.EmployeeDAO;
import de.fh_dortmund.ticket_system.persistence.EmployeeDAOTestImpl;
import de.fh_dortmund.ticket_system.persistence.EmployeeDAOsqlLite;

/**
 * Diese Klasse speichert und verwaltet alle bekannten Benutzer
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
@ApplicationScoped
public class EmployeeData implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	private EmployeeDAO			employeeDAO;

	public EmployeeData()
	{
		employeeDAO = new EmployeeDAOsqlLite();

		addStuff();
	}

	private void addStuff()
	{
		EmployeeDAO employeeDAOtemp = new EmployeeDAOTestImpl();

		List<Employee> allEmployees = employeeDAOtemp.findAllEmployees();

		for (Employee employee : allEmployees)
		{
			if (!employee.equals(employeeDAO.findEmployeeById(employee.getKonzernID())))
				addEmployee(employee);
		}

		System.out.println("Employees added " + allEmployees.size());
	}

	public Employee findEmployeeByID(String konzernID)
	{
		Employee employee = employeeDAO.findEmployeeById(konzernID);
		return employee;
	}

	public void updateEmployee(Employee employee)
	{
		employeeDAO.updateEmployee(employee);
	}

	public void deleteEmployee(Employee employee)
	{
		employeeDAO.deleteEmployee(employee);
	}

	public void addEmployee(Employee employee)
	{
		employeeDAO.addEmployee(employee);
	}

	public List<Employee> findAllEmployees()
	{
		return employeeDAO.findAllEmployees();
	}
}
