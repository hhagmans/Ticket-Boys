<<<<<<< HEAD
package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Employees;

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

			setEmployee(employees.findEmployeeByID(name));

			return "/pages/index?faces-redirect=true";
		}
		else
		{
			setLoggedIn(false);

			FacesMessage msg = new FacesMessage("Login error! Name oder Passwort ist falsch.", "ERROR MSG");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

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
=======
package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Employees;

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

			setEmployee(employees.findEmployeeByID(name));

			return "/pages/index?faces-redirect=true";
		}
		else
		{
			setLoggedIn(false);

			FacesMessage msg = new FacesMessage("Login error! Name oder Passwort ist falsch.", "ERROR MSG");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

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
>>>>>>> branch 'master' of https://github.com/hhagmans/Ticket-Boys.git
}
