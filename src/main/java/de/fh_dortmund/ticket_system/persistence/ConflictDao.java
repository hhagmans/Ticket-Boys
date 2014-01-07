package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

public interface ConflictDao extends BaseDao<Conflict> {

	public List<Conflict> findByUser(Employee emp);

}
