package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Diese Klasse stellt einen Nutzer des Dispatcher- & Urlaubssystem dar
 * 
 * @author Ticket-Boys
 * 
 */

@Entity
@Table(name = "employee")
@NamedQueries({ @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e") })
public class Employee implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String konzernID;
	private String firstName;
	private String lastName;
	private String city;
	private int zipcode;
	private Role role;
	@OneToMany
	private List<VacationEvent> myEvents;

	public Employee()
	{
		super();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Employee)
		{
			Employee emp = (Employee) obj;

			return emp.getKonzernID().equals(this.getKonzernID());

		}
		return false;
	}

	@Id
	public String getKonzernID()
	{
		return konzernID;
	}

	public void setKonzernID(String konzernID)
	{
		this.konzernID = konzernID;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public int getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(int zipcode)
	{
		this.zipcode = zipcode;
	}

	// @OneToOne
	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Employee(String konzernID, String firstName, String lastName, String city, int zipcode, Role role)
	{
		this.konzernID = konzernID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.zipcode = zipcode;
		this.role = role;
	}

	@javax.persistence.Transient
	public String getFullName()
	{
		return firstName + " " + lastName;
	}

	public List<VacationEvent> getMyEvents() {
		return myEvents;
	}

	public void setMyEvents(List<VacationEvent> myEvents) {
		this.myEvents = myEvents;
	}



}