package de.fh_dortmund.ticket_system.entity;

import javax.faces.bean.ManagedBean;

import java.io.Serializable;


/**
 * Dieses Objekt repräsentiert eine Schicht
 * Eine Schicht ist die Zuordnung einer Kalenderwoche zu einem Dispatcher und seinem Vertreter
 * @author Ticket-Boys
 *
 */

@ManagedBean
public class Shift implements Serializable {

	private static final long serialVersionUID = 1L;

	private int weekNumber;
	private String dispatcherName;
	private String substituteName;
	
	/**
	 * 
	 * @param weekNumber Kalenderwoche der Schicht
	 * @param dispatcherName vollständiger Name der dieser Schicht zugeteilten Dispatchers
	 * @param substituteName vollständiger Name der dieser Schicht zugeteilten Vertreters des Dispatchers
	 */
	public Shift(int weekNumber, String dispatcherName, String substituteName) {
		super();
		this.weekNumber = weekNumber;
		this.dispatcherName = dispatcherName;
		this.substituteName = substituteName;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public String getDispatcherName() {
		return dispatcherName;
	}
	public void setDispatcherName(String dispatcherName) {
		this.dispatcherName = dispatcherName;
	}
	public String getSubstituteName() {
		return substituteName;
	}
	public void setSubstituteName(String substituteName) {
		this.substituteName = substituteName;
	}


}