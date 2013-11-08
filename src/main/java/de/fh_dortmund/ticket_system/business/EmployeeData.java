package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.EmployeeModel;
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
public class EmployeeData implements Serializable
{

	private static final long serialVersionUID = 1L;
	private EmployeeDAO employeeDAO;

	private EmployeeModel employeeModel;

	public EmployeeData()
	{
		employeeDAO = new EmployeeDAOImpl();
		initializeEmployeeModel();
	}

	private void initializeEmployeeModel()
	{
		setEmployeeModel(new EmployeeModel(employeeDAO.getAllEmployees()));
	}

	public Employee findEmployeeByID(String konzernID)
	{
		return getEmployeeModel().getRowData(konzernID);
	}

	public EmployeeModel getEmployeeModel()
	{
		return employeeModel;
	}

	public void setEmployeeModel(EmployeeModel employeeModel)
	{
		this.employeeModel = employeeModel;
	}

}
