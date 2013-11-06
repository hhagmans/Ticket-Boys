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
public class EmployeeData
{

	private static final long	serialVersionUID	= 1L;
	private EmployeeDAO employeeDAO;
	private EmployeeModel employeeModel;

	public EmployeeData()
	{
		employeeDAO = new EmployeeDAOImpl();
		setEmployeeModel(new EmployeeModel(employeeDAO.getAllEmployees()));
	}


	public Employee findEmployeeByID(String konzernID)
	{

		return getEmployeeModel().getRowData(konzernID);
	}


	public EmployeeModel getEmployeeModel() {
		return employeeModel;
	}


	public void setEmployeeModel(EmployeeModel employeeModel) {
		this.employeeModel = employeeModel;
	}
}

