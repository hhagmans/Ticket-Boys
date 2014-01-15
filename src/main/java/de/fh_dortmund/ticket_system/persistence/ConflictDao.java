package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

/**
 * Interface for the CanflictDao extending BaseDao and adding individual
 * methods.
 * 
 * @author Ticket-Boys
 * 
 */
public interface ConflictDao extends BaseDao<Conflict> {

	public List<Conflict> findByUser(Employee emp);

}
