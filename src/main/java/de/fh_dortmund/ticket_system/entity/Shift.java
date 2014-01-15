package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Represents a shift. A shift is assigns a dispatcher and his representative to
 * a week.
 * 
 * @author Ticket-Boys
 * 
 */

@Entity
@Table(name = "shift")
@NamedQueries({ @NamedQuery(name = "findByDispatcher", query = "SELECT c FROM Shift c WHERE c.dispatcher = :dispatcher") })
public class Shift implements Serializable {
	private static final long serialVersionUID = 1L;
	private Week week;
	private Employee dispatcher;
	private Employee substitutioner;
	private String uniqueRowKey;

	public Shift() {
	}

	public Employee getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Employee dispatcher) {
		this.dispatcher = dispatcher;
	}

	public Employee getSubstitutioner() {
		return substitutioner;
	}

	public void setSubstitutioner(Employee substitutioner) {
		this.substitutioner = substitutioner;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		Shift newShift = new Shift(this.getWeek().getYear(), this.getWeek()
				.getWeekNumber(), this.getDispatcher(),
				this.getSubstitutioner());

		return newShift;
	}

	/**
	 * 
	 * @param weekNumber
	 *            Kalenderwoche der Schicht
	 * @param dispatcher
	 *            vollständiger Name der dieser Schicht zugeteilten Dispatchers
	 * @param substitutioner
	 *            vollständiger Name der dieser Schicht zugeteilten Vertreters
	 *            des Dispatchers
	 */
	public Shift(int year, int weekNumber, Employee dispatcher,
			Employee substitutioner) {
		this(new Week(year, weekNumber), dispatcher, substitutioner);
	}

	/**
	 * 
	 * @param weekNumber
	 *            Kalenderwoche der Schicht
	 * @param dispatcher
	 *            vollständiger Name der dieser Schicht zugeteilten Dispatchers
	 * @param substitutioner
	 *            vollständiger Name der dieser Schicht zugeteilten Vertreters
	 *            des Dispatchers
	 */
	public Shift(Week week, Employee dispatcher, Employee substitutioner) {
		super();
		this.week = week;
		this.dispatcher = dispatcher;
		this.substitutioner = substitutioner;
	}

	@Id
	public String getUniqueRowKey() {
		return week.getYear() + "-" + week.getWeekNumber();
	}

	public void setUniqueRowKey(String uniqueRowKey) {
		this.uniqueRowKey = uniqueRowKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((dispatcher == null) ? 0 : dispatcher.hashCode());
		result = (prime * result)
				+ ((substitutioner == null) ? 0 : substitutioner.hashCode());
		result = (prime * result)
				+ ((uniqueRowKey == null) ? 0 : uniqueRowKey.hashCode());
		result = (prime * result) + week.getYear();
		result = (prime * result) + week.getWeekNumber();
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
		Shift other = (Shift) obj;
		if (dispatcher == null) {
			if (other.dispatcher != null) {
				return false;
			}
		} else if (!dispatcher.equals(other.dispatcher)) {
			return false;
		}
		if (substitutioner == null) {
			if (other.substitutioner != null) {
				return false;
			}
		} else if (!substitutioner.equals(other.substitutioner)) {
			return false;
		}
		if (uniqueRowKey == null) {
			if (other.uniqueRowKey != null) {
				return false;
			}
		} else if (!uniqueRowKey.equals(other.uniqueRowKey)) {
			return false;
		}
		if (!week.equals(other.week)) {
			return false;
		}

		return true;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	// @Override
	// public boolean equals(Object obj)
	// {
	// if (obj instanceof Shift)
	// {
	// Shift shift = (Shift) obj;
	//
	// return shift.getUniqueRowKey().equals(this.getUniqueRowKey());
	//
	// }
	// return false;
	// }

}
