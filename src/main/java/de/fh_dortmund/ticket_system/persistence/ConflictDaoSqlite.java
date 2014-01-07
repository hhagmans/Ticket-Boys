package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.ArrayList;
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

	@Override
	public List<Conflict> findByUser(Employee emp) {
		Query setParameter = getEm().createNamedQuery("Conflict_findAll",
				Conflict.class);

		ArrayList<Conflict> conflictList = new ArrayList<Conflict>(
				setParameter.getResultList());

		List<Conflict> filteredList = new ArrayList<Conflict>();
		for (Conflict conflict : conflictList) {
			if (!conflict.getSolved() && conflict.getEmployee().equals(emp)) {
				filteredList.add(conflict);
			}
		}
		return filteredList;
	}

}
