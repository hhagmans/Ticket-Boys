package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.annotation.PostConstruct;
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

	@PostConstruct
	public void blub()
	{

	}

	public String login()
	{
		if (authenticate(name, passwort))
		{
			setLoggedIn(true);

			setEmployee(getEmployees().findEmployeeByID(name));

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

	public Employees getEmployees()
	{
		if (employees == null)
			employees = (Employees) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap()
					.get("employees");

		if (employees == null)
			employees = (Employees) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("employees");

		return employees;
	}

	public void setEmployees(Employees employees)
	{
		this.employees = employees;
	}
}