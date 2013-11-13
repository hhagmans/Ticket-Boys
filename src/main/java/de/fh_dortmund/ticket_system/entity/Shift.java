package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Dieses Objekt repräsentiert eine Schicht Eine Schicht ist die Zuordnung einer
 * Kalenderwoche zu einem Dispatcher und seinem Vertreter
 * 
 * @author Ticket-Boys
 * 
 */

@Entity
@Table(name = "shift")
public class Shift implements Serializable {

	private static final long serialVersionUID = 1L;

	private int year;
	private int weekNumber;
	private Employee dispatcher;
	private Employee substitutioner;
	private String uniqueRowKey;

	public Shift() {
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
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

		Shift newShift = new Shift(this.getYear(), this.getWeekNumber(),
				this.getDispatcher(), this.getSubstitutioner());

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
	 * @param yearWeekCombi
	 *            KW-YYYY der Schicht
	 */
	public Shift(int year, int weekNumber, Employee dispatcher,
			Employee substitutioner) {
		super();
		this.year = year;
		this.weekNumber = weekNumber;
		this.dispatcher = dispatcher;
		this.substitutioner = substitutioner;
	}

	@Id
	public String getUniqueRowKey() {
		// TODO Auto-generated method stub
		return year + "-" + weekNumber;
	}

	public void setUniqueRowKey(String uniqueRowKey) {
		this.uniqueRowKey = uniqueRowKey;
	}
}
