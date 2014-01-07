package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

public class ConflictDaoSqlite extends BaseDaoSqlite<Conflict> implements
		ConflictDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public List<Conflict> findAll() {
		return getEm().createNamedQuery("Conflict_findAll", Conflict.class)
				.getResultList();
	}

	public List<Conflict> findByUser(Employee emp) {
		Query setParameter = getEm().createNamedQuery("Conflict_findByUser",
				Conflict.class).setParameter("employee", emp);
		return setParameter.getResultList();
	}

}
