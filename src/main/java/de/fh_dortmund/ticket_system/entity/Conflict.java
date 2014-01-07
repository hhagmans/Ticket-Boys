package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Represents a conflict for an employee and the week.
 * 
 * @author Alex Hofmann
 * 
 */
@Entity
@Table(name = "conflict")
@NamedQueries({
		@NamedQuery(name = "Conflict_findAll", query = "SELECT v FROM Conflict v"),
		@NamedQuery(name = "Conflict_findByUser", query = "SELECT v FROM Conflict v WHERE v.employee = :employee") })
public class Conflict implements Serializable {

	private static final long serialVersionUID = 4982696674221907971L;

	private String id;
	private Employee employee;
	private Week week;
	private Boolean solved;
	private String name;

	public Conflict() {
		this.id = null;
	}

	public Conflict(Employee employee, Week week) {
		super();
		this.employee = employee;
		this.week = week;
		this.solved = false;
		this.name = "Kalenderwoche: " + week.getUniqueRowKey();
		this.id = employee.getKonzernID() + week.getUniqueRowKey();
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((employee == null) ? 0 : employee.hashCode());
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((solved == null) ? 0 : solved.hashCode());
		result = (prime * result) + ((week == null) ? 0 : week.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return name;
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
		Conflict other = (Conflict) obj;
		if (employee == null) {
			if (other.employee != null) {
				return false;
			}
		} else if (!employee.equals(other.employee)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (solved == null) {
			if (other.solved != null) {
				return false;
			}
		} else if (!solved.equals(other.solved)) {
			return false;
		}
		if (week == null) {
			if (other.week != null) {
				return false;
			}
		} else if (!week.equals(other.week)) {
			return false;
		}
		return true;
	}

	public Boolean getSolved() {
		return solved;
	}

	public void setSolved(Boolean solved) {
		this.solved = solved;

		if ((solved == true) && !getName().substring(0, 9).equals("GELÖST - ")) {
			setName("GELÖST - " + getName());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
