package de.fh_dortmund.ticket_system.authentication;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "auth")
@SessionScoped
public class Authentication implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String passwort;
	private boolean loggedIn;

	public String login() {
		if (authenticate(name, passwort)) {
			setLoggedIn(true);

			return "/pages/index?faces-redirect=true";
		} else {
			setLoggedIn(false);

			FacesMessage msg = new FacesMessage("Login error! Name oder Passwort ist falsch.", "ERROR MSG");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			return "/login?faces-redirect=true";
		}
	}

	public String logout() {
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

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}