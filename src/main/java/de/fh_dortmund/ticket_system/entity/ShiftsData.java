package de.fh_dortmund.ticket_system.entity;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.persistence.ShiftDAO;
import de.fh_dortmund.ticket_system.persistence.ShiftDAOImpl;


/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten) 
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
@SessionScoped
public class ShiftsData implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private ShiftModel shiftModel;
	private ShiftDAO shiftDAO;
	
	public ShiftsData() {
		shiftDAO = new ShiftDAOImpl();
		shiftModel = new ShiftModel(shiftDAO.getAllShifts());
		
	}


	public ShiftModel getShiftModel() {
		return shiftModel;
	}
	
}