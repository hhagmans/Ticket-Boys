package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.persistence.EmployeeDAO;
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
public class ShiftData extends BaseData<Shift, ShiftDAO> implements Serializable
{
	public ShiftData()
	{
		dao = new ShiftDAOsqlLite();
		fill();
	}

	public void fill()
	{
		ShiftDAO shiftDAOtemp = new ShiftDAOTestImpl();

		List<Shift> allshifts = shiftDAOtemp.findAllShifts();

		if (dao.findAllShifts().isEmpty())
		{
			for (Shift shift : allshifts)
			{
				if (!shift.equals(dao.findById(shift.getUniqueRowKey())))
					dao.add(shift);
			}
		}
	}

	public List<Shift> findAllShifts()
	{
		return dao.findAllShifts();
	}

	public Shift findShiftByWeekNumber(int weekNumber)
	{
		List<Shift> findAllShifts = findAllShifts();

		for (Shift shift : findAllShifts)
		{
			if (shift.getWeekNumber() == weekNumber)
				return shift;
		}
		return null;
	}
	
}
