package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

public class ShiftDaoSqlite extends BaseDaoSqlite<Shift> implements ShiftDao, Serializable
{
	private static final long	serialVersionUID	= 1L;

	@Override
	public void update(Shift shift)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().merge(shift);
		tx.commit();
	}

	@Override
	public void delete(Shift shift)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(shift);
		Employee emp = shift.getDispatcher();
		emp.decrementScore();
		getEm().merge(emp);
		tx.commit();
	}

	@Override
	public void add(Shift newShift)
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
	public Shift findById(String id)
	{
		Shift shift = getEm().find(Shift.class, id);
		return shift;
	}

	@Override
	public List<Shift> findByEmployee(Employee employee)
	{
		Query setParameter = getEm().createNamedQuery("findByDispatcher").setParameter("dispatcher", employee);
		return (List<Shift>) setParameter.getResultList();
	}
}
