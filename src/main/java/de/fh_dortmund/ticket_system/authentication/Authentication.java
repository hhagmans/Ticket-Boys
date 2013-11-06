package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesServlet;

import com.sun.faces.util.MessageUtils;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Employees;
import de.fh_dortmund.ticket_system.util.MessageUtil;

@ManagedBean(name = "auth")
@SessionScoped
public class Authentication implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	private String				name;
	private String				passwort;
	private boolean				loggedIn;
	private Employee			employee;

	@ManagedProperty("#{employees}")
	Employees					employees;

	public String login()
	{
		if (authenticate(name, passwort))
		{
			setLoggedIn(true);

			Employee employee = getEmployees().findEmployeeByID(name);
			if (employee == null)
			{
				MessageUtil.show("User nicht gefunden!", FacesMessage.SEVERITY_ERROR);
			}
			else
			{
				setEmployee(employee);
			}

			return "/pages/index?faces-redirect=true";
		}
		else
		{
			setLoggedIn(false);

			MessageUtil.show("Login error! Name oder Passwort ist falsch.", FacesMessage.SEVERITY_ERROR);

			return "/login?faces-redirect=true";
		}
	}

	public String logout()
	{
		setLoggedIn(false);

		return "/login?faces-redirect=true";
	}

	/**
	 * Hier kommt die Authentifizierung mit LDAP statt.
	 * 
	 * @param name2
	 * @param passwort2
	 * @return
	 */
	private boolean authenticate(String name, String passwort)
	{
		return true;
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