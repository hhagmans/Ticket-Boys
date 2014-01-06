package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import javax.persistence.Query;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

public class ConflictDaoSqlite extends BaseDaoSqlite<Conflict> implements ConflictDao
{
	@Override
	public List<Conflict> findAll()
	{
		return getEm().createNamedQuery("Conflict_findAll", Conflict.class).getResultList();
	}

	public List<Conflict> findByUser(Employee emp)
	{
		Query setParameter = getEm().createNamedQuery("Conflict_findByUser", Conflict.class).setParameter("employee",
			emp);
		return setParameter.getResultList();
	}

}
