package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import de.fh_dortmund.ticket_system.entity.Shift;

@ManagedBean
@SessionScoped
public class ShiftDAOsqlLite implements ShiftDAO
{

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlite");
	EntityManager entityManager = emf.createEntityManager();

	@Override
	public List<Shift> findAllShifts()
	{
		List<Shift> resultList = entityManager.createNamedQuery("Shift.findAll", Shift.class).getResultList();
		return resultList;
	}

	@Override
	public void updateShift(Shift shift)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.merge(shift);

		entityManager.persist(shift);
		tx.commit();
	}

	@Override
	public void deleteShift(Shift shift)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(shift);
		tx.commit();
	}

	@Override
	public void addShift(Shift newShift)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(newShift);
		tx.commit();
	}
	
	@Override
	public Shift findShiftById(String id)
	{
		return entityManager.find(Shift.class, id);
	}
}
