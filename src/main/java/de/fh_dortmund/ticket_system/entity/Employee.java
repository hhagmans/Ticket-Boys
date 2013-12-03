package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private String konzernID;
	private String firstName;
	private String lastName;
	private String city;
	private int zipcode;
	private Role role;
	private int score;
	private int vacationCount;
	private int maxVacationCount=30;
	private int freeVacationCount;

	private Set<VacationEvent> myEvents;

	public Employee() {
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			Employee emp = (Employee) obj;

			return emp.getKonzernID().equals(this.getKonzernID());

		}
		return false;
	}

	@Id
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void incrementScore() {
		this.score++;
	}

	public void decrementScore() {
		this.score--;
	}

	public int getVacationCount() {
		return vacationCount;
	}

	public void setVacationCount(int vacationCount) {
		this.vacationCount = vacationCount;
	}

	public void incrementVacationCount(int value) {
		this.vacationCount += value;
	}

	public void decrementVacationCount(int value) {
		this.vacationCount -= value;
	}

	public Employee(String konzernID, String firstName, String lastName,
			String city, int zipcode, Role role, int score, int vacationCount) {
		this.konzernID = konzernID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.zipcode = zipcode;
		this.role = role;
		this.score = score;
		this.vacationCount = vacationCount;
	}

	@javax.persistence.Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "vacations", joinColumns = { @JoinColumn(name = "employeeID") }, inverseJoinColumns = { @JoinColumn(name = "vacationID") })
	public Set<VacationEvent> getMyEvents() {
		return myEvents;
	}

	public void setMyEvents(Set<VacationEvent> myEvents) {
		this.myEvents = myEvents;
	}

	public void addEvent(VacationEvent event) {
		this.myEvents.add(event);
	}

	public void deleteEvent(VacationEvent event) {

	}

	public int getMaxVacationCount() {
		return maxVacationCount;
	}

	public void setFreeVacationCount(int freeVacationDays) {
		this.freeVacationCount = freeVacationDays;
	}

	public int getFreeVacationCount() {
		return freeVacationCount;
	}

	public void setMaxVacationCount(int maxVacationCount) {
		this.maxVacationCount = maxVacationCount;
	}

	public void refreshFreeVacationDays() {
		this.freeVacationCount = maxVacationCount - vacationCount;
	}

}