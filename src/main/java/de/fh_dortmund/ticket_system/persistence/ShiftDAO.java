package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDao;
import de.fh_dortmund.ticket_system.entity.Shift;

public interface ShiftDAO extends BaseDao<Shift>
{
	public List<Shift> findAllShifts();
}
