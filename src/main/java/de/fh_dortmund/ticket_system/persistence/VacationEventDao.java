package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

public interface VacationEventDao extends BaseDao<VacationEvent>
{
	public List<VacationEvent> findByUser(Employee emp);
}
