package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
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
public class EmployeeData extends BaseData<Employee, EmployeeDAO> implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	public EmployeeData()
	{
		dao = new EmployeeDAOsqlLite();

		addStuff();
	}

	private void addStuff()
	{
		EmployeeDAO employeeDAOtemp = new EmployeeDAOTestImpl();

		List<Employee> allEmployees = employeeDAOtemp.findAll();

		for (Employee employee : allEmployees)
		{
			if (!employee.equals(dao.findById(employee.getKonzernID())))
				dao.add(employee);
		}

		System.out.println("Employees added " + allEmployees.size());
	}
}
