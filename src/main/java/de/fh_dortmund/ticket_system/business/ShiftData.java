package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.persistence.ShiftDao;
import de.fh_dortmund.ticket_system.persistence.ShiftDaoSqlite;
import de.fh_dortmund.ticket_system.persistence.ShiftDaoTestdataProvider;

/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten)
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
@ApplicationScoped
public class ShiftData extends BaseData<Shift, ShiftDao> implements Serializable
{
	private static final long	serialVersionUID	= -5407101199319373331L;

	public ShiftData()
	{
		dao = new ShiftDaoSqlite();
		fill();
	}

	public void fill()
	{
		ShiftDaoTestdataProvider dataProvider = new ShiftDaoTestdataProvider();
		List<Shift> allshifts = dataProvider.findAllShifts();

		if (dao.findAll().isEmpty())
		{
			for (Shift shift : allshifts)
			{
				if (!shift.equals(dao.findById(shift.getUniqueRowKey())))
					dao.add(shift);
			}
		}
	}

	public Shift findShiftByWeekNumber(int weekNumber)
	{
		List<Shift> findAllShifts = findAll();

		for (Shift shift : findAllShifts)
		{
			if (shift.getWeek().getWeekNumber() == weekNumber)
				return shift;
		}
		return null;
	}

	public List<Shift> findShiftByEmployee(Employee employee)
	{
		return dao.findByEmployee(employee);
	}
}
