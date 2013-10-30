package de.fh_dortmund.ticket_system;

import javax.faces.bean.ManagedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Dieses Objekt berechnet den Dispatcher-Schichtplan und erstellt Shift-Objekte
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
public class ShiftCalculator implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Shift> shifts;
	
	public ShiftCalculator() {
	shifts = new ArrayList<Shift>();
	fillDatShit();
	}

	private void fillDatShit() {
		shifts.add(new Shift(1,"Herbert", "Nicht Herbert"));
		shifts.add(new Shift(2,"Dieter", "Herbert"));
		shifts.add(new Shift(3,"Nicht Herbert", "Herbert"));
		shifts.add(new Shift(3,"Dieter","Nicht Herbert"));
	}
	
	


}