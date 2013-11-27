package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fh_dortmund.ticket_system.base.BaseDAOSqlite;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

public class VacationEventDAOsqlLite extends BaseDAOSqlite<VacationEvent> implements VacationEventDAO, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public void delete(VacationEvent vacationEvent)
	{

		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(vacationEvent);
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Date startDate = vacationEvent.getStartDate();
		Date endDate = vacationEvent.getEndDate();
		start.setTime(startDate);
		end.setTime(endDate);
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		start.add(Calendar.DAY_OF_MONTH, (int) diffDays);
		while (start.before(end))
		{
			start.add(Calendar.DAY_OF_MONTH, 1);
			diffDays++;
		}
		while (start.after(end))
		{
			start.add(Calendar.DAY_OF_MONTH, -1);
			diffDays--;
		}
		vacationEvent.getEmployee().decrementVacationCouint((int) diffDays + 1);
		tx.commit();
	}

	@Override
	public void add(VacationEvent vacationEvent)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(vacationEvent);
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Date startDate = vacationEvent.getStartDate();
		Date endDate = vacationEvent.getEndDate();
		start.setTime(startDate);
		end.setTime(endDate);
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		start.add(Calendar.DAY_OF_MONTH, (int) diffDays);
		while (start.before(end))
		{
			start.add(Calendar.DAY_OF_MONTH, 1);
			diffDays++;
		}
		while (start.after(end))
		{
			start.add(Calendar.DAY_OF_MONTH, -1);
			diffDays--;
		}
		vacationEvent.getEmployee().incrementVacationCouint((int) diffDays + 1);
		tx.commit();
	}

	@Override
	public VacationEvent findById(String id)
	{
		VacationEvent emp = getEm().find(VacationEvent.class, id);
		return emp;
	}

}
