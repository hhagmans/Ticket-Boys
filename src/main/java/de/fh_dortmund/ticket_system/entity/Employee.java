package de.fh_dortmund.ticket_system.entity;

import javax.faces.bean.ManagedBean;

/**
 * Diese Klasse stellt einen Nutzer des Dispatcher- & Urlaubssystem dar
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
public class Employee {
	
	private String konzernID;
	private String firstName;
	private String lastName;
	private String city;
	private int zipcode;
	private Role role;
	
	public String getKonzernID() {
		return konzernID;
	}
	public void setKonzernID(String konzernID) {
		this.konzernID = konzernID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Employee(String konzernID, String firstName, String lastName,
			String city, int zipcode, Role role) {
		super();
		this.konzernID = konzernID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.zipcode = zipcode;
		this.role = role;
	}

}
