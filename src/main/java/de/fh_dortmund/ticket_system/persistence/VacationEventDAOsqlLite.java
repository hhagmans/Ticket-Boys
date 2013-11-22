package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.VacationEvent;
import de.fh_dortmund.ticket_system.util.RightsManager;

public class VacationEventDAOsqlLite extends BaseDAO implements VacationEventDAO, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public List<VacationEvent> findAllVacationEvents()
	{
		List<VacationEvent> resultList = getEm().createNamedQuery("VacationEvent.findAll", VacationEvent.class)
				.getResultList();
		return resultList;
	}

	@Override
	public void updateVacationEvent(VacationEvent vacationEvent)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().merge(vacationEvent);
		tx.commit();
	}

	@Override
	public boolean deleteVacationEvent(VacationEvent vacationEvent)
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
		start.add(Calendar.DAY_OF_MONTH, (int)diffDays);
		while (start.before(end)) {
		    start.add(Calendar.DAY_OF_MONTH, 1);
		    diffDays++;
		}
		while (start.after(end)) {
		    start.add(Calendar.DAY_OF_MONTH, -1);
		    diffDays--;
		}
		vacationEvent.getEmployee().decrementVacationCouint((int)diffDays + 1);
		tx.commit();
		return true;
	}

	@Override
	public void addVacationEvent(VacationEvent vacationEvent)
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
		start.add(Calendar.DAY_OF_MONTH, (int)diffDays);
		while (start.before(end)) {
		    start.add(Calendar.DAY_OF_MONTH, 1);
		    diffDays++;
		}
		while (start.after(end)) {
		    start.add(Calendar.DAY_OF_MONTH, -1);
		    diffDays--;
		}
		vacationEvent.getEmployee().incrementVacationCouint((int)diffDays + 1);
		tx.commit();
	}

	@Override
	public VacationEvent findVacationEventById(String id)
	{
		VacationEvent emp = getEm().find(VacationEvent.class, id);
		return emp;
	}

}
