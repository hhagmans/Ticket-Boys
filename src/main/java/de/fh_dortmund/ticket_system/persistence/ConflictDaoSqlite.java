package de.fh_dortmund.ticket_system.persistence;

import java.util.ArrayList;
import java.util.List;

import de.fh_dortmund.ticket_system.base.BaseDaoSqlite;
import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;

public class ConflictDaoSqlite extends BaseDaoSqlite<Conflict> implements ConflictDao
{

	public List<Conflict> findByEmployee(Employee employee)
	{
		List<Conflict> allConflicts = findAll();
		List<Conflict> userConflicts = new ArrayList<Conflict>();

		for (Conflict c : allConflicts)
		{
			if (c.getEmployee().equals(employee))
			{
				userConflicts.add(c);
			}
		}

		return userConflicts;
	}

}
