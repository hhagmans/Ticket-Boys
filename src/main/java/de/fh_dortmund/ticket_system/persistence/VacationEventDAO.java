package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

public interface VacationEventDAO extends BaseDao<VacationEvent>
{
	public List<VacationEvent> findAllVacationEvents();
}
