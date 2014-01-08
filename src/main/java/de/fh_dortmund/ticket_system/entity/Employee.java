package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.fh_dortmund.ticket_system.util.HolidayCalendarType;

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
	private Role role;
	private int score;
	private HolidayCalendarType holidayCalendarType = HolidayCalendarType.germanyNRW;
	private int vacationCount = 0;
	private int maxVacationCount = 30;
	private int freeVacationCount;
	private String email = "ticketboys1337@gmail.com";

	private Set<Event> myEvents = new HashSet<Event>();

	public Employee() {
		this.freeVacationCount = maxVacationCount - vacationCount;
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
			String city, Role role, int score, int vacationCount) {
		this.konzernID = konzernID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.role = role;
		this.score = score;
		this.vacationCount = vacationCount;
		this.freeVacationCount = maxVacationCount - vacationCount;
	}

	public Employee(String konzernID, String firstName, String lastName,
			String city, Role role, int score, int vacationCount,
			HolidayCalendarType holidayCalendarType) {
		this.konzernID = konzernID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.role = role;
		this.score = score;
		this.vacationCount = vacationCount;
		this.freeVacationCount = maxVacationCount - vacationCount;
		this.holidayCalendarType = holidayCalendarType;
	}

	@javax.persistence.Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "vacations", joinColumns = { @JoinColumn(name = "employeeID") }, inverseJoinColumns = { @JoinColumn(name = "vacationID") })
	// @OneToMany(cascade = CascadeType.MERGE)
	// @JoinTable(name = "employeeID")
	public Set<Event> getMyEvents() {
		return myEvents;
	}

	public void setMyEvents(Set<Event> myEvents) {
		this.myEvents = myEvents;
	}

	public void addEvent(Event event) {
		this.myEvents.add(event);
	}

	public void deleteEvent(Event event) {
		this.myEvents.remove(event);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = (prime * result)
				+ ((konzernID == null) ? 0 : konzernID.hashCode());
		result = (prime * result)
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Employee other = (Employee) obj;
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (konzernID == null) {
			if (other.konzernID != null) {
				return false;
			}
		} else if (!konzernID.equals(other.konzernID)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		return true;
	}

	public HolidayCalendarType getHolidayCalendarType() {
		return holidayCalendarType;
	}

	public void setHolidayCalendarType(HolidayCalendarType holidayCalendarType) {
		this.holidayCalendarType = holidayCalendarType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}