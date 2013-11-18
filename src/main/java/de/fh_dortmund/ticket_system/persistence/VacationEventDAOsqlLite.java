package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import de.fh_dortmund.ticket_system.entity.VacationEvent;

@ManagedBean
@SessionScoped
public class VacationEventDAOsqlLite implements VacationEventDAO, Serializable
{
	private static final long serialVersionUID = 1L;

	private EntityManager entityManager;

	public VacationEventDAOsqlLite()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlite");
		entityManager = emf.createEntityManager();
	}

	@Override
	public List<VacationEvent> findAllVacationEvents()
	{
		List<VacationEvent> resultList = entityManager.createNamedQuery("VacationEvent.findAll", VacationEvent.class).getResultList();
		return resultList;
	}

	@Override
	public void updateVacationEvent(VacationEvent vacationEvent)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.merge(vacationEvent);
		tx.commit();
	}

	@Override
	public void deleteVacationEvent(VacationEvent vacationEvent)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(vacationEvent);
		tx.commit();
	}

	@Override
	public void addVacationEvent(VacationEvent vacationEvent)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(vacationEvent);
		tx.commit();
	}

	@Override
	public VacationEvent findVacationEventById(String id)
	{
		VacationEvent emp = entityManager.find(VacationEvent.class, id);
		return emp;
	}

}
