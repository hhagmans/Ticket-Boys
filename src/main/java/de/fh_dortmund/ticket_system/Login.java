package de.fh_dortmund.ticket_system;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;


/**
 * Login-Bean zur Validierung der Login-Daten
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;

	private String test = "blub";
	
	private String name;
	private String passwort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTest(){
		return test;
	}
	
	public void setTest(String test){
		this.test = test;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

}