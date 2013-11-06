package de.fh_dortmund.ticket_system.business;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.EmployeeDAO;
import de.fh_dortmund.ticket_system.persistence.EmployeeDAOImpl;

/**
 * Diese Klasse speichert und verwaltet alle bekannten Benutzer
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
@SessionScoped
public class EmployeeData
{

	private static final long	serialVersionUID	= 1L;
	private List<Employee>		employees;
	private Employee			selectedEmployee;
	private EmployeeDAO employeeDAO;

	public EmployeeData()
	{
		employeeDAO = new EmployeeDAOImpl();
		employees = employeeDAO.getAllEmployees();
	}


	public Employee findEmployeeByID(String konzernID)
	{
		for (Employee employee : employees)
		{
			if (employee.getKonzernID().equals(konzernID))
				return employee;
		}

		return null;
	}

	public List<Employee> getEmployees()
	{
		return employees;
	}

	public void setEmployees(List<Employee> employees)
	{
		this.employees = employees;
	}

	public Employee getSelectedEmployee()
	{
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee)
	{
		this.selectedEmployee = selectedEmployee;
	}



}
