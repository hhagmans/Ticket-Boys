package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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
@ApplicationScoped
public class ShiftData implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	private static ShiftDAO			shiftDAO;

	public ShiftData()
	{
		shiftDAO = new ShiftDAOsqlLite();
		fill();
	}

	public void fill()
	{
		ShiftDAO shiftDAOtemp = new ShiftDAOTestImpl();

		List<Shift> allshifts = shiftDAOtemp.findAllShifts();

		if (shiftDAO.findAllShifts().isEmpty())
		{
			for (Shift shift : allshifts)
			{
				if (!shift.equals(shiftDAO.findShiftById(shift.getUniqueRowKey())))
					shiftDAO.addShift(shift);
			}
		}
	}

	public Shift findShiftByID(String uniqueRowKey)
	{
		return shiftDAO.findShiftById(uniqueRowKey);
	}

	public void updateShift(Shift shift)
	{
		shiftDAO.updateShift(shift);
	}

	public void deleteShift(Shift shift)
	{
		shiftDAO.deleteShift(shift);
	}

	public void addShift(Shift shift)
	{
		shiftDAO.addShift(shift);
	}

	public static List<Shift> findAllShifts()
	{
		return shiftDAO.findAllShifts();
	}
	
	public Shift findShiftByWeekNumber(int weekNumber) {
		List<Shift> findAllShifts = findAllShifts();
		
	for (Shift shift : findAllShifts) {
		if(shift.getWeekNumber()==weekNumber)
			return shift;
	}
	return null;
	}
}
