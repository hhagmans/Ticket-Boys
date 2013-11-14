package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.persistence.ShiftDAO;
import de.fh_dortmund.ticket_system.persistence.ShiftDAOTestImpl;
import de.fh_dortmund.ticket_system.persistence.ShiftDAOsqlLite;

/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten)
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
@SessionScoped
public class ShiftData implements Serializable
{

	private static final long serialVersionUID = 1L;

	private ShiftModel shiftModel;
	private ShiftDAO shiftDAO;

	public ShiftData()
	{
		shiftDAO = new ShiftDAOsqlLite();
		fill();
		setShiftModel(new ShiftModel(shiftDAO.findAllShifts()));

	}
	
	public void fill() {
		ShiftDAO shiftDAOtemp = new  ShiftDAOTestImpl();
		
		List<Shift> allshifts = shiftDAOtemp.findAllShifts();
		
		for (Shift shift : allshifts) {
			if (!shift.equals(shiftDAO.findShiftById(shift.getUniqueRowKey())))
			shiftDAO.addShift(shift);
		}
	}

	public ShiftModel getShiftModel()
	{
		return shiftModel;
	}

	public void setShiftModel(ShiftModel shiftModel)
	{
		this.shiftModel = shiftModel;
	}

}
