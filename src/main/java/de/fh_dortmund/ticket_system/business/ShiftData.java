package de.fh_dortmund.ticket_system.business;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.fh_dortmund.ticket_system.base.BaseData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.persistence.ShiftDao;
import de.fh_dortmund.ticket_system.persistence.ShiftDaoSqlite;
import de.fh_dortmund.ticket_system.util.TestdataProvider;

/**
 * Dieses Objekt verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten)
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean()
@ApplicationScoped
public class ShiftData extends BaseData<Shift, ShiftDao> implements Serializable
{
	private static final long serialVersionUID = -5407101199319373331L;

	public ShiftData()
	{
		setDao(new ShiftDaoSqlite());
	}

	@PostConstruct
	public void fill()
	{
		TestdataProvider.fillShift(getDao());
	}

	public Shift findShiftByWeekNumber(int weekNumber)
	{
		List<Shift> findAllShifts = findAll();

		for (Shift shift : findAllShifts)
		{
			if (shift.getWeek().getWeekNumber() == weekNumber)
			{
				return shift;
			}
		}
		return null;
	}

	public List<Shift> findShiftByEmployee(Employee employee)
	{
		return getDao().findByEmployee(employee);
	}

	public void deleteEmployeeFromShifts(Employee employee)
	{
		getDao().deleteEmployeeFromShifts(employee);
	}

	public Shift findLatestShift()
	{

		List<Shift> findAllShifts = findAll();
		if (findAllShifts == null || findAllShifts.size() == 0)
		{
			return null;
		}

		Shift latestShift = findAllShifts.get(0);
		for (Shift shift : findAllShifts)
		{
			if (shift.getWeek().isAfter(latestShift.getWeek()))
			{
				latestShift = shift;
			}
		}

		return latestShift;
	}
}
