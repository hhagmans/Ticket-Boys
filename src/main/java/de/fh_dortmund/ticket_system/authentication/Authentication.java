package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.util.LDAPJsonParser;
import de.fh_dortmund.ticket_system.util.MessageUtil;

public abstract class Authentication implements Serializable {
	private static Logger log = Logger.getLogger(Authentication.class);

	private static final long serialVersionUID = 1L;

	private String name;
	private String passwort;
	private boolean loggedIn;
	private Employee employee;

	@ManagedProperty("#{employeeData}")
	EmployeeData employeeData;

	/**
	 * Basic Authentication
	 * 
	 * @param name2
	 * @param passwort2
	 * @return
	 */
	protected abstract boolean authenticate(String name, String passwort);

	public String login() {
		if (isLoggedIn()) {
			return "/pages/index?faces-redirect=true";
		}
		
		/**
		 * employee is authenticated in LDAP system and already in employee list
		 */
		if (authenticate(name, passwort) && findEmployee()) {
			setLoggedIn(true);
			log.info("User mit dem Namen \"" + name + "\" hat sich eingeloggt.");
			return "/pages/index?faces-redirect=true";
			
			/**
			 * employee is authenticated in LDAP system but is not yet listed in employee list
			 */
		} else if (authenticate(name, passwort) && !findEmployee()) {
			getEmployeeData().add(LDAPJsonParser.getActEmployee());
			setEmployee(LDAPJsonParser.getActEmployee());
			setLoggedIn(true);
			log.info("User mit dem Namen \"" + name + "\" hat sich eingeloggt.");
			return "/pages/index?faces-redirect=true";
		}
		/**
		 * employee is not authenticated in LDAP system and not listed in employee list
		 */
		else {
			setLoggedIn(false);
			MessageUtil.showE("Login fehlgeschlagen!");
			return "/login?faces-redirect=true";
		}
	}

	private boolean findEmployee() {
		Employee employee = getEmployeeData().findByID(name);
		if (employee == null) {
//			MessageUtil.showE("User nicht gefunden!");
			return false;
		}

		setEmployee(employee);

		return true;
	}

	public String logout() {
		setLoggedIn(false);

		if ((FacesContext.getCurrentInstance() != null)
				&& (FacesContext.getCurrentInstance().getExternalContext() != null)) {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
		}

		return "/login?faces-redirect=true";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeData getEmployeeData() {
		return employeeData;
	}

	public void setEmployeeData(EmployeeData employeeData) {
		this.employeeData = employeeData;
	}
}
