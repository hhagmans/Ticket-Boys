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
import de.fh_dortmund.ticket_system.entity.Shift;

public class ShiftDAOsqlLite extends BaseDAO implements ShiftDAO, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public List<Shift> findAllShifts()
	{
		List<Shift> resultList = getEm().createNamedQuery("Shift.findAll", Shift.class).getResultList();
		return resultList;
	}

	@Override
	public void updateShift(Shift shift)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().merge(shift);
		tx.commit();
	}

	@Override
	public void deleteShift(Shift shift)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(shift);
		tx.commit();
	}

	@Override
	public void addShift(Shift newShift)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(newShift);
		Employee emp = newShift.getDispatcher();
		emp.incrementScore();
		getEm().merge(emp);
		tx.commit();
	}

	@Override
	public Shift findShiftById(String id)
	{
		Shift shift = getEm().find(Shift.class, id);
		return shift;
	}
}
