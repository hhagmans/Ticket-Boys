package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

public class VacationEventDaoSqlite extends BaseDaoSqlite<VacationEvent> implements VacationEventDao, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public void delete(VacationEvent vacationEvent)
	{

		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(vacationEvent);
		long diffDays = calculateDayCount(vacationEvent);
		vacationEvent.getEmployee().decrementVacationCount((int) diffDays + 1);
		tx.commit();
	}

	@Override
	public void add(VacationEvent vacationEvent)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(vacationEvent);
		long diffDays = calculateDayCount(vacationEvent);
		vacationEvent.getEmployee().incrementVacationCount((int) diffDays + 1);
		tx.commit();
	}
		
	@Override
	public void update(VacationEvent vacationEvent)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		VacationEvent oldEvent = getEm().find(VacationEvent.class, vacationEvent.getId());
		getEm().merge(vacationEvent);
		long newDiffDays = calculateDayCount(vacationEvent);
		long oldDiffDays = calculateDayCount(oldEvent);
		if (newDiffDays < oldDiffDays) {
			vacationEvent.getEmployee().decrementVacationCount((int) (oldDiffDays - newDiffDays + 1));
		}
		else if (newDiffDays > oldDiffDays){
			vacationEvent.getEmployee().incrementVacationCount((int) (newDiffDays - oldDiffDays + 1));
		}
		tx.commit();
	}

	@Override
	public VacationEvent findById(String id)
	{
		VacationEvent emp = getEm().find(VacationEvent.class, id);
		return emp;
	}
	
	@Override
	public List<VacationEvent> findAll() {
		return getEm().createNativeQuery("SELECT v FROM VacationEvent v", VacationEvent.class).getResultList();
	}
	
	private long calculateDayCount (VacationEvent vacationEvent) {
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
		return diffDays;
	}

}
