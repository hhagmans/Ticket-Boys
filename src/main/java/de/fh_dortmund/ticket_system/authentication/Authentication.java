package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Employees;
import de.fh_dortmund.ticket_system.util.MessageUtil;

public abstract class Authentication implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	private String				name;
	private String				passwort;
	private boolean				loggedIn;
	private Employee			employee;

	@ManagedProperty("#{employees}")
	Employees					employees;

	/**
	 * Hier kommt die Authentifizierung statt.
	 * 
	 * @param name2
	 * @param passwort2
	 * @return
	 */
	protected abstract boolean authenticate(String name, String passwort);

	public String login()
	{
		if (authenticate(name, passwort) && findEmploye())
		{
			setLoggedIn(true);

			return "/pages/index?faces-redirect=true";
		}
		else
		{
			setLoggedIn(false);

			return "/login?faces-redirect=true";
		}
	}

	private boolean findEmploye()
	{
		Employee employee = getEmployees().findEmployeeByID(name);
		if (employee == null)
		{
			MessageUtil.show("User nicht gefunden!", FacesMessage.SEVERITY_ERROR);
			return false;
		}

		setEmployee(employee);

		return true;
	}

	public String logout()
	{
		setLoggedIn(false);

		if (FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getExternalContext() != null)
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/login?faces-redirect=true";
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPasswort()
	{
		return passwort;
	}

	public void setPasswort(String passwort)
	{
		this.passwort = passwort;
	}

	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn)
	{
		this.loggedIn = loggedIn;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	public Employees getEmployees()
	{
		return employees;
	}

	public void setEmployees(Employees employees)
	{
		this.employees = employees;
	}
}