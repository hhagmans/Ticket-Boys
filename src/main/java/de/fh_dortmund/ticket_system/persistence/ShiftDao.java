package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

/**
 * Interface for the ShiftDao extending BaseDao and adding individual methods.
 * 
 * @author Ticket-Boys
 * 
 */
public interface ShiftDao extends BaseDao<Shift> {
	public List<Shift> findByEmployee(Employee employee);

	public void deleteEmployeeFromShifts(Employee employee);
}
