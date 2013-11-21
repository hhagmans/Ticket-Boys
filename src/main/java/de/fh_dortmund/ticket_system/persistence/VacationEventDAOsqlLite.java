package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
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
		//vacationEvent.getEmployee().
		tx.commit();
		return true;
	}

	@Override
	public void addVacationEvent(VacationEvent vacationEvent)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(vacationEvent);
		tx.commit();
	}

	@Override
	public VacationEvent findVacationEventById(String id)
	{
		VacationEvent emp = getEm().find(VacationEvent.class, id);
		return emp;
	}

}
