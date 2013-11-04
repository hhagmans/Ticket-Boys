package de.fh_dortmund.ticket_system.model;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String passwort;
	private boolean logedIn;

	public String login() {
		if (authenticate(name, passwort)) {
			logedIn = true;

			return "index?faces-redirect=true";
		} else {
			logedIn = false;

			FacesMessage msg = new FacesMessage("Login error!", "ERROR MSG");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "login?faces-redirect=true";
		}
	}

	/**
	 * Hier kommt die Authentifizierung mit LDAP statt.
	 * 
	 * @param name2
	 * @param passwort2
	 * @return
	 */
	private boolean authenticate(String name, String passwort) {
		if (name.equals("test") && passwort.equals("test"))
			return true;
		else
			return false;
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

	public Boolean getLogenIn() {
		return logedIn;
	}

	public void setLogenIn(Boolean logenIn) {
		this.logedIn = logenIn;
	}

}