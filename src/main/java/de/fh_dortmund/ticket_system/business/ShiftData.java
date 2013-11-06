package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.entity.ShiftModel;
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
public class ShiftData implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private ShiftModel shiftModel;
	private ShiftDAO shiftDAO;
	
	public ShiftData() {
		shiftDAO = new ShiftDAOImpl();
		setShiftModel(new ShiftModel(shiftDAO.getAllShifts()));
		
	}


	public ShiftModel getShiftModel() {
		return shiftModel;
	}


	public void setShiftModel(ShiftModel shiftModel) {
		this.shiftModel = shiftModel;
	}
	
}