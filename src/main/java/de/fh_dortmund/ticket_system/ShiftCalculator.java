package de.fh_dortmund.ticket_system;

import javax.faces.bean.ManagedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten) 
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
public class ShiftCalculator implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Shift> shifts;
	
	public ShiftCalculator() {
	setShifts(new ArrayList<Shift>());
	fillDatShit();
	}

	private void fillDatShit() {
		getShifts().add(new Shift(1,"Herbert", "Nicht Herbert"));
		getShifts().add(new Shift(2,"Dieter", "Herbert"));
		getShifts().add(new Shift(3,"Nicht Herbert", "Herbert"));
		getShifts().add(new Shift(4,"Dieter","Nicht Herbert"));
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}
	
	


}