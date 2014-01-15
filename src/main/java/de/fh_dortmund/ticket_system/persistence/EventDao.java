package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Event;

/**
 * Interface for the EventDao extending BaseDao and adding individual methods.
 * 
 * @author Ticket-Boys
 * 
 */
public interface EventDao extends BaseDao<Event> {
	public List<Event> findByUser(Employee emp);

	public void updateVacationCount(Employee emp);
}
